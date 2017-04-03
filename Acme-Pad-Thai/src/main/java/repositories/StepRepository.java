package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Step;

@Repository
public interface StepRepository extends JpaRepository<Step, Integer> {
	@Query("select s from Step s where s.recipe.id=?1")
	Collection<Step> findAllStepByRecipe(int r);
	
	@Query("select avg(r.steps.size), stddev(r.steps.size) from Recipe r")
	Object[] calculateAvgStdRecipe();
}
