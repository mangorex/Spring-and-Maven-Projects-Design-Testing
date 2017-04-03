/* 
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.RecipeService;
import controllers.AbstractController;
import domain.Customer;
import domain.Recipe;

@Controller
@RequestMapping("/recipe/customer")
public class RecipeCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CustomerService customerService;

	@Autowired
	private RecipeService recipeService;
	
	// Constructors -----------------------------------------------------------

	public RecipeCustomerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/latest", method = RequestMethod.GET)
	public ModelAndView latest() {
		ModelAndView result;
		Collection<Recipe> recipes;
		Customer a= customerService.findByPrincipal();

		recipes = recipeService.findLatestRecipeFollowers(a.getId());

		result = new ModelAndView("recipe/latest");
		result.addObject("recipes", recipes);

		return result;
	}

}