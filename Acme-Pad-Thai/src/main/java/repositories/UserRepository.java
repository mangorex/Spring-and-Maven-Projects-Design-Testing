package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.userAccount.id = ?1")
	User findByUserAccount(int id);

	@Query("select u from User u join u.authoredRecipes r where r.id = ?1 ")
	User findUserByRecipeId(int id);

	@Query("select u from User u where u.name like concat('%',?1,'%')")
	User searchUserByName(String keyword);

	@Query("select u from User u where u.authoredRecipes.size = (select max(u.authoredRecipes.size) from User u)")
	Collection<User> findUserMaxRecipes();

	/*
	 * @Query("select c from Customer c order by c.followers.size DESC")
	 * Collection<User> findUserOrderByFollowers();
	 */
	@Query("select u from User u order by u.followers.size DESC")
	Collection<User> findUserOrderByFollowers();


	@Query("select u from Recipe r inner join r.author u group by u.id order by avg(r.likedCustomers.size), avg(r.dislikedCustomers.size) DESC")
	Collection<User> findUserRegardingAvgLikesAndDislikes();

	
	@Query("select u from User u where u.userAccount.id = ?1")
	User findByUserAccountId(int userAccountId);

	@Query("select u from User u where u.name like concat('%',?1,'%') or u.surname like concat('%',?1,'%')")
	Collection<User> findUserByKeywordName(String keyWord);
}
