package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

	@Query("select r from Category c join c.recipes r where c.recipes.size>0 order by c.id")
	Collection<Recipe> findRecipeGroupedByCategory();

	@Query("select r from Recipe r where r.ticker like concat('%',?1,'%') or r.title like concat('%',?1,'%') or r.summary like concat('%',?1,'%')")
	Collection<Recipe> findRecipeByKeywordTickerTitleOrSummary(String keyWord);

	@Query("select r from Recipe r where r.author.id = ?1")
	Collection<Recipe> findAllRecipeByAuthor(int idAuthor);

	@Query("select r from Recipe r join r.author a join a.followers f where f.id=?1 order by r.creationDate desc")
	Collection<Recipe> findLatestRecipeFollowers(int userId);

	@Query("select min(u.authoredRecipes.size)*1.0, avg(u.authoredRecipes.size)*1.0, max(u.authoredRecipes.size)*1.0 from User u")
	Object[] calculateMinAvgMaxRecipePerUser();

	@Query("select avg(r.steps.size), stddev(r.steps.size) from Recipe r")
	Object[] calculateAvgStddevStepsOfRecipe();

	@Query("select avg(r.quantifieds.size), stddev(r.quantifieds.size) from Recipe r")
	Object[] calculateAvgStddevIngredientsOfRecipe();
	
	@Query("select min(co.recipes.size), avg(co.recipes.size), max(co.recipes.size) from Contest co")
	Object[] calculateMinAvgMaxQualifiedsForContest();
	
	@Query("select quali from Contest co join co.recipes quali where co.id= ?1 Order by quali.likedCustomers.size DESC,quali.dislikedCustomers.size ASC")
	Collection<Recipe> findRecipesQualifiedsByContestOrderedByLikesAndDislikes(int contestId);
}
