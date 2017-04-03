/* CurriculumCustomerController.java
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
import services.NutritionistService;
import domain.Nutritionist;

@Controller
@RequestMapping("/nutritionist")
public class NutritionistController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private NutritionistService nutritionistService;
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public NutritionistController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView register() {

		Nutritionist nutritionist = nutritionistService.create();
		ModelAndView result = createEditModelAndView(nutritionist);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "register")
	public ModelAndView save(@Valid Nutritionist nutritionist,
			BindingResult binding) {

		ModelAndView result;
		String redirectView;
		if (binding.hasErrors()) {
			result = createEditModelAndView(nutritionist);
		} else {

			try {
				String nutritionistname = nutritionist.getUserAccount()
						.getUsername();
				if (actorService.existsUsername(nutritionistname)) {
					result = createEditModelAndView(nutritionist,
							"actor.commit.error");
				} else if (actorService.existsEmail(nutritionist
						.getEmailAddress())) {
					result = createEditModelAndView(nutritionist,
							"actor.commit.error4");
				} else {
					nutritionistService.save2(nutritionist);
					redirectView = "redirect:../security/login.do";
					result = new ModelAndView(redirectView);
				}
			} catch (Throwable oops) {
				result = createEditModelAndView(nutritionist,
						"actor.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile() {
		ModelAndView result;

		Nutritionist nutritionist = nutritionistService.findByPrincipal();

		result = createEditModelAndView(nutritionist);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Nutritionist nutritionist,
			BindingResult binding) {

		ModelAndView result;
		String redirectView;
		Nutritionist a = nutritionistService.findByPrincipal();

		if (binding.hasErrors()) {
			result = createEditModelAndView(nutritionist);
		} else if (!a.getEmailAddress().equals(nutritionist.getEmailAddress())
				&& actorService.existsEmail(nutritionist.getEmailAddress())) {
			result = createEditModelAndView2(nutritionist,
					"actor.commit.error4");
		} else {

			try {

				nutritionistService.save(nutritionist);
				redirectView = "redirect:../nutritionist/profile.do";
				result = new ModelAndView(redirectView);

			} catch (Throwable oops) {
				result = createEditModelAndView2(nutritionist,
						"actor.commit.error2");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Nutritionist nutritionist) {
		ModelAndView result;

		if (nutritionist.getId() == 0) {
			result = createEditModelAndView(nutritionist, null);
		} else {
			result = createEditModelAndView2(nutritionist, null);
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Nutritionist nutritionist,
			String message) {
		ModelAndView result;

		result = new ModelAndView("nutritionist/register");
		result.addObject("nutritionist", nutritionist);
		result.addObject("messageERROR", message);

		return result;
	}

	protected ModelAndView createEditModelAndView2(Nutritionist nutritionist,
			String message) {
		ModelAndView result;

		result = new ModelAndView("nutritionist/edit");
		result.addObject("nutritionist", nutritionist);
		result.addObject("messageERROR", message);

		return result;
	}

}