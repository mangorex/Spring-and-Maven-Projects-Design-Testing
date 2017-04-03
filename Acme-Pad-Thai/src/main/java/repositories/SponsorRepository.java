package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;
import domain.User;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {



	@Query("select s from Sponsor s order by s.campaigns.size desc")
	Collection<Sponsor> findRankingCompaniesNumberOfCampaigns();

	@Query("select b.sponsor from Bill b group by b.sponsor order by sum(b.cost) desc")
	Collection<Sponsor> findRankingCompaniesMonthlyBill();

	@Query("select c1.sponsor from Campaign c1 where c1 not in (select c.id from Campaign c where (sysdate()-c.endDate)<=300000000 or sysdate()-c.endDate=null)")
	Collection<Sponsor> findSponsorNotManagedCampaignForTheLastThreeMonths();

	@Query("select b.sponsor from Bill b group by b.sponsor.companyName having sum(b.cost) < (select avg(b.cost) from Bill b)")
	Collection<Sponsor> findCompaniesHaveSpentLessAvgInCampaigns();

	@Query("select b.sponsor from Bill b group by b.sponsor.companyName having sum(b.cost) >= ALL (select sum(b.cost) * 0.9 from Bill b group by b.campaign)")
	Collection<Sponsor> findCompanyNameSpentAtLeastNinetyPerCentMaxAmountCompanyOnCampaign();

	@Query("Select b.sponsor from Bill b where paid is null and (sysdate()-b.created)>=100000000")
	Collection<Sponsor> sponsorsWithUnpaidBills();

	@Query("select c from Sponsor c where c.userAccount.id = ?1")
	Sponsor findByUserAccount(int id);
	
	@Query("select count(u) from Sponsor u where u.userAccount.username = ?1")
	Long countUsersWithUsername(String username);
	
	@Query("select u from User u where u.userAccount.id = ?1")
	User findByUserAccountId(int userAccountId);
}
