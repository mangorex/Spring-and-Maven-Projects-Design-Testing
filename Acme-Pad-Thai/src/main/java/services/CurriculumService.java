package services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Curriculum;
import domain.Endorser;
import domain.Nutritionist;
import repositories.CurriculumRepository;
import security.Authority;

@Service
@Transactional
public class CurriculumService {
	// Managed repository -------------------------------
	@Autowired
	private CurriculumRepository curriculumRepository;
	@Autowired
	private NutritionistService nutritionistService;
	@Autowired
	private EndorserService endorserService;
	@Autowired
	private ActorService actorService;

	// Constructor --------------------------------------
	public CurriculumService() {
		super();
	}

	// Simple CRUD methods ------------------------------
	public Curriculum create(Nutritionist n) {
		checkPrincipal();
		Curriculum c = new Curriculum();
		c.setNutritionist(n);
		c.setEndorsers(new HashSet<Endorser>());
		return c;
	}

	public Curriculum save(Curriculum c) {
		Curriculum cur=curriculumRepository.save(c);
		
		cur.getNutritionist().setCurriculum(cur);
		nutritionistService.save(cur.getNutritionist());
		
		for(Endorser e:cur.getEndorsers()){
			
			Set<Curriculum> se = new HashSet<Curriculum>(e.getCurricula());
			se.add(cur);
			e.setCurricula(se);
			endorserService.save(e);
		}	
		
		return curriculumRepository.save(cur);
	}

	public void delete(Curriculum c) {
		checkPrincipal();

		for (Endorser e : c.getEndorsers()) {
			Set<Curriculum> se = new HashSet<Curriculum>(e.getCurricula());
			se.remove(c);
			e.setCurricula(se);
			endorserService.save(e);
		}
		
		c.getNutritionist().setCurriculum(null);
		
		curriculumRepository.delete(c);
		nutritionistService.save(c.getNutritionist());
	}

	public void deleteWR(Curriculum c) {

		for (Endorser e : c.getEndorsers()) {
			Set<Curriculum> se = new HashSet<Curriculum>(e.getCurricula());
			se.remove(c);
			e.setCurricula(se);
			endorserService.save(e);
		}
		
		curriculumRepository.delete(c);
	}

	public Curriculum findOne(Integer id) {
		return curriculumRepository.findOne(id);
	}

	// Other business methods ---------------------------
	public Curriculum findCurriculumByNutritionist(int autor) {
		return curriculumRepository.findCurriculumByNutritionist(autor);
	}

	public void checkPrincipal() {
		Actor a = actorService.findByPrincipal();
		for (Authority b : a.getUserAccount().getAuthorities()) {
			Assert.isTrue(b.getAuthority().equals("NUTRITIONIST"));
		}

	}

}
