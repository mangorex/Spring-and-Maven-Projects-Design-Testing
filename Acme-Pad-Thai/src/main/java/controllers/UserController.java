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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.UserService;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private UserService userService;

	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public UserController() {
		super();
	}

	// Searching
	// ----------------------------------------------------------------

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam String keyword) {
		ModelAndView result;
		Collection<User> users;
		users = userService.findUserByKeywordName(keyword);

		result = new ModelAndView("user/search");
		result.addObject("users", users);
		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> users;

		users = userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("users", users);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView register() {

		User user = userService.create();
		ModelAndView result = createEditModelAndView(user);
		return result;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		User user = userService.findByPrincipal();

		result = createEditModelAndView(user);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@Valid User user, BindingResult binding) {

		ModelAndView result;
		String redirectView;

		if (binding.hasErrors()) {

			result = createEditModelAndView(user);
		} else {

			try {
				String username = user.getUserAccount().getUsername();
				if (actorService.existsUsername(username)) {
					result = createEditModelAndView(user, "actor.commit.error");
				} else if (actorService.existsEmail(user.getEmailAddress())) {
					result = createEditModelAndView(user, "actor.commit.error4");
				} else {

					userService.save2(user);
					redirectView = "redirect:../security/login.do";
					result = new ModelAndView(redirectView);
				}
			} catch (Throwable oops) {
				result = createEditModelAndView(user, "actor.commit.error2");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid User user, BindingResult binding) {

		ModelAndView result;
		String redirectView;

		User a = userService.findByPrincipal();

		if (binding.hasErrors()) {
			result = createEditModelAndView(user);
		} else if (!a.getEmailAddress().equals(user.getEmailAddress())
				&& actorService.existsEmail(user.getEmailAddress())) {
			result = createEditModelAndView2(user, "actor.commit.error4");
		} else {
			try {
				userService.save(user);
				redirectView = "redirect:../user/profile.do";
				result = new ModelAndView(redirectView);

			} catch (Throwable oops) {
				result = createEditModelAndView2(user, "actor.commit.error2");
			}
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(User user) {
		ModelAndView result;

		if (user.getId() == 0) {
			result = createEditModelAndView(user, null);
		} else {
			result = createEditModelAndView2(user, null);
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(User user, String message) {
		ModelAndView result;

		result = new ModelAndView("user/register");
		result.addObject("user", user);
		result.addObject("messageERROR", message);

		return result;
	}

	protected ModelAndView createEditModelAndView2(User user, String message) {
		ModelAndView result;

		result = new ModelAndView("user/edit");
		result.addObject("user", user);
		result.addObject("messageERROR", message);

		return result;
	}

}