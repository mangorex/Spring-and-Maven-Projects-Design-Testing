package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Text;

@Repository
public interface TextRepository extends JpaRepository<Text, Integer> {

}
