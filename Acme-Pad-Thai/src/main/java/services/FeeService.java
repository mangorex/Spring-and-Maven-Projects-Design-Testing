package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FeeRepository;
import security.Authority;
import domain.Actor;
import domain.Fee;

@Service
@Transactional
public class FeeService {
	// Managed repository -------------------------------
	@Autowired
	private FeeRepository feeRepository;
	@Autowired
	private ActorService actorService;

	// Constructor --------------------------------------
	public FeeService() {
		super();
	}

	// Simple CRUD methods ------------------------------

	public Fee create() {
		Fee f = new Fee();
		f.setCostPerBanner(0.25);
		return f;
	}

	public Fee save(Fee f) {
		Assert.notNull(f);
		checkPrincipal();
		return feeRepository.save(f);
	}

	public Collection<Fee> findAll() {

		return feeRepository.findAll();
	}

	// Other business methods ---------------------------

	public void checkPrincipal() {
		Actor a = actorService.findByPrincipal();
		for (Authority b : a.getUserAccount().getAuthorities()) {
			Assert.isTrue(b.getAuthority().equals("ADMINISTRATOR"));
		}

	}

}
