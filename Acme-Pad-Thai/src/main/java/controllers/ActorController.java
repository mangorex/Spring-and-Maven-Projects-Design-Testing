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

import security.Authority;
import services.ActorService;
import services.CustomerService;
import domain.Actor;
import domain.Customer;
import domain.Nutritionist;
import domain.User;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private CustomerService customerService;

	// Constructors -----------------------------------------------------------

	public ActorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@Valid int actorId) {

		Actor actor = actorService.findOne(actorId);
		ModelAndView result = createEditModelAndView2(actor);
		return result;
	}

	@RequestMapping(value = "/createLogged", method = RequestMethod.GET)
	public ModelAndView createLogged(@Valid int actorId) {
		ModelAndView result;
		Actor actor = actorService.findOne(actorId);
		if (checkPrincipal() && checkPrincipal(actor)) {
			result = createEditModelAndView(actor);
		} else {
			result = createEditModelAndView2(actor);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "followUser")
	public ModelAndView follow(User user, BindingResult binding) {

		ModelAndView result;
		String redirectView;
		Customer a = customerService.findByPrincipal();

		if (binding.hasErrors()) {
			result = createEditModelAndView(user);
		} else {
			try {
				customerService.follow(a, user);
				redirectView = "redirect:../customer/followingList.do";
				result = new ModelAndView(redirectView);

			} catch (Throwable oops) {
				result = createEditModelAndView(user, "actor.commit.error2");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "followNutritionist")
	public ModelAndView follow(Nutritionist nut, BindingResult binding) {

		ModelAndView result;
		String redirectView;
		Customer a = customerService.findByPrincipal();

		if (binding.hasErrors()) {
			result = createEditModelAndView(nut);
		} else {
			try {
				customerService.follow(a, nut);
				redirectView = "redirect:../customer/followingList.do";
				result = new ModelAndView(redirectView);

			} catch (Throwable oops) {
				result = createEditModelAndView(nut, "actor.commit.error2");
			}

		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "unfollowUser")
	public ModelAndView unfollow(User user, BindingResult binding) {

		ModelAndView result;
		String redirectView;
		Customer a = customerService.findByPrincipal();

		if (binding.hasErrors()) {
			result = createEditModelAndView(user);
		} else {
			try {
				customerService.unfollow(a, user);
				redirectView = "redirect:../customer/followingList.do";
				result = new ModelAndView(redirectView);

			} catch (Throwable oops) {
				result = createEditModelAndView(user, "actor.commit.error2");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "unfollowNutritionist")
	public ModelAndView unfollow(Nutritionist nut, BindingResult binding) {

		ModelAndView result;
		String redirectView;
		Customer a = customerService.findByPrincipal();

		if (binding.hasErrors()) {
			result = createEditModelAndView(nut);
		} else {
			try {
				customerService.unfollow(a, nut);
				redirectView = "redirect:../customer/followingList.do";
				result = new ModelAndView(redirectView);

			} catch (Throwable oops) {
				result = createEditModelAndView(nut, "actor.commit.error2");
			}

		}
		return result;
	}

	private ModelAndView createEditModelAndView(Actor actor) {
		ModelAndView result;

		result = createEditModelAndView(actor, null);

		return result;
	}

	private ModelAndView createEditModelAndView2(Actor actor) {
		ModelAndView result;

		result = createEditModelAndView2(actor, null);

		return result;
	}

	private ModelAndView createEditModelAndView(Actor actor, String message) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);

		Customer a = customerService.findByPrincipal();
		if (a != null) {
			if (a.getFollowing().contains((Customer) actor)) {
				result.addObject("follows", true);
			} else {
				result.addObject("follows", false);
			}
		}

		if (a.getId() == actor.getId()) {
			result.addObject("same", true);
		} else {
			result.addObject("same", false);
		}

		result.addObject("messageERROR", message);

		return result;
	}

	private ModelAndView createEditModelAndView2(Actor actor, String message) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);

		result.addObject("follows", false);

		result.addObject("messageERROR", message);

		return result;
	}

	private boolean checkPrincipal() {

		boolean result = false;
		Actor actor = actorService.findByPrincipal();
		for (Authority a : actor.getUserAccount().getAuthorities()) {
			if (a.getAuthority().equals("USER")
					|| a.getAuthority().equals("NUTRITIONIST")) {
				result = true;
			}
		}

		return result;
	}
	
	private boolean checkPrincipal(Actor actor) {

		boolean result = false;

		for (Authority a : actor.getUserAccount().getAuthorities()) {
			if (a.getAuthority().equals("USER")
					|| a.getAuthority().equals("NUTRITIONIST")) {
				result = true;
			}
		}

		return result;
	}

}