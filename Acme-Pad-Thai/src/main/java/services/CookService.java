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

import repositories.CookRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Cook;
import domain.Folder;
import domain.MasterClass;
import domain.SocialIdentity;

@Service
@Transactional
public class CookService {
	// Managed repository -------------------------------
	@Autowired
	private CookRepository cookRepository;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private FolderService folderService;

	@Autowired
	private LoginService loginService;
	@Autowired
	private ActorService actorService;

	// Constructor --------------------------------------
	public CookService() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Simple CRUD methods ------------------------------
	public Cook create() {
		checkPrincipal();
		Cook c = new Cook();

		Authority b = new Authority();
		b.setAuthority("COOK");
		UserAccount ua = new UserAccount();
		ua.addAuthority(b);
		c.setUserAccount(ua);

		c.setManagedMasterClasses(new ArrayList<MasterClass>());
		c.setMasterClasses(new ArrayList<MasterClass>());

		c.setFolders(new ArrayList<Folder>());
		c.setSocialIdentities(new ArrayList<SocialIdentity>());
		return c;
	}

	public Cook save(Cook u) {
		Assert.notNull(u);
		Cook cook2 = cookRepository.save(u);

		return cook2;
	}

	public void delete(Cook c) {
		Assert.notNull(c);

		for (Administrator a : administratorService.findAll()) {

			Set<Cook> sc = new HashSet<Cook>(a.getCooks());
			if (sc.contains(c)) {
				Set<Cook> scc = new HashSet<Cook>(sc);
				scc.remove(c);
				a.setCooks(scc);

				administratorService.save(a);

			}
		}

		for (Folder ff : c.getFolders()) {

			folderService.delete(ff, c);
		}

		cookRepository.delete(c);
	}

	public Collection<Cook> findAll() {
		return cookRepository.findAll();
	}

	// Other business methods ---------------------------

	@SuppressWarnings("static-access")
	public Cook findByPrincipal() {

		UserAccount userAccount;
		Cook result;

		userAccount = loginService.getPrincipal();
		result = findByUserAccount(userAccount);
		return result;
	}

	public Cook findByUserAccount(UserAccount userAccount) {
		Cook result;
		result = cookRepository.findByUserAccount(userAccount.getId());
		return result;
	}

	public void checkPrincipal() {
		Actor a = actorService.findByPrincipal();
		for (Authority b : a.getUserAccount().getAuthorities()) {
			Assert.isTrue(b.getAuthority().equals("ADMINISTRATOR"));
		}
	}

	public void checkPrincipal2(Cook c) {
		Cook cook = findByPrincipal();
		Assert.isTrue(cook.equals(c));
	}

	public Collection<Object[]> findCooksSortedAccordingToNumberOfPromotedMasterClasses() {
		return cookRepository
				.findCooksSortedAccordingToNumberOfPromotedMasterClasses();
	}

	public Cook save2(Cook u) {
		Assert.notNull(u);
		String password;
		String hash;

		password = u.getUserAccount().getPassword();
		hash = encodePassword(password);
		u.getUserAccount().setPassword(hash);

		u = cookRepository.save(u);

		Administrator admin = administratorService.findByPrincipal();

		Set<Cook> cc = new HashSet<Cook>(admin.getCooks());
		cc.add(u);
		admin.setCooks(cc);
		administratorService.save(admin);

		Collection<Folder> folders = new ArrayList<Folder>();

		Folder f1 = folderService.create(u);
		f1.setSystemFolder(true);
		f1.setName("Inbox");
		Folder ff1 = folderService.save(f1);

		Folder f2 = folderService.create(u);
		f2.setSystemFolder(true);
		f2.setName("Outbox");
		Folder ff2 = folderService.save(f2);

		Folder f3 = folderService.create(u);
		f3.setSystemFolder(true);
		f3.setName("Spambox");
		Folder ff3 = folderService.save(f3);

		Folder f4 = folderService.create(u);
		f4.setSystemFolder(true);
		f4.setName("Trashbox");
		Folder ff4 = folderService.save(f4);

		folders.add(ff1);
		folders.add(ff2);
		folders.add(ff3);
		folders.add(ff4);
		u.setFolders(folders);

		return cookRepository.save(u);

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
}
