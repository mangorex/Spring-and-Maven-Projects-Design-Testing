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

import services.PropertyService;
import controllers.AbstractController;
import domain.Property;

@Controller
@RequestMapping("/property/nutritionist")
public class PropertyNutritionistController extends AbstractController{
	// Services ---------------------------------------------------------------
	@Autowired	
	private PropertyService propertyService;
	
	// Constructors -----------------------------------------------------------
	public PropertyNutritionistController(){
		super();
	}
	
	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Property> properties;
		
		properties=propertyService.findAll();
		
		result = new ModelAndView("property/list");
		result.addObject("properties",properties);

		return result;
	}
	
	// Create ----------------------------------------------------------------
	@RequestMapping(value = "/create", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Property property=propertyService.create();
		
		result = new ModelAndView("property/edit");
		result.addObject("property", property);

		return result;
	}
	
	// Edit ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int propertyId) {
		ModelAndView result;
		Property property=propertyService.findOne(propertyId);
		
		result=createEditModelAndView(property);

		return result;
	}
	
	// Save ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method=RequestMethod.POST,params="save")
	public ModelAndView save(@Valid Property property, BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result=createEditModelAndView(property);
		}
		else{
			try{
			propertyService.save(property);
			result = new ModelAndView("redirect:list.do");
			}catch(Throwable oops)
			{
				System.out.println(oops.toString());
				result=createEditModelAndView(property,"property.commit.error");
			}
		}
		return result;
	}
	
	// Delete ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method=RequestMethod.POST,params="delete")
	public ModelAndView delete(@Valid Property property, BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result=createEditModelAndView(property);
		}
		else{
			
			if(property.getValueds().size()==0){
				try{
					propertyService.delete(property);
					result = new ModelAndView("redirect:list.do");
					}catch(Throwable oops)
					{
						result=createEditModelAndView(property,"property.commit.error");
					}
				}
			else{
				result=createEditModelAndView(property,"property.commit.ingredient.error");
				}
		}
		return result;
	}
			
	
	private ModelAndView createEditModelAndView(Property property)
	{
		return createEditModelAndView(property, null);
	}
	
	private ModelAndView createEditModelAndView(Property property,String message)
	{
		ModelAndView result;
		
		result=new ModelAndView("property/edit");
		result.addObject("property",property);
		result.addObject("messageERROR",message);
		
		return result;
	}
}
