package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BillRepository;
import domain.Bill;
import domain.Campaign;
import domain.Fee;
import domain.Sponsor;

@Service
@Transactional
public class BillService {

	// Managed repository -------------------------------
	@Autowired
	private BillRepository billRepository;
	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private FeeService feeService;
	
	// Constructor --------------------------------------
	public BillService() {
		super();

	}

	// Simple CRUD methods ------------------------------
	public Bill create(Sponsor s, Campaign c, Fee f) {
		Bill b = new Bill();

		b.setCampaign(c);
		b.setSponsor(s);
		b.setCost(c.getNumBanners() * f.getCostPerBanner());

		Date dateMoment = new Date();
		dateMoment.setTime(dateMoment.getTime() - 60000);
		b.setCreated(dateMoment);
		return b;
	}

	public Bill create(Campaign c) {
		Bill b = new Bill();
		List<Fee> fees = (List<Fee>) feeService.findAll();
		b.setCampaign(c);
		b.setSponsor(c.getSponsor());
		b.setCost(c.getNumBanners() * fees.get(0).getCostPerBanner());
		b.setDescription("Banners of the campaign: " + c.getBanners());
		Date dateMoment = new Date();
		dateMoment.setTime(dateMoment.getTime() - 60000);
		b.setCreated(dateMoment);
		return b;
	}
	
	public Bill save(Bill b) {
		Assert.notNull(b);
	
		Collection<Bill> bs = b.getSponsor().getBills();
		if(!bs.contains(b))
		{
			bs.add(b);
			Sponsor sponsor = b.getSponsor();
			sponsor.setBills(bs);
			sponsorService.saveRelations(sponsor);
		}
		
		Collection<Bill> bc = b.getCampaign().getBills();
		if(!bc.contains(b)){
			bc.add(b);
			Campaign campaign = b.getCampaign();
			campaign.setNumBanners(0);
			campaignService.saveRelations(campaign);
		}
		
		return billRepository.save(b);
	}
	// Other business methods ---------------------------

	public Collection<Bill> selectBillFromSponsor(int id) {
		return billRepository.selectBillFromSponsor(id);

	}

	public Collection<Bill> findAll() {

		return billRepository.findAll();
	}
	
	public Bill findOne(int billId) {
		return billRepository.findOne(billId);
	}
	
	public Collection<Bill> findByPrincipal() {
		Collection<Bill> result;
		Sponsor sponsor;

		sponsor = sponsorService.findByPrincipal();
		result = billRepository.selectBillFromSponsor(sponsor.getId());

		return result;
	}
	
	public Collection<Object> calculateAvgStd(){
		return billRepository.calculateAvgStd();
	}
}
