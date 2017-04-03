package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Presentation;

@Repository
public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

}
