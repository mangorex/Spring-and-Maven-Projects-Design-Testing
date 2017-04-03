package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;
import domain.MasterClass;

@Controller
@RequestMapping("/masterClass")
public class MasterClassActorController extends AbstractController {

	// Services ---------------------------

	@Autowired
	private ActorService actorService;

	// Constructors ------------------------
	public MasterClassActorController() {
		super();
	}

	@RequestMapping(value = "/registerList", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Actor a = actorService.findByPrincipal();
		Collection<MasterClass> cmc = a.getMasterClasses();

		result = new ModelAndView("masterClass/registerList");
		result.addObject("cmc", cmc);

		return result;

	}

}
