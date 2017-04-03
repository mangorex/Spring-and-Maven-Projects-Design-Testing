package repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Quantified;

@Repository
public interface QuantifiedRepository extends JpaRepository<Quantified, Integer> {
	
}
