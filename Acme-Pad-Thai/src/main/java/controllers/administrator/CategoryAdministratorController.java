package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	// Services ----------------

	@Autowired
	private CategoryService categoryService;

	// Constructors ------------------

	public CategoryAdministratorController() {
		super();
	}

	// Listing ------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Category> categories;

		categories = categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/administrator/list.do");
		return result;
	}

	// Creating -------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		Category c = new Category();
		Category category = categoryService.create(c);

		return createEditModelAndView(category);
	}

	// Modifying ----------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@Valid int categoryId) {
		ModelAndView result;
		Category category = categoryService.findOne(categoryId);
		Collection<Category> categoriesList = categoryService.findAll();
		result = createEditModelAndView(category);
		result.addObject("categoriesList", categoriesList);
		return result;
	}

	// Saving ---------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Category category, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(category);
		} else {
			try {

				categoryService.save(category);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(category,
						"category.commit.error.save");

			}
		}
		return result;
	}

	// Deleting ------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Category category, BindingResult binding) {
		ModelAndView result;
		if (category.getRecipes().size() != 0) {
			result = createEditModelAndView(category,
					"category.commit.error.delete.size");
		} else {
			try {
				categoryService.delete(category);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(category,
						"category.commit.error.delete");
			}
		}
		return result;
	}

	private ModelAndView createEditModelAndView(Category category) {
		return createEditModelAndView(category, null);
	}

	private ModelAndView createEditModelAndView(Category category,
			String message) {
		ModelAndView result;

		result = new ModelAndView("category/edit");
		result.addObject("categoryc", category);
		result.addObject("messageERROR", message);
		Collection<Category> categoriesList = categoryService.findAll();
		result.addObject("categoriesList", categoriesList);

		return result;
	}

}
