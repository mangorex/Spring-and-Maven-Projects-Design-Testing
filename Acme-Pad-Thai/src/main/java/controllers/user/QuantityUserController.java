package controllers.user;

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
import services.QuantifiedService;
import services.UserService;
import controllers.AbstractController;
import domain.Ingredient;
import domain.Quantified;
import domain.Recipe;
import domain.User;

@Controller
@RequestMapping("/quantified/user")
public class QuantityUserController extends AbstractController{
	// Services ---------------------------------------------------------------
	@Autowired	
	private UserService userService;
	@Autowired	
	private IngredientService ingredientService;
	@Autowired	
	private QuantifiedService quantifiedService;
	
	// Constructors -----------------------------------------------------------
	public QuantityUserController(){
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Quantified> qua=new HashSet<Quantified>();
		User u= userService.findByPrincipal();
		
		for(Recipe r:u.getAuthoredRecipes()){
			qua.addAll(r.getQuantifieds());
		}

		result = new ModelAndView("quantified/list");
		result.addObject("quantifieds",qua);
		
		return result;
	}
	
	// Create ----------------------------------------------------------------
		@RequestMapping(value = "/create", method=RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Quantified quantified=new Quantified();
			User u=userService.findByPrincipal();
			Collection<Recipe> recipes=new HashSet<Recipe>();
			Collection<Ingredient> ingredients=new HashSet<Ingredient>();
			
			recipes.addAll(u.getAuthoredRecipes());
			ingredients.addAll(ingredientService.findAll());
			
			result = new ModelAndView("quantified/edit");
			result.addObject("quantified", quantified);
			result.addObject("ingredients", ingredients);
			result.addObject("recipes", recipes);

			return result;
		}
		
		// Edit ----------------------------------------------------------------
		@RequestMapping(value = "/commit", method=RequestMethod.GET)
		public ModelAndView edit(@RequestParam int quantifiedId) {
			ModelAndView result;
			Quantified quantified=quantifiedService.findOne(quantifiedId);
			
			if(quantified.getQuantityInteger()!=null){
				String s=quantified.getIngredient()+".0";
				Double res=Double.parseDouble(s);
				quantified.setQuantityDouble(res);
			}
			
			result = new ModelAndView("quantified/commit");
			result.addObject("quantified", quantified);

			return result;
		}
		
		
		// Save ----------------------------------------------------------------
		@RequestMapping(value = "/edit", method=RequestMethod.POST, params="save")
		public ModelAndView save(@Valid Quantified quantified, BindingResult binding) {
			ModelAndView result;
			if(binding.hasErrors()){
				System.out.println(binding.toString());
				result = new ModelAndView("quantified/edit");
				result.addObject("quantified",quantified);
			}
			else{
				try{
				quantified=formatearCantidad(quantified);
				if(quantified==null){
					result=createEditModelAndView(new Quantified(),"quantified.commit.error");
				}else{
					
					quantifiedService.save(quantified);
					
					Collection<Quantified> qua=new HashSet<Quantified>();
					User u= userService.findByPrincipal();
					
					for(Recipe r:u.getAuthoredRecipes()){
						qua.addAll(r.getQuantifieds());
					}
	
					result = new ModelAndView("quantified/list");
					result.addObject("quantifieds",qua);
				}
				}catch(Throwable oops)
				{
					result = new ModelAndView("quantified/edit");
					result.addObject("quantified",quantified);
					result.addObject("message","quantified.commit.error");
				}
			}
			return result;
		}

		// Save ----------------------------------------------------------------
		@RequestMapping(value = "/commit", method=RequestMethod.POST, params="save2")
		public ModelAndView save2(@Valid Quantified quantified, BindingResult binding) {
			ModelAndView result;
			if(binding.hasErrors()){
				result = new ModelAndView("quantified/edit");
				result.addObject("quantified",quantified);
			}
			else{
				try{
				quantified=formatearCantidad(quantified);
				
				if(quantified==null){
					result=createEditModelAndView(new Quantified(),"quantified.commit.error");
				}
				
				quantifiedService.save(quantified);
				
				Collection<Quantified> qua=new HashSet<Quantified>();
				User u= userService.findByPrincipal();
				
				for(Recipe r:u.getAuthoredRecipes()){
					qua.addAll(r.getQuantifieds());
				}

				result = new ModelAndView("quantified/list");
				result.addObject("quantifieds",qua);
				}catch(Throwable oops)
				{
					result = new ModelAndView("quantified/edit");
					result.addObject("quantified",quantified);
					result.addObject("message","quantified.commit.error");
				}
			}
			return result;
		}
		
		// Delete ----------------------------------------------------------------
		@RequestMapping(value = "/commit", method=RequestMethod.POST,params="delete")
		public ModelAndView delete(@Valid Quantified quantified, BindingResult binding) {
			ModelAndView result;
			if(binding.hasErrors()){
				result=createEditModelAndView(quantified);
			}
			else{
				try{
				quantifiedService.delete(quantified);
				
				Collection<Quantified> qua=new HashSet<Quantified>();
				User u= userService.findByPrincipal();
				
				for(Recipe r:u.getAuthoredRecipes()){
					qua.addAll(r.getQuantifieds());
				}

				result = new ModelAndView("quantified/list");
				result.addObject("quantifieds",qua);
				}catch(Throwable oops)
				{
					result=createEditModelAndView(quantified,"quantified.commit.error");
				}
			}
			return result;
		}
		
		private ModelAndView createEditModelAndView(Quantified quantified)
		{
			return createEditModelAndView(quantified, null);
		}
		
		private ModelAndView createEditModelAndView(Quantified quantified,String message)
		{
			ModelAndView result;
			
			result=new ModelAndView("quantified/edit");
			result.addObject("quantified",quantified);
			result.addObject("messageERROR",message);
			
			return result;
		}

		private Quantified formatearCantidad(Quantified quantified) {
			if(quantified.getUnit().equals("grams") || quantified.getUnit().equals("millilitres") || quantified.getUnit().equals("spoons") || quantified.getUnit().equals("cups") || quantified.getUnit().equals("pieces")){
				
				if(esEntero(quantified.getQuantityDouble())){
					
					String c=quantified.getQuantityDouble()+"";
					c=c.substring(0, c.length()-2);
					Integer res=Integer.parseInt(c);
					quantified.setQuantityInteger(res);
					quantified.setQuantityDouble(null);
					
				}else { quantified=null; }
			}
			
			return quantified;
		}
		
		private boolean esEntero(Double d){
			boolean res=false;
			if(d % 1==0)
				res=true;
			return res;
		}
}
