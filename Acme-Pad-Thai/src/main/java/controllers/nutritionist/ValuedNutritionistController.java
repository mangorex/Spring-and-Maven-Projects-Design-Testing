package controllers.nutritionist;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.IngredientService;
import services.PropertyService;
import services.ValuedService;
import controllers.AbstractController;
import domain.Ingredient;
import domain.Property;
import domain.Valued;

@Controller
@RequestMapping("/valued/nutritionist")
public class ValuedNutritionistController extends AbstractController{
	// Services ---------------------------------------------------------------
	@Autowired	
	private ValuedService valuedService;
	@Autowired	
	private PropertyService propertyService;
	@Autowired	
	private IngredientService ingredientService;
	
	// Constructors -----------------------------------------------------------
	public ValuedNutritionistController(){
		super();
	}
	
	// Create ----------------------------------------------------------------
		@RequestMapping(value = "/create", method=RequestMethod.GET)
		public ModelAndView create(@RequestParam int ingredientId) {
			ModelAndView result;
			
			result = new ModelAndView("valued/edit");
			result.addObject("ingredientId",ingredientId);
			return result;
		}
		
		// Save ----------------------------------------------------------------
		@RequestMapping(value = "/edit", method=RequestMethod.POST,params="save")
		public ModelAndView save(@RequestParam("ingredientId") int ingredientId,@RequestParam("name") String name,@RequestParam("valued") String valued) {
		ModelAndView result;
		
		Ingredient ingredient=ingredientService.findOne(ingredientId);
		Property property= propertyService.create();
		
		boolean p=false;
		
		for(Property prop:propertyService.findAll()){
			if(prop.getName().equals(name))
			{
				p=true;
				property=propertyService.findOne(prop.getId());
			}
		}
		
		if(!p){
			property.setName(name);
			property=propertyService.save(property);
		}
		
		
		Valued val=valuedService.create(property, ingredient);
		val.setValue(valued);
		val.setIngredient(ingredientService.findOne(ingredientId));
		val=valuedService.save(val);
		
		property.getValueds().add(val);
		ingredient.getValueds().add(val);

		result = new ModelAndView("valued/edit");
		result.addObject("ingredientId",ingredientId);
		return result;
		}
		
		// Save2 ----------------------------------------------------------------
		@RequestMapping(value = "/mod", method=RequestMethod.POST,params="save2")
		public ModelAndView save(@Valid Valued valued, BindingResult binding) {
			ModelAndView result;
			if(binding.hasErrors()){
				result=new ModelAndView("redirect:../../ingredient/nutritionist/edit.do?ingredientId="+valued.getIngredient().getId());
			}
			else{
				try{
				valuedService.save(valued);
				result = new ModelAndView("redirect:../../ingredient/nutritionist/edit.do?ingredientId="+valued.getIngredient().getId());
				Collection<Ingredient> ingredients;
				
				ingredients=ingredientService.findAll();
				result.addObject("ingredients",ingredients);
				}catch(Throwable oops)
				{
					System.out.println(oops.toString());
					result=new ModelAndView("redirect:../../ingredient/nutritionist/edit.do?ingredientId="+valued.getIngredient().getId());
					result.addObject("messageERROR","Error");
				}
			}
			return result;
		}
		
		
		// Delete ----------------------------------------------------------------
		@RequestMapping(value = "/delete", method=RequestMethod.POST,params="delete")
		public ModelAndView delete(@RequestParam("ingredientId") int ingredientId, @RequestParam("valuedId") int propertyId) {
			ModelAndView result;
			
			Valued v=valuedService.findOne(auxValuedByPropertyAndIngredient(ingredientId,propertyId).getId());
			valuedService.delete(v);
			result = new ModelAndView("redirect:../../ingredient/nutritionist/edit.do?ingredientId="+ingredientId);
			
			Collection<Ingredient> ingredients;
			
			ingredients=ingredientService.findAll();
			result.addObject("ingredients",ingredients);
			return result;
		}
		
		// mod ----------------------------------------------------------------
		@RequestMapping(value = "/mod", method=RequestMethod.POST,params="mod")
		public ModelAndView mod(@RequestParam("ingredientId") int ingredientId, @RequestParam("valuedId") int propertyId) {
			ModelAndView result;
			result = new ModelAndView("valued/mod");
			
			result.addObject("valued",auxValuedByPropertyAndIngredient(ingredientId,propertyId));
			result.addObject("property",propertyService.findOne(propertyId));
			result.addObject("ingredientId",ingredientId);
			
			
			return result;
		}
		
		private Valued auxValuedByPropertyAndIngredient(int ingredientId,int propertyId){
			Ingredient ingredient=ingredientService.findOne(ingredientId);
			Property property=propertyService.findOne(propertyId);
			
			return valuedByPropertyAndIngredient(ingredient,property);
		}
		
		private Valued valuedByPropertyAndIngredient(Ingredient ingredient,Property property){
			Valued v=null;
		
			for(Valued valued:ingredient.getValueds()){
				if(valued.getIngredient().equals(ingredient) && valued.getProperty().equals(property)){
					v=valued;
				}
			}
			return v;
		}
		
	}
