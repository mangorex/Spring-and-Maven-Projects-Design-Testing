package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Cook;
import domain.MasterClass;

import services.ActorService;
import services.MasterClassService;

@Controller
@RequestMapping("/masterClass")
public class MasterClassController extends AbstractController{

	
	
	@Autowired
	private MasterClassService masterClassService;
	@Autowired
	private ActorService actorService;
	
	public MasterClassController(){
		super();
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(){
		
		ModelAndView result;
		
		
		Collection<MasterClass> cmc = masterClassService.findAll();
	
		result = new ModelAndView("masterClass/list");
		result.addObject("publicMasterClass", cmc);
		

		
		
		return result;
		
	}
	@RequestMapping(value = "/access", method = RequestMethod.GET)
	public ModelAndView viewList(@Valid Integer masterClassId) {
		ModelAndView result;

		MasterClass masterClass = masterClassService.findOne(masterClassId);
		result = new ModelAndView("masterClass/viewList");
		result.addObject("masterClassViews", masterClass);

		Actor a = actorService.findByPrincipal();

		if (a.getId() != masterClass.getCook().getId()) {

			result.addObject("isnotcook", true);
		} else {
			result.addObject("isnotcook", false);
		}

		if (masterClass.getActors().contains(a)) {
			result.addObject("isnotregistered", false);
		} else {
			result.addObject("isnotregistered", true);
		}

		result.addObject("id", masterClassId);

		return result;
	}

	// Register ---------------------------------------

	@RequestMapping(value = "/viewList", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@Valid MasterClass masterClass,
			BindingResult binding) {
		ModelAndView result;
		String redirectView;
		Actor a = actorService.findByPrincipal();

		try {
			masterClassService.register(masterClass, a);
			redirectView = "redirect:registerList.do";
			result = new ModelAndView(redirectView);

		} catch (Throwable oops) {
			result = createEditModelAndView(masterClass,
					"masterClass.commit.error.register");
		}

		return result;
	}

	@RequestMapping(value = "/viewList", method = RequestMethod.POST, params = "unregister")
	public ModelAndView unregister(@Valid MasterClass masterClass,
			BindingResult binding) {
		ModelAndView result;
		String redirectView;
		Actor a = actorService.findByPrincipal();

		try {
			masterClassService.unregister(masterClass, a);

			redirectView = "redirect:registerList.do";
			result = new ModelAndView(redirectView);
		} catch (Throwable oops) {
			result = createEditModelAndView(masterClass,
					"masterClass.commit.error.unregister");
		}

		return result;
	}

	private ModelAndView createEditModelAndView(MasterClass masterClass) {
		return createEditModelAndView(masterClass, null);
	}

	private ModelAndView createEditModelAndView(MasterClass masterClass,
			String message) {
		ModelAndView result;

		result = new ModelAndView("masterClass/edit");
		result.addObject("masterClass", masterClass);

		result.addObject("messageERROR", message);
		return result;
	}
}
