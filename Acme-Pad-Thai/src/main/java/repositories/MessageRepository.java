package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
	@Query("select m from Message m where m.folder in(select f.id from Folder f where f.name=?1 and f.actor.id=?2)")
	Collection<Message> findAllByActorAndFolder(String fname,Integer a);

	@Query("select m from Message m where m.folder in(select f.id from Folder f where f.id=?1 and f.actor.id=?2)")
	Collection<Message> findAllByActorAndFolder(Integer id,Integer a);
	
	@Query("select m from Message m join m.folder f where f.actor=?2 and (m.sender like ?1 or m.recipient like ?1)")
	Collection<Message> findByActor(String email,Actor actor);
}
