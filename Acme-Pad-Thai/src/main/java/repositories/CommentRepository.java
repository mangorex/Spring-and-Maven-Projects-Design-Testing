package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	@Query("select c from Comment c where c.customer.id=?1")
	Collection<Comment> findAllCommentByAuthor(int u);
	
	@Query("select c from Comment c where c.recipe.id=?1")
	Collection<Comment> findAllCommentByRecipe(int r);
}
