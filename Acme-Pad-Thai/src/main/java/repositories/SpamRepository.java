package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Spam;

@Repository
public interface SpamRepository extends JpaRepository<Spam, Integer> {

}
