package services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Comment;
import domain.Customer;
import domain.Folder;
import domain.MasterClass;
import domain.Recipe;
import domain.SocialIdentity;
import domain.User;

@Service
@Transactional
public class UserService {

	// Managed repository -------------------------------
	@Autowired
	private UserRepository userRepository;

	// Suporting service --------------------------------
	@Autowired
	private FolderService folderService;
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private ActorService actorService;

	// Constructor --------------------------------------
	public UserService() {
		super();
	}

	// Simple CRUD methods ------------------------------

	public User create() {
		User u = new User();

		Authority a = new Authority();

		UserAccount ua = new UserAccount();
		a.setAuthority("USER");
		ua.setAuthorities(new HashSet<Authority>());
		ua.getAuthorities().add(a);

		u.setUserAccount(ua);

		u.setAuthoredRecipes(new ArrayList<Recipe>());
		u.setComments(new ArrayList<Comment>());
		u.setSocialIdentities(new ArrayList<SocialIdentity>());
		u.setMasterClasses(new ArrayList<MasterClass>());
		u.setFolders(new ArrayList<Folder>());
		u.setFollowers(new ArrayList<Customer>());
		u.setDislikedRecipes(new ArrayList<Recipe>());
		u.setLikedRecipes(new ArrayList<Recipe>());
		u.setFollowing(new ArrayList<Customer>());
		return u;
	}

	public User saveRelationships(User u){
		User savedUser = userRepository.save(u);
		return savedUser;
	}
	
	public User save(User u) {
		Assert.notNull(u);

		User a = userRepository.findOne(u.getId());

		a.setName(u.getName());
		a.setSurname(u.getSurname());
		a.setEmailAddress(u.getEmailAddress());
		a.setPhoneNumber(u.getPhoneNumber());
		a.setPostalAddress(u.getPostalAddress());

		u = userRepository.save(u);

		return u;
	}

	public User save2(User u) {
		Assert.notNull(u);

		String password;
		String hash;

		password = u.getUserAccount().getPassword();
		hash = encodePassword(password);
		u.getUserAccount().setPassword(hash);

		u = userRepository.save(u);

		Collection<Folder> folders = new HashSet<Folder>();
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

		User u2 = userRepository.save(u);

		return u2;
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

	public void delete(User a) {

		for (Folder ff : a.getFolders()) {
			folderService.delete(ff, a);
		}

		if (a.getAuthoredRecipes() != null
				&& a.getAuthoredRecipes().size() != 0) {
			for (Recipe r : a.getAuthoredRecipes()) {
				recipeService.delete(r);
			}
		}

		userRepository.delete(a);
	}

	public User findOne(Integer id) {
		return userRepository.findOne(id);
	}

	@SuppressWarnings("static-access")
	public User findByPrincipal() {
		UserAccount userAccount;
		User result;

		userAccount = loginService.getPrincipal();
		result = findByUserAccount(userAccount);
		return result;
	}

	public User findByUserAccount(UserAccount userAccount) {
		User result = userRepository.findByUserAccount(userAccount.getId());
		return result;

	}

	public Collection<User> findAll() {
		return userRepository.findAll();
	}

	// Other User methods ---------------------------
	public User searchUserByName(String keyword) {
		return userRepository.searchUserByName(keyword);
	}

	public User findUserByRecipeId(int idRecipe) {
		return userRepository.findUserByRecipeId(idRecipe);
	}

	public Collection<User> findUserMaxRecipes() {
		return userRepository.findUserMaxRecipes();
	}

	public Collection<User> findUserOrderByFollowers() {
		return userRepository.findUserOrderByFollowers();
	}

	public Collection<User> findUserRegardingAvgLikesAndDislikes() {
		return userRepository.findUserRegardingAvgLikesAndDislikes();
	}

	public void checkPrincipal() {
		Actor a = actorService.findByPrincipal();
		for (Authority b : a.getUserAccount().getAuthorities()) {
			Assert.isTrue(b.getAuthority().equals("USER"));
		}

	}

	public Collection<User> findUserByKeywordName(
			String keyWord) {
		return userRepository
				.findUserByKeywordName(keyWord);
	}

	public Customer saveRelations(User c) {
		return userRepository.save(c);
	}
}