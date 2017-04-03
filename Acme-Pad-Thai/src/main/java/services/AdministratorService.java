package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Category;
import domain.Contest;
import domain.Cook;
import domain.Folder;
import domain.MasterClass;
import domain.SocialIdentity;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository adminRepository;

	@Autowired
	private FolderService folderService;
	@Autowired
	private LoginService loginService;

	public AdministratorService() {

		super();
	}

	public Administrator create() {

		Administrator a = new Administrator();

		Authority b = new Authority();
		b.setAuthority("ADMINISTRATOR");
		UserAccount ua = new UserAccount();
		ua.addAuthority(b);
		a.setUserAccount(ua);

		a.setContests(new ArrayList<Contest>());
		a.setMasterClasses(new ArrayList<MasterClass>());
		a.setCategories(new ArrayList<Category>());
		a.setCooks(new ArrayList<Cook>());
		a.setFolders(new ArrayList<Folder>());
		a.setSocialIdentities(new ArrayList<SocialIdentity>());
		a.setPromotedMasterClasses(new ArrayList<MasterClass>());
		return a;
	}

	public void delete(Administrator admin) {

		Assert.notNull(admin);

		for (Folder ff : admin.getFolders()) {
			folderService.delete(ff, admin);
		}

		adminRepository.delete(admin);
	}

	public Administrator save(Administrator admin) {

		Assert.notNull(admin);

		return adminRepository.save(admin);

	}

	public Collection<Administrator> findAll() {
		return adminRepository.findAll();
	}

	@SuppressWarnings("static-access")
	public Administrator findByPrincipal() {

		UserAccount userAccount;
		Administrator result;

		userAccount = loginService.getPrincipal();
		result = findByUserAccount(userAccount);
		return result;
	}

	public Administrator findByUserAccount(UserAccount userAccount) {
		Administrator result;
		result = adminRepository.findByUserAccount(userAccount.getId());
		return result;

	}

	public void checkPrincipal(Administrator a) {
		Administrator admin = findByPrincipal();
		Assert.isTrue(admin.equals(a));

	}

}
