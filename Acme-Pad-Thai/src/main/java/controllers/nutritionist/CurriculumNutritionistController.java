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

import services.CurriculumService;
import services.NutritionistService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.Endorser;
import domain.Nutritionist;

@Controller
@RequestMapping("/curriculum/nutritionist")
public class CurriculumNutritionistController extends AbstractController{
	// Services ---------------------------------------------------------------
	@Autowired	
	private CurriculumService curriculumService;
	@Autowired	
	private NutritionistService nutritionistService;
	
	// Constructors -----------------------------------------------------------
	public CurriculumNutritionistController(){
		super();
	}
	
	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Curriculum curriculum;
		
		curriculum=nutritionistService.findByPrincipal().getCurriculum();
		
		if(curriculum==null || curriculum.getId()==0){
			result = new ModelAndView("curriculum/list");
		}else{
		Collection<Endorser> endorsers=curriculum.getEndorsers();
		
		result = new ModelAndView("curriculum/list");
		result.addObject("curriculum",curriculum);
		result.addObject("endorsers",endorsers);
		}
		return result;
	}
	
	// Create ----------------------------------------------------------------
	@RequestMapping(value = "/create", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Nutritionist n=nutritionistService.findByPrincipal();
		Curriculum curriculum=curriculumService.create(n);
		
		result = new ModelAndView("curriculum/edit");
		result.addObject("curriculum", curriculum);

		return result;
	}
	
	// Edit ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int curriculumId) {
		ModelAndView result;
		Curriculum curriculum=curriculumService.findOne(curriculumId);
		
		result=createEditModelAndView(curriculum);

		return result;
	}
	
	// Save ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method=RequestMethod.POST,params="save")
	public ModelAndView save(@Valid Curriculum curriculum, BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result=createEditModelAndView(curriculum);
		}
		else{
			try{
			curriculumService.save(curriculum);
			result = new ModelAndView("redirect:list.do");
			}catch(Throwable oops)
			{
				System.out.println(oops.toString());
				result=createEditModelAndView(curriculum,"curriculum.commit.error");
			}
		}
		return result;
	}
	
	// Delete ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method=RequestMethod.POST,params="delete")
	public ModelAndView delete(@Valid Curriculum curriculum, BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result=createEditModelAndView(curriculum);
		}
		else{
			try{
			curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:list.do");
			}catch(Throwable oops)
			{
				result=createEditModelAndView(curriculum,"curriculum.commit.error");
			}
		}
		return result;
	}
	
	private ModelAndView createEditModelAndView(Curriculum curriculum)
	{
		return createEditModelAndView(curriculum, null);
	}
	
	private ModelAndView createEditModelAndView(Curriculum curriculum,String message)
	{
		ModelAndView result;
		
		result=new ModelAndView("curriculum/edit");
		result.addObject("curriculum",curriculum);
		result.addObject("messageERROR",message);
		
		return result;
	}
}
