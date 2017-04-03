/* 
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerFollowController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public CustomerFollowController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/followersList", method = RequestMethod.GET)
	public ModelAndView followers() {
		ModelAndView result;
		Collection<Customer> customers;
		Customer a;

		a = (Customer) actorService.findByPrincipal();

		customers = a.getFollowers();

		result = new ModelAndView("customer/followersList");
		result.addObject("customers", customers);

		return result;
	}

	@RequestMapping(value = "/followingList", method = RequestMethod.GET)
	public ModelAndView following() {
		ModelAndView result;
		Collection<Customer> customers;
		Customer a;

		a = (Customer) actorService.findByPrincipal();

		customers = a.getFollowing();

		result = new ModelAndView("customer/followingList");
		result.addObject("customers", customers);

		return result;
	}

}