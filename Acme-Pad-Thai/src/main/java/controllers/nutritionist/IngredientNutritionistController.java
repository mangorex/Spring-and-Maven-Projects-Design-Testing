package controllers.nutritionist;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.IngredientService;
import services.NutritionistService;
import controllers.AbstractController;
import domain.Ingredient;
import domain.Nutritionist;
import domain.Property;
import domain.Valued;

@Controller
@RequestMapping("/ingredient/nutritionist")
public class IngredientNutritionistController extends AbstractController{
	// Services ---------------------------------------------------------------
	@Autowired	
	private IngredientService ingredientService;
	@Autowired	
	private NutritionistService nutritionistService;
	
	// Constructors -----------------------------------------------------------
	public IngredientNutritionistController(){
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Ingredient> ingredients;
		
		ingredients=ingredientService.findAll();
		
		result = new ModelAndView("ingredient/list");
		result.addObject("ingredients",ingredients);

		return result;
	}
	
	// Create ----------------------------------------------------------------
	@RequestMapping(value = "/create", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Nutritionist n=nutritionistService.findByPrincipal();
		Ingredient ingredient=ingredientService.create(n);
		
		result = new ModelAndView("ingredient/edit");
		result.addObject("ingredient", ingredient);
		result.addObject("hayProperties", 0);
		result.addObject("Properties", new HashSet<Property>());

		return result;
	}
	
	// Edit ----------------------------------------------------------------
		@RequestMapping(value = "/edit", method=RequestMethod.GET)
		public ModelAndView edit(@RequestParam int ingredientId) {
			ModelAndView result;
			Ingredient ingredient=ingredientService.findOne(ingredientId);
			Collection<Property> cp= new HashSet<Property>();
			
			result=createEditModelAndView(ingredient);
			
			if(ingredient.getValueds().size()>0){
				for(Valued v: ingredient.getValueds()){
					cp.add(v.getProperty());
				}
				result.addObject("hayProperties", 1);
			}else { result.addObject("hayProperties", 0); }

			result.addObject("Properties", cp);
			
			return result;
		}
		
		// Save ----------------------------------------------------------------
		@RequestMapping(value = "/edit", method=RequestMethod.POST,params="save")
		public ModelAndView save(@Valid Ingredient ingredient, BindingResult binding) {
			ModelAndView result;
			if(binding.hasErrors()){
				result=createEditModelAndView(ingredient);
			}
			else{
				try{
				ingredientService.save(ingredient);
				result = new ModelAndView("redirect:list.do");
				}catch(Throwable oops)
				{
					System.out.println(oops.toString());
					result=createEditModelAndView(ingredient,"ingredient.commit.error");
				}
			}
			return result;
		}
		
		// Save ----------------------------------------------------------------
		@RequestMapping(value = "/edit", method=RequestMethod.POST,params="save2")
		public ModelAndView save2(@Valid Ingredient ingredient, BindingResult binding) {
			ModelAndView result;
			if(binding.hasErrors()){
				result=createEditModelAndView(ingredient);
			}
			else{
				try{
				Ingredient ing=ingredientService.save(ingredient);
				result = new ModelAndView("valued/edit");
				result.addObject("ingredientId",ing.getId());
				}catch(Throwable oops)
				{
					System.out.println(oops.toString());
					result=createEditModelAndView(ingredient,"ingredient.commit.error");
				}
			}
			return result;
		}
		
		// Delete ----------------------------------------------------------------
		@RequestMapping(value = "/edit", method=RequestMethod.POST,params="delete")
		public ModelAndView delete(@Valid Ingredient ingredient, BindingResult binding) {
			ModelAndView result;
			if(binding.hasErrors()){
				result=createEditModelAndView(ingredient);
			}
			else{
				try{
				ingredientService.delete(ingredient);
				result = new ModelAndView("redirect:list.do");
				}catch(Throwable oops)
				{
					result=createEditModelAndView(ingredient,"ingredient.commit.error");
				}
			}
			return result;
		}
		
		private ModelAndView createEditModelAndView(Ingredient ingredient)
		{
			return createEditModelAndView(ingredient, null);
		}
		
		private ModelAndView createEditModelAndView(Ingredient ingredient,String message)
		{
			ModelAndView result;
			
			result=new ModelAndView("ingredient/edit");
			result.addObject("ingredient",ingredient);
			result.addObject("messageERROR",message);
			
			return result;
		}
}
