package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import domain.Actor;


@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select c from Actor c where c.userAccount.id = ?1")
	Actor findByUserAccount(int id);
	
	@Query("select a from Actor a where a.emailAddress like ?1")
	Actor findByEmail(String email);
	
	@Query("select count(u) from Actor u where u.userAccount.username = ?1")
	Long countUsersWithUsername(String username);
	
	@Query("select count(u) from Actor u where u.emailAddress = ?1")
	Long countUsersWithEmail(String email);
}
