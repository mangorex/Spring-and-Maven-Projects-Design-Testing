package services;

import java.util.HashSet;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ContestRepository;
import security.Authority;
import domain.Administrator;
import domain.Contest;
import domain.Recipe;

@Service
@Transactional
public class ContestService {
	// Managed repository -------------------------------
	@Autowired
	private ContestRepository contestRepository;

	@Autowired
	private AdministratorService administratorService;

	// Constructor --------------------------------------
	public ContestService() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Simple CRUD methods ------------------------------

	public Contest create() {
		checkPrincipal();
		Contest c = new Contest();		

		c.setRecipes(new HashSet<Recipe>());
		c.setWinners(new HashSet<Recipe>());

		return c;
	}

	public Contest save(Contest c) {
		Assert.notNull(c);
		checkPrincipal();
	
		Assert.isTrue(c.getOpening().before(c.getClosing()));

		Administrator admin = administratorService.findByPrincipal();
		if(admin.getContests().contains(c)){
			Collection<Contest> cc = admin.getContests();
			cc.remove(c);
			admin.setContests(cc);
		}
		
		Contest saved = contestRepository.save(c);
		Collection<Contest> cc = admin.getContests();
		cc.add(saved);
		admin.setContests(cc);
		administratorService.save(admin);

		return saved;
	}

	public void delete(Contest c) {
		Assert.notNull(c);
		Assert.isTrue(c.getRecipes().size() == 0);
		checkPrincipal();
		Administrator admin = administratorService.findByPrincipal();
		Collection<Contest> cc = admin.getContests();
		cc.remove(c);
		admin.setContests(cc);
		contestRepository.delete(c);
	}

	

	public Contest findOne(int id) {
		return contestRepository.findOne(id);
	}

	public Collection<Contest> findAll() {
		return contestRepository.findAll();
	}

	// Other Contest methods ---------------------------
	public Contest saveQualifiedRecipe(Contest c) {
		Assert.notNull(c);
		//checkPrincipalUser();
	
		Assert.isTrue(c.getOpening().before(c.getClosing()));
		
		Contest saved = contestRepository.save(c);

		return saved;
	}
	
	private void checkPrincipal() {
		Administrator admin = administratorService.findByPrincipal();
		for (Authority a : admin.getUserAccount().getAuthorities())
			Assert.isTrue(a.getAuthority().equals("ADMINISTRATOR"));
	}
	

	public Collection<Contest> findContestMaxRecipesQualifieds() {
		Collection<Contest> result = contestRepository
				.findContestMaxRecipesQualifieds();
		return result;
	}

	public Collection<Recipe> findRecipesQualifiedsByContest(int contestId) {
		return contestRepository.findRecipesQualifiedsByContest(contestId);
	}
}
