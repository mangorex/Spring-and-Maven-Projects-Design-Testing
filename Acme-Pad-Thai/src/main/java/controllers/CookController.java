/* 
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CookService;
import domain.Cook;

@Controller
@RequestMapping("/cook")
public class CookController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CookService cookService;

	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public CookController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Cook cook = cookService.findByPrincipal();

		result = createEditModelAndView(cook);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Cook cook, BindingResult binding) {

		ModelAndView result;
		String redirectView;

		Cook a = cookService.findByPrincipal();

		if (binding.hasErrors()) {
			result = createEditModelAndView(cook);
		} else if (!a.getEmailAddress().equals(cook.getEmailAddress())
				&& actorService.existsEmail(cook.getEmailAddress())) {
			result = createEditModelAndView(cook, "actor.commit.error4");
		} else {
			try {
				cookService.save(cook);
				redirectView = "redirect:../cook/profile.do";
				result = new ModelAndView(redirectView);

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

		result = new ModelAndView("cook/edit");
		result.addObject("cook", cook);
		result.addObject("messageERROR", message);

		return result;
	}

}