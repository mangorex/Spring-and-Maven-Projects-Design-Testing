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
import services.SponsorService;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public SponsorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView register() {

		Sponsor user = sponsorService.create();
		ModelAndView result = createEditModelAndView(user);
		return result;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Sponsor sponsor = sponsorService.findByPrincipal();

		result = createEditModelAndView(sponsor);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@Valid Sponsor sponsor, BindingResult binding) {

		ModelAndView result;
		String redirectView;
		if (binding.hasErrors()) {
			result = createEditModelAndView(sponsor);
		} else {
			try {
				String username = sponsor.getUserAccount().getUsername();
				if (actorService.existsUsername(username)) {
					result = createEditModelAndView(sponsor,
							"actor.commit.error");
				} else if (actorService.existsEmail(sponsor.getEmailAddress())) {
					result = createEditModelAndView(sponsor,
							"actor.commit.error4");
				} else {
					sponsorService.save2(sponsor);
					redirectView = "redirect:../security/login.do";
					result = new ModelAndView(redirectView);
				}
			} catch (Throwable oops) {
				result = createEditModelAndView(sponsor, "actor.commit.error2");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Sponsor sponsor, BindingResult binding) {

		ModelAndView result;
		String redirectView;
		Sponsor a = sponsorService.findByPrincipal();
		if (binding.hasErrors()) {
			result = createEditModelAndView(sponsor);
		} else if (!a.getEmailAddress().equals(sponsor.getEmailAddress())
				&& actorService.existsEmail(sponsor.getEmailAddress())) {
			result = createEditModelAndView2(sponsor, "actor.commit.error4");
		} else {

			try {

				sponsorService.save(sponsor);
				redirectView = "redirect:../sponsor/profile.do";
				result = new ModelAndView(redirectView);

			} catch (Throwable oops) {
				result = createEditModelAndView2(sponsor, "actor.commit.error2");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Sponsor sponsor) {
		ModelAndView result;

		if (sponsor.getId() == 0) {
			result = createEditModelAndView(sponsor, null);
		} else {
			result = createEditModelAndView2(sponsor, null);
		}
		return result;

	}

	protected ModelAndView createEditModelAndView(Sponsor sponsor,
			String message) {
		ModelAndView result;

		result = new ModelAndView("sponsor/register");
		result.addObject("sponsor", sponsor);
		result.addObject("messageERROR", message);

		return result;
	}

	protected ModelAndView createEditModelAndView2(Sponsor sponsor,
			String message) {
		ModelAndView result;

		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsor", sponsor);
		result.addObject("messageERROR", message);

		return result;
	}

}