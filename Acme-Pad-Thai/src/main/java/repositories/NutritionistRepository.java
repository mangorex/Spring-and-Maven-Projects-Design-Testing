package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Nutritionist;
import domain.User;

@Repository
public interface NutritionistRepository extends
		JpaRepository<Nutritionist, Integer> {
	@Query("select n from Nutritionist n where n.userAccount.id = ?1")
	Nutritionist findByNutritionistAccount(int id);

	@Query("select n from Nutritionist n  inner join n.ingredients i where i.id = ?1")
	Nutritionist findNutritionistByIngredient(int id);

	@Query("select count(u) from User u where u.userAccount.username = ?1")
	Long countUsersWithUsername(String username);

	@Query("select u from User u where u.userAccount.id = ?1")
	User findByUserAccountId(int userAccountId);

}
