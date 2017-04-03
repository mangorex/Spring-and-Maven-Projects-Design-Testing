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
import services.AdministratorService;
import domain.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Administrator admin = administratorService.findByPrincipal();

		result = createEditModelAndView(admin);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Administrator admin, BindingResult binding) {

		ModelAndView result;
		String redirectView;

		Administrator a = administratorService.findByPrincipal();

		if (binding.hasErrors()) {
			result = createEditModelAndView(admin);
		} else if (!a.getEmailAddress().equals(admin.getEmailAddress())
				&& actorService.existsEmail(admin.getEmailAddress())) {
			result = createEditModelAndView(admin, "actor.commit.error4");
		} else {
			try {
				administratorService.save(admin);
				redirectView = "redirect:../administrator/profile.do";
				result = new ModelAndView(redirectView);

			} catch (Throwable oops) {
				result = createEditModelAndView(admin, "actor.commit.error2");
			}
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(Administrator admin) {
		ModelAndView result;

		result = createEditModelAndView(admin, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Administrator admin,
			String message) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", admin);
		result.addObject("messageERROR", message);

		return result;
	}

}