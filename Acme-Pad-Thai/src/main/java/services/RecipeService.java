package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RecipeRepository;
import domain.Category;
import domain.Comment;
import domain.Contest;
import domain.Customer;
import domain.Ingredient;
import domain.Nutritionist;
import domain.Quantified;
import domain.Recipe;
import domain.Step;
import domain.User;

@Service
@Transactional
public class RecipeService {

	// Managed repository -------------------------------
	@Autowired
	private RecipeRepository recipeRepository;
	@Autowired
	private CustomerService customerService;

	// Suporting services --------------------------------

	@Autowired
	private UserService userService;
	@Autowired
	private QuantifiedService quantifiedService;
	@Autowired
	private StepService stepService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ContestService contestService;
	@Autowired
	private CommentService commentService;

	@Autowired
	private IngredientService ingredientService;
	@Autowired
	private NutritionistService nutritionistService;

	// Constructor --------------------------------------
	public RecipeService() {
		super();
	}

	// Simple CRUD methods ------------------------------

	public Recipe create() {
		Recipe result;
		User author;
		author = userService.findByPrincipal();
		Assert.notNull(author);
		result = new Recipe();

		Date currentDate = new Date();
		String newTicker = createNewTicker();

		result.setTicker(newTicker);
		result.setAuthor(author);
		result.setCreationDate(currentDate);
		result.setUpdateDate(currentDate);
		result.setCategories(new ArrayList<Category>());
		result.setContests(new ArrayList<Contest>());
		result.setComments(new ArrayList<Comment>());
		result.setLikedCustomers(new ArrayList<Customer>());
		result.setDislikedCustomers(new ArrayList<Customer>());
		result.setSteps(new ArrayList<Step>());
		result.setQuantifieds(new ArrayList<Quantified>());
		result.setPictures(new ArrayList<String>());

		return result;
	}

	public Recipe save(Recipe r) {

		checkPrincipal(r);
		Assert.notNull(r);
		Assert.notNull(r.getCategories());
		for (String s : r.getPictures()) {
			Assert.isTrue(isUrl(s));
		}
		// List<Category> categoriesFather = new ArrayList<Category>();
		// List<Category> categories = (List<Category>) r.getCategories();
		Recipe savedRecipe = recipeRepository.save(r);

		// for (Category c : savedRecipe.getCategories()) {
		// categoriesFather = (List<Category>) checkFather(c , savedRecipe,
		// categoriesFather);
		// for(Category cat: categoriesFather)
		// if( !categories.contains(cat) )
		// categories.add(cat);
		// }

		// savedRecipe.setCategories(categories);

		User a = savedRecipe.getAuthor();
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		recipes.addAll(a.getAuthoredRecipes());
		if (!recipes.contains(savedRecipe)) {
			recipes.add(savedRecipe);
			a.setAuthoredRecipes(recipes);
			userService.save(a);
		}

		return savedRecipe;
	}

	public Recipe saveCopy(Recipe r, Collection<Step> steps,
			Collection<Quantified> quantifieds, Collection<Comment> comments) {

		Assert.notNull(r);

		for (String s : r.getPictures()) {
			Assert.isTrue(isUrl(s));
		}

		Quantified quantifiedCopy;
		Comment commentCopy;
		Step stepCopy;

		List<Quantified> copyquantifieds = new ArrayList<Quantified>();
		List<Step> copySteps = new ArrayList<Step>();
		List<Comment> copyComments = new ArrayList<Comment>();

		r.setQuantifieds(new ArrayList<Quantified>());
		r.setSteps(new ArrayList<Step>());

		Recipe savedRecipe = recipeRepository.save(r);

		User a = savedRecipe.getAuthor();
		List<Recipe> recipes = new ArrayList<Recipe>();
		recipes.addAll(a.getAuthoredRecipes());
		recipes.add(savedRecipe);
		a.setAuthoredRecipes(recipes);
		userService.saveRelationships(a);

		Collection<Category> categories = savedRecipe.getCategories();
		recipes.addAll(new ArrayList<Recipe>());
		for (Category category : categories) {
			recipes.addAll(category.getRecipes());
			recipes.add(savedRecipe);
			category.setRecipes(recipes);
			categoryService.saveRelations(category);
		}

		if (comments.size() != 0 && comments != null) {

			for (Comment c : comments) {
				Comment auxQ = c;
				c = commentService.create(savedRecipe, c.getCustomer());
				c.setStars(auxQ.getStars());
				c.setText(auxQ.getText());
				c.setTitle(auxQ.getTitle());
				commentCopy = commentService.saveRelations(c);
				copyComments.add(commentCopy);
			}
		}

		Collection<Contest> contests = savedRecipe.getContests();
		recipes.addAll(new ArrayList<Recipe>());

		if (contests.size() != 0 || contests != null) {
			for (Contest contest : contests) {
				recipes.addAll(contest.getRecipes());
				recipes.add(savedRecipe);
				contestService.saveQualifiedRecipe(contest);
			}
		}

		if (quantifieds.size() != 0 && quantifieds != null) {
			for (Quantified q : quantifieds) {
				Quantified auxQ = q;
				q = quantifiedService.create(savedRecipe, q.getIngredient());
				q.setQuantityInteger(auxQ.getQuantityInteger());
				q.setQuantityDouble(auxQ.getQuantityDouble());
				q.setUnit(auxQ.getUnit());
				quantifiedCopy = quantifiedService.save(q);
				copyquantifieds.add(quantifiedCopy);
			}
		}

		if (steps.size() != 0 && steps != null) {
			for (Step s : steps) {
				Step auxS = s;
				s = stepService.create(savedRecipe);
				s.setDescription(auxS.getDescription());
				s.setHints(auxS.getHints());
				s.setNumber(auxS.getNumber());
				s.setPicture(auxS.getPicture());
				stepCopy = stepService.saveWithoutRecipe(s);
				copySteps.add(stepCopy);
			}
		}

		savedRecipe.setQuantifieds(copyquantifieds);
		savedRecipe.setSteps(copySteps);
		savedRecipe = recipeRepository.save(savedRecipe);
		return savedRecipe;
	}

	public Recipe saveLikedCustomer(Recipe r) {

		Recipe savedRecipe = r;
		Customer customer = customerService.findByPrincipal();

		if (r.getAuthor() != customer) {
			Set<Customer> likedCustomers = new HashSet<Customer>();
			likedCustomers.addAll(r.getLikedCustomers());

			if (!likedCustomers.contains(customer)) {
				likedCustomers.add(customer);

				Set<Customer> disLikedCustomers = new HashSet<Customer>();
				disLikedCustomers.addAll(r.getDislikedCustomers());
				disLikedCustomers.remove(customer);

				r.setLikedCustomers(likedCustomers);
				r.setDislikedCustomers(disLikedCustomers);
				savedRecipe = recipeRepository.save(r);

				customerService.saveLikedRecipes(r);
			}
		}
		return savedRecipe;
	}

	public Recipe saveDisLikedCustomer(Recipe r) {

		Recipe savedRecipe = r;
		Customer customer = customerService.findByPrincipal();

		if (r.getAuthor() != customer) {
			Set<Customer> disLikedCustomers = new HashSet<Customer>();
			disLikedCustomers.addAll(r.getDislikedCustomers());

			if (!disLikedCustomers.contains(customer)) {
				disLikedCustomers.add(customer);

				Set<Customer> likedCustomers = new HashSet<Customer>();
				likedCustomers.addAll(r.getLikedCustomers());
				likedCustomers.remove(customer);

				r.setLikedCustomers(likedCustomers);
				r.setDislikedCustomers(disLikedCustomers);
				savedRecipe = recipeRepository.save(r);

				customerService.saveDisLikedRecipes(r);
			}
		}
		return savedRecipe;
	}

	private Collection<Category> checkFather(Category c, Recipe r,
			Collection<Category> categoriesFather) {
		Category father = c.getCategoryFather();

		if (father != null) {

			List<Recipe> recipes = (List<Recipe>) father.getRecipes();

			recipes.add(r);
			father.setRecipes(recipes);

			Category saved = categoryService.saveRecipeCategory(father);

			categoriesFather.add(saved);
			if (father.getCategoryFather() != null)
				checkFather(father.getCategoryFather(), r, categoriesFather);
		}
		return categoriesFather;
	}

	public void delete(Recipe recipe) {

		checkPrincipal(recipe);
		recipe.setPictures(new ArrayList<String>());
		Recipe savedRecipe = recipeRepository.save(recipe);
		User a = savedRecipe.getAuthor();
		a.getAuthoredRecipes().remove(savedRecipe);
		userService.saveRelationships(a);

		for (Step s : savedRecipe.getSteps())
			stepService.deleteWithoutRecipe(s);

		for (Quantified q : savedRecipe.getQuantifieds())
			quantifiedService.delete(q);

		for (Comment c : savedRecipe.getComments())
			commentService.delete(c, savedRecipe);

		for (Customer cu : savedRecipe.getLikedCustomers()) {
			Collection<Recipe> likedRecipes = cu.getLikedRecipes();
			likedRecipes.remove(savedRecipe);
			if (cu instanceof User) {
				User user = (User) cu;
				userService.saveRelationships(user);
			} else if (cu instanceof Nutritionist) {
				Nutritionist nutritionist = (Nutritionist) cu;
				nutritionistService.saveRelationships(nutritionist);
			}
		}

		for (Customer cu : savedRecipe.getDislikedCustomers()) {
			Collection<Recipe> dislikedRecipes = cu.getDislikedRecipes();
			dislikedRecipes.remove(savedRecipe);
			if (cu instanceof User) {
				User user = (User) cu;
				userService.saveRelationships(user);
			} else if (cu instanceof Nutritionist) {
				Nutritionist nutritionist = (Nutritionist) cu;
				nutritionistService.saveRelationships(nutritionist);
			}
		}

		for (Category ca : savedRecipe.getCategories()) {
			Collection<Recipe> recipes = ca.getRecipes();
			recipes.remove(savedRecipe);
			ca.setRecipes(recipes);
			categoryService.saveRelations(ca);
		}
		recipeRepository.delete(savedRecipe);
	}

	public Collection<Recipe> findAll() {
		return recipeRepository.findAll();
	}

	public Recipe findOne(int recipeId) {
		return recipeRepository.findOne(recipeId);
	}

	public void checkPrincipal(Recipe recipe) {
		Assert.notNull(recipe.getAuthor());
		Assert.isTrue(recipe.getAuthor().equals(userService.findByPrincipal()));
	}

	// Other Recipe methods ---------------------------

	public Collection<Recipe> findRecipeGroupedByCategory() {
		return recipeRepository.findRecipeGroupedByCategory();
	}

	public Collection<Recipe> findRecipeByKeywordTickerTitleOrSummary(
			String keyWord) {
		return recipeRepository
				.findRecipeByKeywordTickerTitleOrSummary(keyWord);
	}

	public Collection<Recipe> findAllRecipeByAuthor(int idAuthor) {
		return recipeRepository.findAllRecipeByAuthor(idAuthor);
	}

	public Collection<Recipe> findLatestRecipeFollowers(int id) {
		List<Recipe> listRecipe = new ArrayList<Recipe>();
		listRecipe.addAll(recipeRepository.findLatestRecipeFollowers(id));

		if (listRecipe.size() >= 3)
			listRecipe.subList(0, 3);

		return listRecipe;
	}

	private String createNewTicker() {
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		int year = cal.get(Calendar.YEAR) % 100;
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String sDay = day + "";
		String sMonth = month + "";
		String sYear = year + "";

		if (day < 10)
			sDay = "0" + day;
		if (month < 10)
			sMonth = "0" + month;
		if (year < 10)
			sYear = "0" + year;

		String randomLetters = getRandomText(4).replace("-", "");

		return sYear + "" + sMonth + "" + sDay + "-" + randomLetters;
	}

	public static String getRandomText(int len) {
		StringBuilder b = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < len; i++) {
			char c = (char) (65 + r.nextInt(25));
			b.append(c);
		}
		return b.toString();
	}

	public Collection<Recipe> findAllRecipesQualifiedsInContests() {
		Collection<Contest> collectionContest = contestService.findAll();
		Collection<Recipe> collQualifies = new ArrayList<Recipe>();
		for (Contest cont : collectionContest) {
			collQualifies
					.addAll(recipeRepository
							.findRecipesQualifiedsByContestOrderedByLikesAndDislikes(cont
									.getId()));
		}
		return collQualifies;
	}

	public Collection<Recipe> findRecipesQualifiedsByContestOrderedByLikesAndDislikes(
			int idContest) {
		return recipeRepository
				.findRecipesQualifiedsByContestOrderedByLikesAndDislikes(idContest);
	}

	public int setWinnersOfEveryContests() {
		int numberWinners = 0;
		Collection<Contest> contests = contestService.findAll();
		Date currentDate = new Date();

		for (Contest co : contests) {
			ArrayList<Recipe> winners = new ArrayList<Recipe>();

			if (co.getClosing().before(currentDate)) {
				List<Recipe> qualifieds = (List<Recipe>) recipeRepository
						.findRecipesQualifiedsByContestOrderedByLikesAndDislikes(co
								.getId());
				if (qualifieds.size() >= 3)
					qualifieds = qualifieds.subList(0, 3);

				for (Recipe q : qualifieds) {
					winners.add(q);
					numberWinners++;
				}
			}

			if (winners.size() > 0) {
				co.setWinners(winners);
				contestService.save(co);
			}
		}
		return numberWinners;
	}

	public Recipe saveRelations(Recipe rec) {
		return recipeRepository.save(rec);
	}

	private static boolean isUrl(String s) {
		String regex = "^(https?://)?(([\\w!~*'().&=+$%-]+: )?[\\w!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([\\w!~*'()-]+\\.)*([\\w^-][\\w-]{0,61})?[\\w]\\.[a-z]{2,6})(:[0-9]{1,4})?((/*)|(/+[\\w!~*'().;?:@&=+$,%#-]+)+/*)$";

		try {
			Pattern patt = Pattern.compile(regex);
			Matcher matcher = patt.matcher(s);
			return matcher.matches();

		} catch (RuntimeException e) {
			return false;
		}
	}

	public Collection<Recipe> findByPrincipal() {
		Collection<Recipe> result;
		User user;

		user = userService.findByPrincipal();
		result = recipeRepository.findAllRecipeByAuthor(user.getId());

		return result;
	}

	public Object[] calculateAvgStddevIngredientsOfRecipe() {
		return recipeRepository.calculateAvgStddevIngredientsOfRecipe();
	}

	public Object[] calculateMinAvgMaxRecipePerUser() {
		return recipeRepository.calculateMinAvgMaxRecipePerUser();
	}

	public Object[] calculateMinAvgMaxQualifiedsForContest() {
		return recipeRepository.calculateMinAvgMaxQualifiedsForContest();
	}
}
