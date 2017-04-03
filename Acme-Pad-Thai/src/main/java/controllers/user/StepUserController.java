package controllers.user;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.StepService;
import services.UserService;
import controllers.AbstractController;
import domain.Recipe;
import domain.Step;
import domain.User;


@Controller
@RequestMapping("/step/user")
public class StepUserController extends AbstractController{
	// Services ---------------------------------------------------------------
		@Autowired
		private StepService stepService;
		@Autowired
		private UserService userService;
		
		// Constructors -----------------------------------------------------------
		public StepUserController() {
			super();
		}
		
		// Creation -----------------------------------------------------------
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Step step =new Step();
			User u= userService.findByPrincipal();
			Collection<Recipe> recipes=new HashSet<Recipe>();
			
			recipes.addAll(u.getAuthoredRecipes());
			
			result = new ModelAndView("step/edit");
			result.addObject("step", step);
			result.addObject("recipes", recipes);
			
			return result;
		}

		// Edition -------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int stepId) {
			ModelAndView result;
			Step step;

			step = stepService.findOne(stepId);
			Assert.notNull(step);
			result = createEditModelAndView(step);

			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Step step, BindingResult binding) {
			ModelAndView result;
			
			if (binding.hasErrors()){
				result = createEditModelAndView(step);
				System.out.println("ERROR save Step");
			}
			else {
				try {
					stepService.save(step);
					result = new ModelAndView("redirect:/step/user/edit.do");
				} catch (Throwable oops) {
					result = createEditModelAndView(step, "step.commit.error");
				}
			}

			return result;

		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Step step, BindingResult bindingResult) {
			ModelAndView result;

			try {
				stepService.delete(step);
				result = new ModelAndView("redirect:edit.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(step, "recipe.commit.error");
			}

			return result;
		}

		// Ancillary methods
		// ------------------------------------------------------------------
		private ModelAndView createEditModelAndView(Step step) {
			return createEditModelAndView(step, null);
		}

		private ModelAndView createEditModelAndView(Step step, String message) {
			ModelAndView result;

			result = new ModelAndView("step/edit");
			result.addObject("step", step);
			result.addObject("messageERROR", message);

			return result;
		}
}
