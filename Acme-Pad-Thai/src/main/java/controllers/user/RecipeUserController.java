package controllers.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ContestService;
import services.IngredientService;
import services.QuantifiedService;
import services.RecipeService;
import services.StepService;
import services.UserService;
import controllers.AbstractController;
import domain.Category;
import domain.Comment;
import domain.Contest;
import domain.Customer;
import domain.Ingredient;
import domain.Quantified;
import domain.Recipe;
import domain.Step;
import domain.User;
import forms.ContestRecipe;
import forms.FolderList;

@Controller
@RequestMapping("/recipe/user")
public class RecipeUserController extends AbstractController {
	// Services ---------------------------------------------------------------
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private ContestService contestService;
	@Autowired
	private StepService stepService;
	@Autowired
	private QuantifiedService quantifiedService;
	@Autowired
	private IngredientService ingredientService;
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;

	// Constructors -----------------------------------------------------------
	public RecipeUserController() {
		super();
	}

	// Listing ---------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Recipe> recipes;

		recipes = recipeService.findByPrincipal();

		result = new ModelAndView("recipe/list");
		result.addObject("recipes", recipes);
		result.addObject("requestUri", "recipe/user/list.do");

		return result;
	}

	// Creation -----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Recipe recipe = recipeService.create();
		Step step = stepService.create();
		Quantified quantified = quantifiedService.create();
		User u = userService.findByPrincipal();
		Collection<Recipe> recipes = new HashSet<Recipe>();
		recipes.addAll(u.getAuthoredRecipes());

		Collection<Category> categories = new HashSet<Category>();
		categories.addAll(categoryService.findAll());

		result = new ModelAndView("recipe/edit");
		result.addObject("recipe", recipe);
		result.addObject("step", step);
		result.addObject("invisible", true);
		result.addObject("recipes", recipes);
		result.addObject("quantified", quantified);
		result.addObject("categories", categories);
		return result;
	}

	// Edition -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int recipeId,
			@RequestParam int stepId, @RequestParam int quantifiedId) {
		ModelAndView result;
		Recipe recipe;
		Quantified quantified;

		Step step;

		recipe = recipeService.findOne(recipeId);
		Set<Ingredient> ingredients = new HashSet<Ingredient>();
		ingredients.addAll(ingredientService.findAll());

		Collection<Category> categories = new HashSet<Category>();
		categories.addAll(categoryService.findAll());
		Collection<Category> categoriesSelected = recipe.getCategories();

		if (stepId == 0)
			step = stepService.create(recipe);
		else
			step = stepService.findOne(stepId);

		if (quantifiedId == 0)
			quantified = quantifiedService.create(recipe);
		else
			quantified = quantifiedService.findOne(quantifiedId);

		Assert.notNull(recipe);
		result = createEditModelAndView(recipe);
		result.addObject("step", step);
		result.addObject("quantified", quantified);
		result.addObject("ingredients", ingredients);
		result.addObject("categories", categories);
		result.addObject("categoriesSelected", categoriesSelected);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Recipe recipe, BindingResult binding) {
		ModelAndView result;
		Date currentDate = new Date();
		Recipe savedRecipe = new Recipe();
		recipe.setUpdateDate(currentDate);

		if (binding.hasErrors()) {
			result = createEditModelAndView(recipe, "recipe.commit.error");
			result.addObject("invisible", true);
			result.addObject("categories", categoryService.findAll());
		} else {
			try {
				savedRecipe = recipeService.save(recipe);
				result = new ModelAndView("redirect:edit.do?recipeId="
						+ savedRecipe.getId() + "&stepId=0&quantifiedId=0");
			} catch (Throwable oops) {
				result = createEditModelAndView(recipe, "recipe.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveStep")
	public ModelAndView save(@Valid Step step, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(step.getRecipe(),
					"step.commit.error");
		} else {
			try {
				stepService.save(step);
				result = new ModelAndView("redirect:edit.do?recipeId="
						+ step.getRecipe().getId() + "&stepId=0&quantifiedId=0");
			} catch (Throwable oops) {
				result = createEditModelAndView(step.getRecipe(),
						"recipe.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Recipe recipe, BindingResult bindingResult) {
		ModelAndView result;

		try {
			recipeService.delete(recipe);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(recipe, "recipe.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteStep")
	public ModelAndView delete(Step step, BindingResult bindingResult) {
		ModelAndView result;

		try {
			stepService.delete(step);
			result = new ModelAndView("redirect:edit.do?recipeId="
					+ step.getRecipe().getId() + "&stepId=0&quantifiedId=0");
		} catch (Throwable oops) {
			result = createEditModelAndView(step.getRecipe(),
					"recipe.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveQuantified")
	public ModelAndView save(@Valid Quantified quantified, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(quantified.getRecipe());
		} else {
			try {
				if( isInteger( quantified.getQuantityDouble() ) )
				{
					quantified.setQuantityInteger(quantified.getQuantityDouble().intValue());
					quantified.setQuantityDouble(0.0);
				}
				quantifiedService.save(quantified);
				result = new ModelAndView("redirect:edit.do?recipeId="
					+ quantified.getRecipe().getId()
					+ "&stepId=0&quantifiedId=0");
			} catch (Throwable oops) {
				result = createEditModelAndView(quantified.getRecipe(),
						"recipe.commit.error");
			}
		}
		return result;
	}
	
	private boolean isInteger(Double d){
		boolean res=false;
		if(d % 1==0)
			res=true;
		return res;
	}

	// Qualifying ---------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "deleteQuantified")
	public ModelAndView delete(Quantified quantified,
			BindingResult bindingResult) {
		ModelAndView result;

		try {
			quantifiedService.delete(quantified);
			result = new ModelAndView("redirect:edit.do?recipeId="
					+ quantified.getRecipe().getId()
					+ "&stepId=0&quantifiedId=0");
		} catch (Throwable oops) {
			result = createEditModelAndView(quantified.getRecipe(),
					"recipe.commit.error");
		}

		return result;
	}

	// Ancillary methods
	// ------------------------------------------------------------------
	private ModelAndView createEditModelAndView(Recipe recipe) {
		return createEditModelAndView(recipe, null);
	}

	private ModelAndView createEditModelAndView(Recipe recipe,
			String messageERROR) {
		ModelAndView result;

		result = new ModelAndView("recipe/edit");
		result.addObject("recipe", recipe);
		result.addObject("messageERROR", messageERROR);
		result.addObject("step", new Step());
		result.addObject("quantified", new Quantified());

		return result;
	}

	@RequestMapping(value = "/qualify", method = RequestMethod.GET)
	public ModelAndView qualify(@RequestParam int recipeId) {
		ModelAndView result;
		Recipe recipe = recipeService.findOne(recipeId);
		List<Contest> contests = (List<Contest>) contestService.findAll();
		Assert.notNull(recipe);

		ContestRecipe contestRecipe = new ContestRecipe();
		contestRecipe.setRecips("" + recipeId);
		contestRecipe.setConts("" + contests.get(0).getId());
		contestRecipe.setTicker(recipe.getTicker());
		contestRecipe.setLikes(recipe.getLikedCustomers().size() + "");
		contestRecipe.setDislikes(recipe.getDislikedCustomers().size() + "");
		
		result = new ModelAndView("recipe/qualify");
		result.addObject("recipeId", recipeId);
		result.addObject("contests", contests);
		result.addObject("contestRecipe", contestRecipe);
		return result;
	}

	@RequestMapping(value = "/qualify", method = RequestMethod.POST, params = "savedQualified")
	public ModelAndView qualify(@RequestParam String conts,
			@RequestParam String recips,
			@ModelAttribute("contestRecipe") ContestRecipe contestRecipe,
			BindingResult binding) throws Exception {
		ModelAndView result;

		Recipe recipe = recipeService.findOne(Integer.parseInt(contestRecipe
				.getRecips()));
		Assert.notNull(recipe);
		int recipeId = recipe.getId();
		Contest contestQualified = contestService.findOne(Integer
				.parseInt(contestRecipe.getConts()));
		List<Contest> contestQualifieds = new ArrayList<Contest>();
		contestQualifieds.add(contestQualified);

		List<Contest> contests = (List<Contest>) contestService.findAll();
		String error;
		
		if (recipe.getLikedCustomers().size() >= 5
				&& recipe.getDislikedCustomers().size() == 0
				&& !recipe.getContests().contains(contestQualified)) {
			Recipe copyRecipe;
			copyRecipe = recipeService.create();
			copyRecipe.setAuthor(recipe.getAuthor());
			
			Collection<Category> copyCategories = new HashSet<Category>();
			copyCategories.addAll(recipe.getCategories());
			copyRecipe.setCategories(copyCategories);
			
			copyRecipe.setComments(new ArrayList<Comment>());
			copyRecipe.setCreationDate(recipe.getCreationDate());
			copyRecipe.setUpdateDate(recipe.getUpdateDate());
			
			Collection<Customer> copyLikedCustomers = new HashSet<Customer>();
			copyLikedCustomers.addAll(recipe.getLikedCustomers());
			copyRecipe.setLikedCustomers(copyLikedCustomers);
			
			Collection<Customer> copyDislikedCustomers = new HashSet<Customer>();
			copyDislikedCustomers.addAll(recipe.getDislikedCustomers());
			copyRecipe.setDislikedCustomers(copyDislikedCustomers);
			
			copyRecipe.setHints(recipe.getHints());
			copyRecipe.setSummary(recipe.getSummary());
			copyRecipe.setTitle(recipe.getTitle());
			copyRecipe.setPictures(recipe.getPictures());
			copyRecipe.setContests(contestQualifieds);
			copyRecipe.setSteps(new ArrayList<Step>());
			copyRecipe.setQuantifieds(new ArrayList<Quantified>());

			recipeService.saveCopy(copyRecipe,
					recipe.getSteps(), recipe.getQuantifieds(),
					recipe.getComments());

			error = "message.qualifying.correct";
			result = new ModelAndView("recipe/qualify");
			result.addObject("recipeId", recipeId);
			result.addObject("contests", contests);
			result.addObject("messageERROR", error);
			
		} else{
			error = "message.qualifying.error";
			result = new ModelAndView("recipe/qualify");
			result.addObject("recipeId", recipeId);
			result.addObject("contests", contests);
			result.addObject("messageERROR", error);
		}
		return result;
	}
}
