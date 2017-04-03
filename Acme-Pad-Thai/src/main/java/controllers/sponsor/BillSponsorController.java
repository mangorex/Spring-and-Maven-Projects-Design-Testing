package controllers.sponsor;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BillService;

import controllers.AbstractController;
import domain.Bill;

@Controller
@RequestMapping("/bill/sponsor/")
public class BillSponsorController extends AbstractController {
	// Services ---------------------------------------------------------------
	@Autowired
	private BillService billService;

	// Listing ----------------------------------------------------------------
	@RequestMapping(value = "/browse", method = RequestMethod.GET)
	public ModelAndView browse() {
		ModelAndView result;
		Collection<Bill> bills;

		bills = billService.findByPrincipal();

		result = new ModelAndView("bill/sponsor/browse");
		result.addObject("bills", bills);
		result.addObject("requestUri", "bill/sponsor/browse.do");
		return result;
	}

	// Edition -------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int billId) {
		ModelAndView result;
		Bill bill;

		bill = billService.findOne(billId);
		Assert.notNull(bill);
		result = createEditModelAndView(bill);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Bill bill, BindingResult binding) {
		ModelAndView result;

		Date currentDate = new Date();
		bill.setPaid(currentDate);

		if (binding.hasErrors()) {
			result = createEditModelAndView(bill);
			System.out.println("ERROR save bill");
		} else {
			try {
				billService.save(bill);
				result = new ModelAndView("redirect:browse.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(bill, "bill.commit.error");
			}
		}

		return result;

	}

	// Ancillary methods
	// ------------------------------------------------------------------
	private ModelAndView createEditModelAndView(Bill bill) {
		return createEditModelAndView(bill, null);
	}

	private ModelAndView createEditModelAndView(Bill bill, String message) {
		ModelAndView result;

		result = new ModelAndView("bill/edit");
		result.addObject("bill", bill);
		result.addObject("messageERROR", message);

		return result;
	}
}
