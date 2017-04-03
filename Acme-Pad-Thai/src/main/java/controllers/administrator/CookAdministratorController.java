package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CookService;
import controllers.AbstractController;
import domain.Cook;

@Controller
@RequestMapping("/cook/administrator")
public class CookAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CookService cookService;

	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public CookAdministratorController() {
		super();
	}

	// Creating -----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView register() {

		Cook cook = cookService.create();
		ModelAndView result = createEditModelAndView(cook);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@Valid Cook cook, BindingResult binding) {

		ModelAndView result;
		String redirectView;

		if (binding.hasErrors()) {

			result = createEditModelAndView(cook);
		} else {

			try {
				String username = cook.getUserAccount().getUsername();
				if (actorService.existsUsername(username)) {
					result = createEditModelAndView(cook, "actor.commit.error");
				} else if (actorService.existsEmail(cook.getEmailAddress())) {
					result = createEditModelAndView(cook, "actor.commit.error4");
				} else {

					cookService.save2(cook);
					redirectView = "redirect:../../welcome/index.do";
					result = new ModelAndView(redirectView);
				}
			} catch (Throwable oops) {
				result = createEditModelAndView(cook, "actor.commit.error2");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Cook cook) {
		ModelAndView result;

		result = createEditModelAndView(cook, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Cook cook, String message) {
		ModelAndView result;

		result = new ModelAndView("cook/administrator/register");
		result.addObject("cook", cook);
		result.addObject("messageERROR", message);

		return result;
	}

}
