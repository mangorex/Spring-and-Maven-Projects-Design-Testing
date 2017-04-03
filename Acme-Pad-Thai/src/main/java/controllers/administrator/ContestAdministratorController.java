package controllers.administrator;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;
import services.RecipeService;

import controllers.AbstractController;
import domain.Contest;

@Controller
@RequestMapping("/contest/administrator")
public class ContestAdministratorController extends AbstractController {

	// Services ---------------------------------------

	@Autowired
	private ContestService contestService;
	@Autowired
	private RecipeService recipeService;

	// Constructors -----------------------------------

	public ContestAdministratorController() {
		super();
	}

	// Listing ----------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Contest> contests;

		contests = contestService.findAll();

		result = new ModelAndView("contest/list");
		result.addObject("contests", contests);
		result.addObject("requestUri", "contest/list.do");

		return result;
	}

	// Creating ---------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		Contest contest = contestService.create();

		result = new ModelAndView("contest/edit");
		result.addObject("contest", contest);

		return result;

	}

	// Modifying --------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@Valid int contestId) {
		ModelAndView result;

		Contest contest = contestService.findOne(contestId);
		result = createEditModelAndView(contest);

		return result;

	}

	// Saving -----------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Contest contest, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(contest);
		} else if (contest.getRecipes().size() > 0) {
			result = createEditModelAndView(contest,
					"contest.commit.error.save");

		} else {
			try {
				contestService.save(contest);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(contest,
						"contest.commit.error.save");

			}

		}
		return result;
	}

	// Deleting ---------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Contest contest, BindingResult binding) {
		ModelAndView result;

		try {
			contestService.delete(contest);
			result = new ModelAndView("redirect:list.do");

		} catch (Throwable oops) {
			result = createEditModelAndView(contest,
					"contest.commit.error.delete");
		}

		return result;
	}

	@RequestMapping(value = "/setWinners", method = RequestMethod.GET)
	public ModelAndView listWinner() {
		ModelAndView result;

		result = new ModelAndView("contest/setWinners");

		return result;
	}
	
	@RequestMapping(value = "/setWinners", method = RequestMethod.POST, params = "saveWinners")
	public ModelAndView setWinners() throws Exception {
		ModelAndView result;

		List<Contest> contests = (List<Contest>) contestService.findAll();
		recipeService.setWinnersOfEveryContests();
		result = new ModelAndView("contest/setWinners");
		result.addObject("correctWinners", true);
		return result;
	}

	private ModelAndView createEditModelAndView(Contest contest) {

		return createEditModelAndView(contest, null);

	}

	private ModelAndView createEditModelAndView(Contest contest, String message) {

		ModelAndView result;

		result = new ModelAndView("contest/edit");
		result.addObject("contest", contest);
		result.addObject("messageERROR", message);

		return result;

	}
}
