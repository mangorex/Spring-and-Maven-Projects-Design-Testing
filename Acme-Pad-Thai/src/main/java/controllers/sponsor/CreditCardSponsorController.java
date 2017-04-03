/* CurriculumCustomerController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.sponsor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.SponsorService;
import controllers.AbstractController;
import domain.CreditCard;

// TODO: implement this controller from scratch. 

/**
 * @author Student
 *
 */
@Controller
@RequestMapping("/creditCard/sponsor")
public class CreditCardSponsorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CreditCardService creditCardService;

	@Autowired
	private SponsorService sponsorService;

	// Constructors -----------------------------------------------------------

	public CreditCardSponsorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<CreditCard> cards;
		
		cards=creditCardService.findByPrincipal();
		

		result = new ModelAndView("creditCard/list");

		result.addObject("creditCards", cards);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam String creditCard) throws Exception {
		ModelAndView result;
		Collection<CreditCard> cards;
		result = new ModelAndView("creditCard/list");
		cards = creditCardService.findByPrincipal();
		/** TODO CAMBIAR EL MENSAJE */
		String error = "creditCard.commit.error";
		try {
			CreditCard c = creditCardService.findOne(Integer.parseInt(creditCard));
			if (c.getSponsor() == sponsorService.findByPrincipal()) {
				creditCardService.delete(c);
				cards = creditCardService.findByPrincipal();
			} else {
				/** TODO CAMBIAR EL MENSAJE */
				result.addObject("messageERROR", error);
			}
			result.addObject("creditCards", cards);

		} catch (Throwable oops) {
			result.addObject("creditCards", cards);
			result.addObject("messageERROR", oops.getStackTrace());
		}
		// }
		return result;
	}

	// Edit -------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView read(@RequestParam int id) {
		ModelAndView result;
		CreditCard card;
		if (id != 0
				&& creditCardService.findOne(id) != null
				&& creditCardService.findOne(id).getSponsor().equals(sponsorService.findByPrincipal())) {
			card = creditCardService.findOne(id);

			result = new ModelAndView("creditCard/edit");

			result.addObject("creditCard", card);
		} else {
			result=this.list();
		}
		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/add", method = RequestMethod.POST, params = "save")
	public ModelAndView send(@Valid CreditCard creditCard, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = new ModelAndView("creditCard/edit");

			result.addObject("creditCard", creditCard);
			
			System.out.println(binding.toString());
		} else {
			try{
			creditCardService.save(creditCard);
			result=this.list();
			}catch(Throwable oops)
			{
				result = new ModelAndView("creditCard/edit");

				result.addObject("creditCard", creditCard);
				result.addObject("messageERROR", oops.getStackTrace());
			}

		}

		return result;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CreditCard card=creditCardService.create(sponsorService.findByPrincipal());
		

			result = new ModelAndView("creditCard/edit");

			result.addObject("creditCard", card);

		return result;
	}

	
}