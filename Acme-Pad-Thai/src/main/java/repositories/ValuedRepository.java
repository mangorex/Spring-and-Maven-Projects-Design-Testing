package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Valued;

@Repository
public interface ValuedRepository extends JpaRepository<Valued, Integer> {

}
