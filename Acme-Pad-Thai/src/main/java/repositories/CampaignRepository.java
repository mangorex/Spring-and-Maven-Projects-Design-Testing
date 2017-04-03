package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

	@Query("select c from Campaign c where c.sponsor.id = ?1")
	Collection<Campaign> campaignBySponsor(int id);

	@Query("select min(s.campaigns.size)*1.0, avg(s.campaigns.size)*1.0, max(s.campaigns.size)*1.0 from Sponsor s")
	Double[] calculateMinAvgMaxCampaignsPerSponsor();

	@Query("select min(s.campaigns.size)*1.0, avg(s.campaigns.size)*1.0, max(s.campaigns.size)*1.0 from Sponsor s where s.id in (select c.sponsor from Campaign c where current_date() between startDate and endDate)")
	Double[] calculateMinAvgMaxNumberCampaignsActivePerSponsor();

}
