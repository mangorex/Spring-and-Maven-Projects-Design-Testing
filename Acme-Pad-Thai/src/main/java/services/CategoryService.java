package services;

import java.util.HashSet;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import domain.Actor;
import domain.Administrator;
import domain.Category;
import domain.Recipe;


@Service
@Transactional
public class CategoryService {
	// Managed repository -------------------------------
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private ActorService actorService;

	// Constructor --------------------------------------
	public CategoryService() {
		super();
		
	}

	// Simple CRUD methods ------------------------------
	
	public Category create(Category cc) {
		checkPrincipal();
		Category c = new Category();
		c.setRecipes(new HashSet<Recipe>());
		c.setCategories(new HashSet<Category>());
		c.setCategoryFather(cc);
		
		return c;
	}

	public Category save(Category c) {
		Assert.notNull(c);
		checkPrincipal();
		Administrator admin = administratorService.findByPrincipal();
		
		if(admin.getCategories().contains(c)){
			Collection<Category> cc = admin.getCategories();
			cc.remove(c);
			admin.setCategories(cc);
		}

		
		Category saved = categoryRepository.save(c);
		Collection<Category> cc = admin.getCategories();
		cc.add(saved);
		admin.setCategories(cc);
		administratorService.save(admin);

		return saved;
	}

	public Category saveRelations(Category c) {
		Assert.notNull(c);
		Category saved = categoryRepository.save(c);

		return saved;
	}
	
	public void delete(Category c) {
		Assert.notNull(c);
		Assert.isTrue(c.getRecipes().size() == 0);
		checkPrincipal();
		
		Administrator admin = administratorService.findByPrincipal();
		Collection<Category> cc = admin.getCategories();
		cc.remove(c);
		admin.setCategories(cc);
		
		if(!c.getCategories().isEmpty()){
			for(Category f: c.getCategories()){
				f.setCategoryFather(null);
			}
		}
		
		categoryRepository.delete(c);
	}


	public Collection<Category> findAll() {
		return categoryRepository.findAll();
	}

	public Category findOne(Integer id) {
		return categoryRepository.findOne(id);
	}
	
	// Other bisiness methods ---------------------------
	
	public void checkPrincipal() {
		Actor a = actorService.findByPrincipal();
		for (Authority b : a.getUserAccount().getAuthorities()) {
			Assert.isTrue(b.getAuthority().equals("ADMINISTRATOR"));
		}

	}
	
	public Category saveRecipeCategory(Category c) {
		Assert.notNull(c);
		checkPrincipalUser();
		
		Category saved = categoryRepository.save(c);

		return saved;
	}

	public void checkPrincipalUser() {
			Actor a = actorService.findByPrincipal();
			for (Authority b : a.getUserAccount().getAuthorities()) {
				Assert.isTrue(b.getAuthority().equals("USER"));
			}
		}
}
