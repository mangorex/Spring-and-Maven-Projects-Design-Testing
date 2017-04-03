package controllers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CampaignService;
import services.CategoryService;
import services.CommentService;
import services.CustomerService;
import services.RecipeService;

import domain.Campaign;
import domain.Category;
import domain.Comment;
import domain.Recipe;

@Controller
@RequestMapping("/recipe/")
public class RecipeController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommentService commentService;
	@Autowired 
	private CampaignService campaignService;

	// Constructors -----------------------------------------------------------
	public RecipeController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/browse", method = RequestMethod.GET)
	public ModelAndView browse() {
		ModelAndView result;
		Collection<Recipe> recipes;

		recipes = recipeService.findRecipeGroupedByCategory();

		result = new ModelAndView("recipe/browse");
		result.addObject("recipes", recipes);
		result.addObject("requestUri", "recipe/browse.do");
		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam String keyword) {
		ModelAndView result;
		Collection<Recipe> recipes;
		recipes = recipeService
				.findRecipeByKeywordTickerTitleOrSummary(keyword);

		result = new ModelAndView("recipe/search");
		result.addObject("recipes", recipes);
		return result;
	}

	// Listing ---------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Recipe> recipes;

		recipes = recipeService.findByPrincipal();

		result = new ModelAndView("recipe/list");
		result.addObject("recipes", recipes);
		result.addObject("requestUri", "recipe/list.do");

		return result;
	}
	
	@RequestMapping(value = "/listPublic", method = RequestMethod.GET)
	public ModelAndView listPublic(@RequestParam int userId) {
		ModelAndView result;
		Collection<Recipe> recipes;

		recipes = recipeService.findAllRecipeByAuthor(userId);

		result = new ModelAndView("recipe/listPublic");
		result.addObject("recipes", recipes);
		return result;
	}

	// View ---------------------------------------------------
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int recipeId,
			@RequestParam String isCustomer) {
		ModelAndView result;
		Recipe recipe;
		Comment comment = new Comment();
		Collection<Campaign> cc=new HashSet<Campaign>();
		cc.addAll(campaignService.findAll());
		
		String banner=bannerAleatorio(getActiveBanners(cc));
		
		recipe = recipeService.findOne(recipeId);
		if (isCustomer.equals("true"))
			comment = commentService.create(recipe);
		Collection<Category> categories = new HashSet<Category>();
		categories.addAll(recipe.getCategories());

		result = new ModelAndView("recipe/view");
		result.addObject("recipe", recipe);
		result.addObject("comment", comment);
		result.addObject("requestUri", "recipe/view.do");
		result.addObject("banner", banner);
		
		return result;
	}

	// Edit
	@RequestMapping(value = "/view", method = RequestMethod.POST, params = "saveLike")
	public ModelAndView saveLike(@Valid Recipe recipe, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(recipe);
		} else {
			try {
				recipeService.saveLikedCustomer(recipe);
				result = new ModelAndView("redirect:view.do?recipeId="
						+ recipe.getId() + "&isCustomer=true");
			} catch (Throwable oops) {
				result = createEditModelAndView(recipe, "recipe.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.POST, params = "saveDislike")
	public ModelAndView saveDislike(@Valid Recipe recipe, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(recipe);
		} else {
			try {
				recipeService.saveDisLikedCustomer(recipe);
				result = new ModelAndView("redirect:view.do?recipeId="
						+ recipe.getId() + "&isCustomer=true");
			} catch (Throwable oops) {
				result = createEditModelAndView(recipe, "recipe.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.POST, params = "saveComment")
	public ModelAndView saveComment(@Valid Comment comment,
			BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(comment.getRecipe());
		} else {
			try {
				commentService.save(comment);
				// recipeService.saveLikedCustomer(recipe);
				result = new ModelAndView("redirect:view.do?recipeId="
						+ comment.getRecipe().getId() + "&isCustomer=true");
			} catch (Throwable oops) {
				result = createEditModelAndView(comment.getRecipe(),
						"recipe.commit.error");
			}
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

		return result;
	}

	private String bannerAleatorio(Collection<String> activeBanners) {
		String b="";
		Random rnd = new Random();
		LinkedList<String> ls=new LinkedList<String>();
		ls.addAll(activeBanners);
		
		int num=(int)(rnd.nextDouble() * ls.size() + 0);
		if(ls.size()>0){
			b=ls.get(num);
		}
		else{	b="ERROR";}
		
		bannerDisplayed(b);
		return b;
	}

	private Collection<String> getActiveBanners(Collection<Campaign> cc){
		Collection<String> cs= new LinkedList<String>();
		Date d= new Date();
		
		for(Campaign c: cc){
			if(c.getEndDate().after(d) && c.getMaxBannersDisplayed()>c.getNumBanners()){
				cs.addAll(Arrays.asList(c.getBanners().split(",")));
			}
		}
		
		return cs;
	}
	
	private void bannerDisplayed(String banner){
		Collection<Campaign> cc=new HashSet<Campaign>();
		cc.addAll(campaignService.findAll());
		
		for(Campaign c:cc){
			if(containBanner(c,banner)){
				c.setNumBanners(c.getNumBanners()+1);
				campaignService.save(campaignService.findOne(c.getId()));
			}
		}
	}
	
	private boolean containBanner(Campaign c,String banner){
		boolean res=false;
		Collection<String> cs= new LinkedList<String>();
		cs.addAll(Arrays.asList(c.getBanners().split(",")));
		
		for(String s:cs){
			if(s.equals(banner)){
				res=true;
			}
		}
		return res;
	}
}
