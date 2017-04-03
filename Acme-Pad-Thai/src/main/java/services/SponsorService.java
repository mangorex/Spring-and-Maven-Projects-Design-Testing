package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Bill;
import domain.Campaign;
import domain.CreditCard;
import domain.Folder;
import domain.MasterClass;
import domain.SocialIdentity;
import domain.Sponsor;

@Transactional
@Service
public class SponsorService {

	// Managed repository -------------------------------
	@Autowired
	private SponsorRepository sponsorRepository;

	@Autowired
	private CreditCardService creditCardService;
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private FolderService folderService;

	// Constructor --------------------------------------
	public SponsorService() {
		super();
	}

	// Simple CRUD methods ------------------------------

	public Sponsor create() {

		Sponsor b = new Sponsor();

		Authority c = new Authority();
		c.setAuthority("SPONSOR");
		UserAccount ua = new UserAccount();
		ua.addAuthority(c);
		b.setUserAccount(ua);

		Collection<CreditCard> crd = new ArrayList<CreditCard>();
		b.setCreditCards(crd);

		Collection<Campaign> ca = new ArrayList<Campaign>();
		b.setCampaigns(ca);

		Collection<Bill> bills = new ArrayList<Bill>();
		b.setBills(bills);

		Collection<Folder> folders = new ArrayList<Folder>();
		b.setFolders(folders);

		b.setMasterClasses(new ArrayList<MasterClass>());
		b.setSocialIdentities(new ArrayList<SocialIdentity>());
		return b;
	}

	public Sponsor save(Sponsor c) {

//		if (c.getId() != 0) {
//			checkPrincipal(c);
//		}

		Assert.notNull(c);
		Sponsor s;

		s = sponsorRepository.save(c);

		if (c.getId() == 0) {
			Set<Folder> folders = new HashSet<Folder>(s.getFolders());

			Folder f1 = folderService.create(s);
			f1.setSystemFolder(true);
			f1.setName("Inbox");
			Folder ff1 = folderService.save(f1);

			Folder f2 = folderService.create(s);
			f2.setSystemFolder(true);
			f2.setName("Outbox");
			Folder ff2 = folderService.save(f2);

			Folder f3 = folderService.create(s);
			f3.setSystemFolder(true);
			f3.setName("Spambox");
			Folder ff3 = folderService.save(f3);

			Folder f4 = folderService.create(s);
			f4.setSystemFolder(true);
			f4.setName("Trashbox");
			Folder ff4 = folderService.save(f4);

			folders.add(ff1);
			folders.add(ff2);
			folders.add(ff3);
			folders.add(ff4);

			s.setFolders(folders);

			s = sponsorRepository.save(s);

		}

		return s;
	}

	public Sponsor saveRelations(Sponsor s){
		return sponsorRepository.save(s);
	}

	public Sponsor save2(Sponsor u) {

		Assert.notNull(u);
		Sponsor s;

		String password;
		String hash;

		password = u.getUserAccount().getPassword();
		hash = encodePassword(password);
		u.getUserAccount().setPassword(hash);

		s = sponsorRepository.save(u);

		Set<Folder> folders = new HashSet<Folder>(s.getFolders());

		Folder f1 = folderService.create(s);
		f1.setSystemFolder(true);
		f1.setName("Inbox");
		Folder ff1 = folderService.save(f1);

		Folder f2 = folderService.create(s);
		f2.setSystemFolder(true);
		f2.setName("Outbox");
		Folder ff2 = folderService.save(f2);

		Folder f3 = folderService.create(s);
		f3.setSystemFolder(true);
		f3.setName("Spambox");
		Folder ff3 = folderService.save(f3);

		Folder f4 = folderService.create(s);
		f4.setSystemFolder(true);
		f4.setName("Trashbox");
		Folder ff4 = folderService.save(f4);

		folders.add(ff1);
		folders.add(ff2);
		folders.add(ff3);
		folders.add(ff4);

		s.setFolders(folders);

		s = sponsorRepository.save(s);

		return s;
	}

	public void delete(Sponsor c) {

		for (Campaign a : c.getCampaigns()) {
			campaignService.delete(a);
		}

		for (CreditCard cc : c.getCreditCards()) {
			creditCardService.delete(cc);
		}

		for (Folder ff : c.getFolders()) {
			folderService.delete(ff, c);
		}

		sponsorRepository.delete(c);

	}

	public boolean existsUsername(String username) {
		boolean result = false;
		long howManyUsers;

		howManyUsers = sponsorRepository.countUsersWithUsername(username);

		if (howManyUsers != 0) {
			result = true;
		}

		return result;
	}

	private String encodePassword(String password) {
		Md5PasswordEncoder encoder;
		String result;

		if (password == null || "".equals(password)) {
			result = null;
		} else {
			encoder = new Md5PasswordEncoder();
			result = encoder.encodePassword(password, null);
		}

		return result;
	}

	@SuppressWarnings("static-access")
	public Sponsor findByPrincipal() {
		UserAccount userAccount;
		Sponsor result;

		userAccount = loginService.getPrincipal();
		result = findByUserAccount(userAccount);
		return result;
	}

	public Sponsor findByUserAccount(UserAccount userAccount) {
		Sponsor result;

		result = sponsorRepository.findByUserAccount(userAccount.getId());
		return result;

	}

	public Collection<Sponsor> findAll() {

		return sponsorRepository.findAll();
	}

	// Other business methods ---------------------------

	public Collection<Sponsor> findRankingCompaniesNumberOfCampaigns() {
		Collection<Sponsor> result = sponsorRepository
				.findRankingCompaniesNumberOfCampaigns();
		return result;
	}

	public Collection<Sponsor> findRankingCompaniesMonthlyBill() {
		Collection<Sponsor> result = sponsorRepository
				.findRankingCompaniesMonthlyBill();
		return result;
	}

	public Collection<Sponsor> findSponsorNotManagedCampaignForTheLastThreeMonths() {
		Collection<Sponsor> result = sponsorRepository
				.findSponsorNotManagedCampaignForTheLastThreeMonths();
		return result;
	}

	public Collection<Sponsor> findCompaniesHaveSpentLessAvgInCampaigns() {
		Collection<Sponsor> result = sponsorRepository
				.findCompaniesHaveSpentLessAvgInCampaigns();
		return result;
	}

	public Collection<Sponsor> findCompanyNameSpentAtLeastNinetyPerCentMaxAmountCompanyOnCampaign() {
		Collection<Sponsor> result = sponsorRepository
				.findCompanyNameSpentAtLeastNinetyPerCentMaxAmountCompanyOnCampaign();
		return result;
	}

	public Collection<Sponsor> sponsorsWithUnpaidBills() {
		return sponsorRepository.sponsorsWithUnpaidBills();
	}

	public void checkPrincipal(Sponsor c) {
		Sponsor sponsor = findByPrincipal();
		Assert.isTrue(sponsor.equals(c));
	}
}
