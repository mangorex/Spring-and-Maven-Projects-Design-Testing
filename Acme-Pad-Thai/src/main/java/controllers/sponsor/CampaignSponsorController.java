/* 
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
import org.springframework.web.servlet.ModelAndView;

import services.CampaignService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Campaign;
import domain.Sponsor;

@Controller
@RequestMapping("/campaign/sponsor")
public class CampaignSponsorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CampaignService campaignService;

	@Autowired
	private SponsorService sponsorService;

	// Constructors -----------------------------------------------------------

	public CampaignSponsorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView followers() {
		ModelAndView result;
		Collection<Campaign> campaigns;
		Sponsor a = sponsorService.findByPrincipal();
		campaigns = a.getCampaigns();

		result = new ModelAndView("campaign/sponsor/list");
		result.addObject("campaigns", campaigns);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@Valid Integer campaignId) {
		ModelAndView result;
		if (campaignId == null) {
			Sponsor a = sponsorService.findByPrincipal();
			Campaign campaign = campaignService.create(a);
			result = createEditModelAndView(campaign);
		} else {
			Campaign campaign = campaignService.findOne(campaignId);
			result = createEditModelAndView(campaign);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Campaign campaign, BindingResult binding) {

		ModelAndView result;
		String redirectView;

		if (binding.hasErrors()) {
			result = createEditModelAndView(campaign);
		} else {

			try {

				campaignService.save(campaign);
				redirectView = "redirect:../sponsor/list.do";
				result = new ModelAndView(redirectView);

			} catch (Throwable oops) {
				result = createEditModelAndView(campaign, "campaign.error2");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method=RequestMethod.POST,params="delete")
	public ModelAndView delete(Campaign campaign, BindingResult binding) {
		ModelAndView result;
		
			try{
			campaignService.delete(campaign);
			result = new ModelAndView("redirect:../sponsor/list.do");
			}catch(Throwable oops)
			{
				result=createEditModelAndView(campaign,"curriculum.commit.error");
			}
		
		return result;
	}

	protected ModelAndView createEditModelAndView(Campaign campaign) {
		ModelAndView result;

		result = createEditModelAndView(campaign, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Campaign campaign,
			String message) {
		ModelAndView result;

		result = new ModelAndView("campaign/sponsor/edit");
		result.addObject("campaign", campaign);
		result.addObject("messageERROR", message);

		return result;
	}

}