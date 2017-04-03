package controllers.administrator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.BillService;
import services.CampaignService;
import services.MessageService;
import services.SponsorService;

import controllers.AbstractController;

import domain.Bill;
import domain.Campaign;
import domain.Message;
import domain.Sponsor;

@Controller
@RequestMapping("/bill/administrator")
public class BillAdministratorController extends AbstractController {

	// Services ---------------------------------------
	@Autowired
	private BillService billService;
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private AdministratorService administratorService;
	
	@RequestMapping(value = "/compute", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		result = new ModelAndView("bill/compute");

		return result;
	}

	@RequestMapping(value = "/compute", method = RequestMethod.POST, params = "saveBill")
	public ModelAndView compute() throws Exception {
		ModelAndView result;
		List<Campaign> campaigns = (List<Campaign>) campaignService.findAll();

		Date currentDate = new Date();

		for (Campaign campaign : campaigns) {

			if (campaign.getStartDate().before(currentDate)
					&& campaign.getEndDate().after(currentDate)) {
				Bill bill = billService.create(campaign);
				Bill savedBill = billService.save(bill);
			}
		}

		result = new ModelAndView("bill/compute");
		result.addObject("correctCompute", true);
		return result;
	}

	@RequestMapping(value = "/compute", method = RequestMethod.POST, params = "sendBulkMessage")
	public ModelAndView bulkMessages() throws Exception {
		ModelAndView result;
		List<Sponsor> sponsors = (List<Sponsor>) sponsorService.findAll();

		Calendar cal = GregorianCalendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DATE) - 30);
		Date limitDate = cal.getTime();
		String sender = administratorService.findByPrincipal().getEmailAddress();
		
		for (Sponsor sponsor : sponsors) {
			
			List<Bill> bills = (List<Bill>) sponsor.getBills();
			for (Bill bill : bills) {
				if (bill.getPaid() == null
						&& bill.getCreated().before(limitDate)) {
					Message m = messageService.create();
					m.setRecipient(sponsor.getEmailAddress());
					m.setSender(sender);
					m.setSubject("Bulk bill unpaid");
					m.setPriority("HIGH");
					m.setBody("The Bill created in " + bill.getCreated()
							+ " is unpaid. Description: "
							+ bill.getDescription() + ". The cost is:"
							+ bill.getCost() + ". The campaign is : "
							+ bill.getCampaign());
					messageService.save(m);
				}
			}

		}

		result = new ModelAndView("bill/compute");
		result.addObject("correctSend", true);
		return result;
	}
}
