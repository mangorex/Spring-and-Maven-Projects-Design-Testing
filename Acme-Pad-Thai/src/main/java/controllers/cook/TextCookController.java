package controllers.cook;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TextService;
import controllers.AbstractController;
import domain.Text;

@Controller
@RequestMapping("/text")
public class TextCookController extends AbstractController{

	//Services ---------------------------
	
	@Autowired
	private TextService textService;
	
	
	
	//Constructors ------------------------
	public TextCookController(){
		super();
	}
	
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		
		Text t = textService.create();
		
		
		
		return createEditModelAndView(t);
	}
	
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Text text,BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(text);
		}else{
			try{
				textService.save(text);
				result = new ModelAndView("redirect:/learningMaterial/list.do");
				
			}catch(Throwable oops){
				result = createEditModelAndView(text, "text.commit.error.save");
			}
		}
		return result;
	}
	
	
	
	
	

	private ModelAndView createEditModelAndView(Text text) {
		
		return createEditModelAndView(text,null);
	}

	private ModelAndView createEditModelAndView(Text text,
			String message) {
		ModelAndView result;
		result = new ModelAndView("text/edit");
		result.addObject("text", text);

		result.addObject("messageERROR", message);

		return result;
	}
}
