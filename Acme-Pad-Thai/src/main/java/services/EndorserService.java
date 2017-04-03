package services;

import java.util.HashSet;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import domain.Actor;
import domain.Curriculum;
import domain.Endorser;
import domain.Nutritionist;
import repositories.EndorserRepository;
import security.Authority;

@Service
@Transactional
public class EndorserService {
	// Managed repository -------------------------------
	@Autowired
	private EndorserRepository endorserRepository;
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private ActorService actorService;

	// Constructor --------------------------------------
	public EndorserService() {
		super();
	}

	// Simple CRUD methods ------------------------------
	public Endorser create(Curriculum c) {
		checkPrincipal();
		Assert.notNull(c);
		Endorser e = new Endorser();
		e.setCurricula(new HashSet<Curriculum>());
		e.getCurricula().add(c);

		return e;
	}

	public Endorser save(Endorser e) {
		checkPrincipal();
		Assert.notNull(e);
		
		Endorser en=endorserRepository.save(e);
		
		for(Curriculum c:en.getCurricula()){
			if(!c.getEndorsers().contains(en)){
				c.getEndorsers().add(en);
				curriculumService.save(c);
			}
		}
		return en;
	}

	public Endorser findOne(Integer id) {
		return endorserRepository.findOne(id);
	}

	public void delete(Endorser end) {
		checkPrincipal();
		for (Curriculum c : end.getCurricula()) {
			c.getEndorsers().remove(end);
			curriculumService.save(c);
		}
		
		end.setCurricula(new HashSet<Curriculum>());
		endorserRepository.delete(end.getId());
	}
	
	public Collection<Endorser> findAll(){
		return endorserRepository.findAll();
	}

	// Other Curriculum methods -------------------------
	
	public Collection<Endorser> findAllByNutriotionist(Nutritionist n) {
		Assert.notNull(n);
		return n.getCurriculum().getEndorsers();
	}
	
	public void checkPrincipal() {
		Actor a = actorService.findByPrincipal();
		for (Authority b : a.getUserAccount().getAuthorities()) {
			Assert.isTrue(b.getAuthority().equals("NUTRITIONIST"));
		}

	}

}
