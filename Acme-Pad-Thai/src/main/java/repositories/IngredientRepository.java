package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
	@Query("select count(r) from Recipe r inner join r.quantifieds q inner join q.ingredient i where i.id=?1")
	Integer calculateNumRecipeByIngredient(int idIngredient);
	@Query("select i from Ingredient i inner join i.quantifieds q inner join q.recipe r where r.id=?1")//116
	Collection<Ingredient> findIngredientByRecipe(int idRecipe);
}
