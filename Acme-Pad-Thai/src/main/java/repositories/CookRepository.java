package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Cook;

@Repository
public interface CookRepository extends JpaRepository<Cook, Integer> {

	@Query("select c from Cook c where c.userAccount.id = ?1")
	Cook findByUserAccount(int id);
	
	@Query("select c.name,c.surname,count(m) from Cook c inner join c.managedMasterClasses m where m.promoted=TRUE order by count(m) ASC")
	Collection<Object[]> findCooksSortedAccordingToNumberOfPromotedMasterClasses();

}
