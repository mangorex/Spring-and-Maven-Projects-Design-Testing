package controllers.administrator;

import java.util.LinkedList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FeeService;
import controllers.AbstractController;
import domain.Fee;

@Controller
@RequestMapping("/fee/administrator")
public class FeeAdministratorController extends AbstractController {

	// Services ---------------------------------------

	@Autowired
	private FeeService feeService;

	// Constructors -----------------------------------

	public FeeAdministratorController() {
		super();
	}

	// view -----------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		LinkedList<Fee> fee = new LinkedList<Fee>();
		fee.addAll(feeService.findAll());

		result = new ModelAndView("fee/edit");
		result.addObject("fee", fee.get(0));

		return result;
	}

	// Save ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Fee fee, BindingResult binding) {
		ModelAndView result;
		result = new ModelAndView("fee/edit");

		if (binding.hasErrors()) {
			result.addObject("fee", fee);
			result.addObject("MessageERROR", null);
		} else {
			try {
				fee = feeService.save(fee);
				result.addObject("fee", fee);
			} catch (Throwable oops) {
				result.addObject("fee", fee);
				result.addObject("MessageERROR", "fee.commit.error");
			}
		}
		return result;
	}

	// Delete ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "default")
	public ModelAndView delete(@Valid Fee fee, BindingResult binding) {
		ModelAndView result;
		result = new ModelAndView("fee/edit");

		if (binding.hasErrors()) {
			result.addObject("fee", fee);
			result.addObject("MessageERROR", null);
		} else {
			try {
				fee.setCostPerBanner(0.25);
				fee = feeService.save(fee);
				result.addObject("fee", fee);
			} catch (Throwable oops) {
				result.addObject("fee", fee);
				result.addObject("MessageERROR", "fee.commit.error");
			}
		}
		return result;
	}
}
