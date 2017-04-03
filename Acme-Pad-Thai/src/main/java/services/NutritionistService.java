package services;

import java.util.HashSet;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NutritionistRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Comment;
import domain.Curriculum;
import domain.Customer;
import domain.Folder;
import domain.Ingredient;
import domain.MasterClass;
import domain.Nutritionist;
import domain.Recipe;
import domain.SocialIdentity;

@Service
@Transactional
public class NutritionistService {
	// Managed repository -------------------------------
	@Autowired
	private NutritionistRepository nutritionistRepository;
	@Autowired
	private FolderService folderService;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private IngredientService ingredientService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private ActorService actorService;

	// Constructor --------------------------------------

	public NutritionistService() {
		super();
	}

	// Simple CRUD methods ------------------------------

	public Nutritionist create() {
		Nutritionist n = new Nutritionist();

		Authority b = new Authority();
		b.setAuthority("NUTRITIONIST");
		UserAccount ua = new UserAccount();
		ua.addAuthority(b);
		n.setUserAccount(ua);

		n.setComments(new HashSet<Comment>());
		n.setSocialIdentities(new HashSet<SocialIdentity>());
		n.setMasterClasses(new HashSet<MasterClass>());
		n.setFolders(new HashSet<Folder>());
		n.setFollowers(new HashSet<Customer>());
		n.setDislikedRecipes(new HashSet<Recipe>());
		n.setLikedRecipes(new HashSet<Recipe>());
		n.setFollowing(new HashSet<Customer>());
		n.setIngredients(new HashSet<Ingredient>());
		return n;
	}

	public Nutritionist save(Nutritionist n) {
		Assert.notNull(n);

		n = nutritionistRepository.save(n);

		if (n.getId() == 0) {

			Collection<Folder> folders = new HashSet<Folder>();
			Folder f1 = folderService.create(n);
			f1.setSystemFolder(true);
			f1.setName("Inbox");
			Folder ff1 = folderService.save(f1);
			Folder f2 = folderService.create(n);
			f2.setSystemFolder(true);
			f2.setName("Outbox");
			Folder ff2 = folderService.save(f2);
			Folder f3 = folderService.create(n);
			f3.setSystemFolder(true);
			f3.setName("Spambox");
			Folder ff3 = folderService.save(f3);
			Folder f4 = folderService.create(n);
			f4.setSystemFolder(true);
			f4.setName("Trashbox");
			Folder ff4 = folderService.save(f4);
			folders.add(ff1);
			folders.add(ff2);
			folders.add(ff3);
			folders.add(ff4);
			n.setFolders(folders);
		}

		return nutritionistRepository.save(n);
	}

	public Nutritionist saveRelationships(Nutritionist nutritionist) {
		Nutritionist savedNutritionist = nutritionistRepository.save(nutritionist);
		return savedNutritionist;
	}
	
	public Nutritionist save2(Nutritionist u) {
		Assert.notNull(u);
		String password;
		String hash;

		password = u.getUserAccount().getPassword();
		hash = encodePassword(password);
		u.getUserAccount().setPassword(hash);

		Nutritionist n = nutritionistRepository.save(u);

		Collection<Folder> folders = new HashSet<Folder>();
		Folder f1 = folderService.create(n);
		f1.setSystemFolder(true);
		f1.setName("Inbox");
		Folder ff1 = folderService.save(f1);
		Folder f2 = folderService.create(n);
		f2.setSystemFolder(true);
		f2.setName("Outbox");
		Folder ff2 = folderService.save(f2);
		Folder f3 = folderService.create(n);
		f3.setSystemFolder(true);
		f3.setName("Spambox");
		Folder ff3 = folderService.save(f3);
		Folder f4 = folderService.create(n);
		f4.setSystemFolder(true);
		f4.setName("Trashbox");
		Folder ff4 = folderService.save(f4);
		folders.add(ff1);
		folders.add(ff2);
		folders.add(ff3);
		folders.add(ff4);
		n.setFolders(folders);

		return nutritionistRepository.save(n);
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

	public Nutritionist findOne(int id) {
		return nutritionistRepository.findOne(id);
	}

	public void delete(Nutritionist n) {
		checkPrincipal();
		if (n.getCurriculum() != null) {
			Curriculum c = n.getCurriculum();
			curriculumService.delete(c);
		}
		for (Folder ff : n.getFolders()) {
			folderService.delete(ff, n);
		}

		for (Ingredient i : n.getIngredients()) {
			ingredientService.delete(i);
		}

		nutritionistRepository.delete(n);

	}

	public Collection<Nutritionist> findAll() {
		return nutritionistRepository.findAll();
	}

	// Other business methods ---------------------------
	public Nutritionist findNutritionistByIngredient(Integer i) {
		return nutritionistRepository.findNutritionistByIngredient(i);
	}

	@SuppressWarnings("static-access")
	public Nutritionist findByPrincipal() {
		UserAccount userAccount;
		Nutritionist result;

		userAccount = loginService.getPrincipal();
		result = findByNutritionistAccount(userAccount);
		return result;
	}

	public Nutritionist findByNutritionistAccount(UserAccount userAccount) {
		Nutritionist result;
		result = nutritionistRepository.findByNutritionistAccount(userAccount
				.getId());
		return result;
	}

	public void checkPrincipal() {
		Actor a = actorService.findByPrincipal();
		for (Authority b : a.getUserAccount().getAuthorities()) {
			Assert.isTrue(b.getAuthority().equals("NUTRITIONIST"));
		}

	}

	public Customer saveRelations(Nutritionist c) {
		return nutritionistRepository.save(c);
	}

}
