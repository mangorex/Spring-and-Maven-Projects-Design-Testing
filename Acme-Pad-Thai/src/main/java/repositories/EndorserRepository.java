package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import domain.Endorser;

@Repository
public interface EndorserRepository extends JpaRepository<Endorser, Integer> {
}
