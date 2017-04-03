package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CampaignRepository;
import domain.Bill;
import domain.Campaign;
import domain.Sponsor;

@Transactional
@Service
public class CampaignService {
	// Managed repository -------------------------------
	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private SponsorService sponsorService;

	// Constructor --------------------------------------
	public CampaignService() {
		super();

	}

	// Simple CRUD methods ------------------------------

	public Campaign create(Sponsor s) {
		Campaign b = new Campaign();
		b.setName("");
		b.setSponsor(s);
		b.setNumBanners(0);
		b.setBanners("");
		b.setMaxBannersDisplayed(0);
		b.setStar(false);
		b.setBills(new ArrayList<Bill>());
		return b;
	}

	public Campaign save(Campaign c) {
//		checkPrincipal(c);

		Assert.isTrue(checkBanners(c.getBanners()));

		Date dateMoment = new Date();
		Assert.notNull(c);
//		Assert.isTrue(c.getStartDate().after(dateMoment));
		Assert.isTrue(c.getStartDate().before(c.getEndDate()));

		Campaign saved = campaignRepository.save(c);

		Sponsor s = c.getSponsor();

		Collection<Campaign> lc = s.getCampaigns();

		lc.add(saved);

		s.setCampaigns(lc);

		sponsorService.save(s);

		return saved;
	}
	
	public Campaign saveRelations(Campaign campaign) {
		return campaignRepository.save(campaign);
	}

	public void delete(Campaign c) {
		checkPrincipal(c);
		Date dateMoment = new Date();
		Assert.isTrue(c.getStartDate().after(dateMoment));

		Sponsor s = c.getSponsor();
		Collection<Campaign> lc = s.getCampaigns();

		lc.remove(c);

		s.setCampaigns(lc);

		sponsorService.save(s);

		campaignRepository.delete(c);
	}

	public Collection<Campaign> findAll() {
		return campaignRepository.findAll();
	}

	// Other business methods ---------------------------

	public void checkPrincipal(Campaign c) {
		Sponsor sponsor = sponsorService.findByPrincipal();
		Assert.isTrue(sponsor.equals(c.getSponsor()));
	}

	public Double[] calculateMinAvgMaxCampaignsPerSponsor() {
		Double[] result = campaignRepository
				.calculateMinAvgMaxCampaignsPerSponsor();
		return result;
	}

	public Double[] calculateMinAvgMaxNumberCampaignsActivePerSponsor() {
		Double[] result = campaignRepository
				.calculateMinAvgMaxNumberCampaignsActivePerSponsor();
		return result;
	}

	public Collection<Campaign> CampaignBySponsor(int id) {
		return campaignRepository.campaignBySponsor(id);
	}

	private static boolean isUrl(String s) {
		String regex = "^(https?://)?(([\\w!~*'().&=+$%-]+: )?[\\w!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([\\w!~*'()-]+\\.)*([\\w^-][\\w-]{0,61})?[\\w]\\.[a-z]{2,6})(:[0-9]{1,4})?((/*)|(/+[\\w!~*'().;?:@&=+$,%#-]+)+/*)$";

		try {
			Pattern patt = Pattern.compile(regex);
			Matcher matcher = patt.matcher(s);
			return matcher.matches();

		} catch (RuntimeException e) {
			return false;
		}

	}

	public boolean checkBanners(String s) {

		boolean res = true;

		String[] keyWords = s.split(",");
		for (String a : keyWords) {
			res = isUrl(a);
			if (res == false) {
				break;
			}
		}

		return res;
	}

	public Campaign findOne(Integer campaignId) {
		return campaignRepository.findOne(campaignId);
	}

}
