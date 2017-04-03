package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MasterClass;

@Repository
public interface MasterClassRepository extends JpaRepository<MasterClass, Integer> {

	@Query ("select m.cook,m.title,m.description from MasterClass m")
	Collection<Object[]> findNameTitleDescriptionMasterClass();
	
	@Query("select min(co.managedMasterClasses.size), max(co.managedMasterClasses.size), avg(co.managedMasterClasses.size), stddev(co.managedMasterClasses.size),co.name from Cook co")
	Collection<Object[]> calculateMinMaxStddevNumberOfMasterClassPerCook();

	@Query("select avg(m.promoted),c.name from Cook c inner join c.managedMasterClasses m group by c")
	Collection<Object[]> calculateAvgPromotedDemotedMasterClassPerCook();
	
	@Query("select count(m) from MasterClass m where m.promoted=TRUE")
	Integer calculateNumberMasterClassPromoted();
	
	@Query("select m from MasterClass m inner join m.actors a where a.id=?1")
	Collection<MasterClass> findMasterClassFromActors(Integer id);
}
