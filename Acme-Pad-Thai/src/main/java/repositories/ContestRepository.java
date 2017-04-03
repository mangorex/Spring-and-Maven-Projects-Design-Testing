package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Contest;
import domain.Recipe;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Integer> {

	@Query("select quali from Contest co join co.recipes quali where co.id= ?1")
	Collection<Recipe> findRecipesQualifiedsByContest(int contestId);

	@Query("select co from Contest co where co.recipes.size = (select max(co.recipes.size) from Contest co)")
	Collection<Contest> findContestMaxRecipesQualifieds();
}
