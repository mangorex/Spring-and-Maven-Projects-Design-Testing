package controllers.cook;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PresentationService;
import controllers.AbstractController;
import domain.Presentation;

@Controller
@RequestMapping("/presentation")
public class PresentationCookController extends AbstractController{

	//Services ---------------------------
	
	@Autowired
	private PresentationService presentationService;
	
	
	
	//Constructors ------------------------
	public PresentationCookController(){
		super();
	}
	
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		
		Presentation p = presentationService.create();
		p.setPath("https://es.slideshare.net/");
		
		
		return createEditModelAndView(p);
	}
	
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Presentation presentation,BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(presentation);
		}else{
			try{
				presentationService.save(presentation);
				result = new ModelAndView("redirect:../masterClass/cook/learningMaterial.do");
				
			}catch(Throwable oops){
				result = createEditModelAndView(presentation, "presentation.commit.error.save");
			}
		}
		return result;
	}
	
	
	
	
	

	private ModelAndView createEditModelAndView(Presentation presentation) {
		
		return createEditModelAndView(presentation,null);
	}

	private ModelAndView createEditModelAndView(Presentation presentation,
			String message) {
		ModelAndView result;
		result = new ModelAndView("presentation/edit");
		result.addObject("presentation", presentation);

		result.addObject("messageERROR", message);

		return result;
	}
}
