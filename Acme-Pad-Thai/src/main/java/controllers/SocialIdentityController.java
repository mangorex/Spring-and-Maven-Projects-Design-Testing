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

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SocialIdentityService;
import domain.Actor;
import domain.SocialIdentity;

@Controller
@RequestMapping("/socialIdentity")
public class SocialIdentityController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SocialIdentityService socialIdentityService;
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public SocialIdentityController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@Valid Integer actorId) {
		ModelAndView result;
		Collection<SocialIdentity> socialIdentities;
		Actor a;

		a = actorService.findByPrincipal();

		socialIdentities = a.getSocialIdentities();

		result = new ModelAndView("socialIdentity/list");
		result.addObject("socialIdentities", socialIdentities);

		return result;
	}

	@RequestMapping(value = "/publicList", method = RequestMethod.GET)
	public ModelAndView publicList(@Valid Integer actorId) {
		ModelAndView result;
		Collection<SocialIdentity> socialIdentities;
		Actor a;

		a = actorService.findOne(actorId);

		socialIdentities = a.getSocialIdentities();

		result = new ModelAndView("socialIdentity/publicList");
		result.addObject("socialIdentities", socialIdentities);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		Actor a = actorService.findByPrincipal();
		SocialIdentity socialIdentity = socialIdentityService.create(a);
		ModelAndView result = createEditModelAndView(socialIdentity);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView modify(@Valid Integer socialIdentityId) {
		ModelAndView result;
		SocialIdentity a = socialIdentityService.findOne(socialIdentityId);

		result = createEditModelAndView(a);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid SocialIdentity socialIdentity,
			BindingResult binding) {

		ModelAndView result;
		String redirectView;

		if (binding.hasErrors()) {
			result = createEditModelAndView(socialIdentity);
		} else {

			try {
				socialIdentityService.save(socialIdentity);
				redirectView = "redirect:../socialIdentity/list.do";
				result = new ModelAndView(redirectView);

			} catch (Throwable oops) {
				result = createEditModelAndView(socialIdentity,
						"actor.commit.error2");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(SocialIdentity socialIdentity) {
		ModelAndView result;

		result = createEditModelAndView(socialIdentity, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			SocialIdentity socialIdentity, String message) {
		ModelAndView result;

		result = new ModelAndView("socialIdentity/edit");
		result.addObject("socialIdentity", socialIdentity);
		result.addObject("messageERROR", message);

		return result;
	}

}