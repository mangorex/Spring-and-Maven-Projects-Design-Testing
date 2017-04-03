package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.Recipe;

@Service
@Transactional
public class CustomerService {

	// Managed repository -------------------------------
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private LoginService loginService;


	// Constructor --------------------------------------
	public CustomerService() {
		super();
	}

	// Suporting services --------------------------------

	// Simple CRUD methods ------------------------------

	// Other Customer methods ---------------------------

	public void follow(Customer a, Customer b) {

		Collection<Customer> fa = a.getFollowing();
		fa.add(b);
		a.setFollowing(fa);
		customerRepository.save(a);

		Collection<Customer> fb = b.getFollowers();
		fb.add(a);
		a.setFollowing(fb);
		customerRepository.save(b);

	}

	public void unfollow(Customer a, Customer b) {

		Collection<Customer> fa = a.getFollowing();
		fa.remove(b);
		a.setFollowing(fa);
		customerRepository.save(a);

		Collection<Customer> fb = b.getFollowers();
		fb.remove(a);
		a.setFollowing(fb);
		customerRepository.save(b);

	}

	public boolean checkPrincipal(Customer a) {
		boolean res = false;
		for (Authority b : a.getUserAccount().getAuthorities()) {
			if (b.getAuthority().equals("NUTRITIONIST")) {
				return true;
			}
		}
		return res;
	}

	@SuppressWarnings("static-access")
	public Customer findByPrincipal() {
		UserAccount userAccount;
		Customer result;
		userAccount = loginService.getPrincipal();
		result = findByUserAccount(userAccount);

		return result;
	}

	public Customer findByUserAccount(UserAccount userAccount) {
		Customer result;

		result = customerRepository.findByUserAccount(userAccount.getId());
		return result;

	}

	public Customer findOne(int actorId) {
		return customerRepository.findOne(actorId);
	}

	public void saveDisLikedRecipes(Recipe recipe) {
		Assert.notNull(recipe);

		Customer customer = findByPrincipal();

		Set<Recipe> disLikedRecipes = new HashSet<Recipe>();
		disLikedRecipes.addAll(customer.getDislikedRecipes());
		disLikedRecipes.add(recipe);
		
		Set<Recipe> likedRecipes = new HashSet<Recipe>();
		likedRecipes.addAll(customer.getLikedRecipes());
		likedRecipes.remove(recipe);

		customer.setLikedRecipes(likedRecipes);
		customer.setDislikedRecipes(disLikedRecipes);
		customerRepository.save(customer);
	}
	
	public void saveLikedRecipes(Recipe recipe) {
		Assert.notNull(recipe);

		Customer customer = findByPrincipal();

		Set<Recipe> likedRecipes = new HashSet<Recipe>();
		likedRecipes.addAll(customer.getLikedRecipes());
		likedRecipes.add(recipe);
		Set<Recipe> disLikedRecipes = new HashSet<Recipe>();
		disLikedRecipes.addAll(customer.getDislikedRecipes());
		disLikedRecipes.remove(recipe);

		customer.setLikedRecipes(likedRecipes);
		customer.setDislikedRecipes(disLikedRecipes);
		customerRepository.save(customer);
	}
}
