package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Fee;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Integer> {

}
