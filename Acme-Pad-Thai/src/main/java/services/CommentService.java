package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import security.Authority;
import domain.Comment;
import domain.Customer;
import domain.Nutritionist;
import domain.Recipe;
import domain.User;

@Service
@Transactional
public class CommentService {
	// Managed repository -------------------------------
	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NutritionistService nutritionistService;

	// Constructor --------------------------------------
	public CommentService() {
		super();
		
	}

	// Simple CRUD methods ------------------------------
	public Comment create(Customer cc, Recipe r) {
		checkPrincipal();
		Assert.notNull(cc);
		Assert.notNull(r);
		Comment c = new Comment();
		c.setCustomer(cc);
		c.setRecipe(r);
		return c;
	}

	public Comment create(Recipe recipe) {
		Customer customer = customerService.findByPrincipal();
		Comment comment = new Comment();
		comment.setRecipe(recipe);
		comment.setStars(0);
		comment.setCustomer(customer);
		return comment;
	}
	
	public Comment create(Recipe recipe, Customer customer) {
		Comment comment = new Comment();
		comment.setRecipe(recipe);
		comment.setStars(0);
		comment.setCustomer(customer);
		
		return comment;
	}
	
	public Comment saveRelations(Comment comment){
		Comment commentSave = commentRepository.save(comment);
		Customer customer = commentSave.getCustomer();
		List<Comment> comments = (List<Comment>) customer.getComments();
		comments.add(comment);
	
		if(customer instanceof User){
			User u = (User) customer;
			u.setComments(comments);
			userService.saveRelationships(u);
		}
		else{
			if(customer instanceof Nutritionist){
				Nutritionist nu = (Nutritionist) customer;
				nu.setComments(comments);
				nutritionistService.saveRelationships(nu);
			}
		}
		return commentSave;
	}
	
	public Comment save(Comment c) {
		Assert.notNull(c);
		if(c.getId()==0)
		{
			checkPrincipal();
		}
		else
		{
			Assert.notNull(actorService.findByPrincipal().getId());
		}
		Comment result=commentRepository.save(c);
		if (!c.getCustomer().getComments().contains(c)) {
			Customer cus = c.getCustomer();
			Collection<Comment> ac = c.getCustomer().getComments();
			ac.add(c);
			cus.setComments(ac);
			saveCustomer(cus);
		}
		if (!c.getRecipe().getComments().contains(c)) {
			Recipe rec = c.getRecipe();
			Collection<Comment> ac = c.getRecipe().getComments();
			ac.add(c);
			rec.setComments(ac);
			recipeService.saveRelations(rec);
		}
		return result;
	}

	public Collection<Comment> findAllCommentByAuthor(int u) {
		Assert.notNull(u);
		return commentRepository.findAllCommentByAuthor(u);
	}

	public Collection<Comment> findAllCommentByRecipe(int r) {
		Assert.notNull(r);
		return commentRepository.findAllCommentByRecipe(r);
	}

	public Comment findOne(int id) {
		Assert.notNull(id);
		return commentRepository.findOne(id);
	}

	public void delete(Comment c, Recipe r) {
		Assert.notNull(actorService.findByPrincipal().getId());
		Assert.notNull(r);
		Set<Comment> com = new HashSet<Comment>();
		com.addAll(commentRepository.findAllCommentByRecipe(r.getId()));
		if (com.contains(c)) {

			commentRepository.delete(c);
		}
	}
	
	public Collection<Comment> findAll() {
		return commentRepository.findAll();
	}
	// Other Comment methods ---------------------------
	public void checkPrincipal() {
		Customer c = customerService.findByPrincipal();
		for (Authority b : c.getUserAccount().getAuthorities()) {
			Assert.isTrue(b.getAuthority().equals("USER") || b.getAuthority().equals("NUTRITIONIST"));
		}

	}
	
	public Customer saveCustomer(Customer c)
	{
		Customer r=null;
		if(c instanceof User)
		{
			r=userService.saveRelations((User)c);
		}
		else if(c instanceof Nutritionist)
		{
			r=nutritionistService.saveRelations((Nutritionist)c);
		}
		return r;
	}

}
