package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

	@Query("select b from Bill b where b.sponsor.id = ?1")
	Collection<Bill> selectBillFromSponsor(int id);

	
	@Query("select avg(b.cost)*1.0,stddev(b.cost)*1.0 from Bill b group by DATE_FORMAT(b.paid,'PAID')")
	Collection<Object> calculateAvgStd();
}
