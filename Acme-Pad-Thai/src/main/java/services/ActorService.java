package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Transactional
@Service
public class ActorService {
	// Managed repository -------------------------------
	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private LoginService loginService;

	// Constructor --------------------------------------
	public ActorService() {
		super();

	}

	public Actor save(Actor a) {
		Assert.notNull(a);

		return actorRepository.save(a);
	}

	// Simple CRUD methods ------------------------------

	public Actor findOne(Integer id) {
		return actorRepository.findOne(id);
	}

	public Collection<Actor> findAll() {
		return actorRepository.findAll();
	}

	// Other business methods ---------------------------

	@SuppressWarnings("static-access")
	public Actor findByPrincipal() {
		UserAccount userAccount;
		Actor result;

		userAccount = loginService.getPrincipal();
		result = findByUserAccount(userAccount);
		return result;
	}

	public Actor findByUserAccount(UserAccount userAccount) {
		Actor result;

		result = actorRepository.findByUserAccount(userAccount.getId());
		return result;

	}
	
	public Actor findByEmail(String email){
		Assert.notNull(email);
		return actorRepository.findByEmail(email);
	}
	

	public boolean existsUsername(String username) {
		boolean result = false;
		long howManyUsers;

		howManyUsers = actorRepository.countUsersWithUsername(username);

		if (howManyUsers != 0) {
			result = true;
		}

		return result;
	}
	
	public boolean existsEmail(String email) {
		boolean result = false;
		long howManyUsers;

		howManyUsers = actorRepository.countUsersWithEmail(email);

		if (howManyUsers != 0) {
			result = true;
		}

		return result;
	}
	

}