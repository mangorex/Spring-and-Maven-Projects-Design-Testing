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

import services.EndorserService;
import services.NutritionistService;
import controllers.AbstractController;
import domain.Endorser;
import domain.Nutritionist;

@Controller
@RequestMapping("/endorser/nutritionist")
public class EndorserNutritionistController extends AbstractController{
	// Services ---------------------------------------------------------------
	@Autowired	
	private EndorserService endorserService;
	@Autowired	
	private NutritionistService nutritionistService;

	
	// Constructors -----------------------------------------------------------
	public EndorserNutritionistController(){
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Endorser> endorsers;
		
		endorsers=endorserService.findAll();
		
		result = new ModelAndView("endorser/list");
		result.addObject("endorsers",endorsers);

		return result;
	}
	
	// Create ----------------------------------------------------------------
		@RequestMapping(value = "/create", method=RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Nutritionist n=nutritionistService.findByPrincipal();
			
			if(n.getCurriculum()==null || n.getCurriculum().getId()==0){
				result = new ModelAndView("endorser/list");
			}else{
			Endorser endorser=endorserService.create(n.getCurriculum());
			
			result = new ModelAndView("endorser/edit");
			result.addObject("endorser", endorser);
			}
			return result;
		}
		
	// Edit ----------------------------------------------------------------
		@RequestMapping(value = "/edit", method=RequestMethod.GET)
		public ModelAndView edit(@RequestParam int endorserId) {
			ModelAndView result;
			Endorser endorser=endorserService.findOne(endorserId);
					
			result=createEditModelAndView(endorser);

			return result;
		}
	
		// Save ----------------------------------------------------------------
			@RequestMapping(value = "/edit", method=RequestMethod.POST,params="save")
			public ModelAndView save(@Valid Endorser endorser, BindingResult binding) {
				ModelAndView result;
				if(binding.hasErrors()){
					result=createEditModelAndView(endorser);
				}
				else{						
					try{
					endorserService.save(endorser);
					result = new ModelAndView("redirect:list.do");
					}catch(Throwable oops)
					{
						result=createEditModelAndView(endorser,"endorser.commit.error");
					}
				}	
				return result;
			}
	
			
			// Delete ----------------------------------------------------------------
			@RequestMapping(value = "/edit", method=RequestMethod.POST,params="delete")
			public ModelAndView delete(@Valid Endorser endorser, BindingResult binding) {
				ModelAndView result;
				if(binding.hasErrors()){
					result=createEditModelAndView(endorser);
				}
				else{
					try{
					endorserService.delete(endorser);
					result = new ModelAndView("redirect:list.do");
					}catch(Throwable oops)
					{
						result=createEditModelAndView(endorser,"endorser.commit.error");
					}
				}
				return result;
			}
		private ModelAndView createEditModelAndView(Endorser endorser)
		{
			return createEditModelAndView(endorser, null);
		}
		
		private ModelAndView createEditModelAndView(Endorser endorser,String message)
		{
			ModelAndView result;
			
			result=new ModelAndView("endorser/edit");
			result.addObject("endorser",endorser);
			result.addObject("messageERROR",message);
			
			return result;
		}
}
