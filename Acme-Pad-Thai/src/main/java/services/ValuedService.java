package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Ingredient;
import domain.Property;
import domain.Valued;
import repositories.IngredientRepository;
import repositories.PropertyRepository;
import repositories.ValuedRepository;
import security.Authority;

@Service
@Transactional
public class ValuedService {
	// Managed repository -------------------------------
	@Autowired
	private ValuedRepository valuedRepository;
	@Autowired
	private IngredientRepository ingredientRepository;
	@Autowired
	private PropertyRepository propertyRepository;
	@Autowired
	private ActorService actorService;

	// Constructor --------------------------------------

	public ValuedService() {
		super();
	}

	// Simple CRUD methods ------------------------------

	public Valued create(Property p, Ingredient i) {
		checkPrincipal();
		Assert.notNull(p);
		Valued v = new Valued();
		v.setIngredient(i);
		v.setProperty(p);

		return v;
	}

	public Valued save(Valued v) {
		checkPrincipal();
		Assert.notNull(v);

		Ingredient i = v.getIngredient();
		i.getValueds().add(v);

		Property p = v.getProperty();
		p.getValueds().add(v);

		propertyRepository.save(p);
		ingredientRepository.save(i);
		return valuedRepository.save(v);
	}

	public Valued findOne(Integer id) {
		return valuedRepository.findOne(id);
	}

	public Collection<Valued> findAll(){
		return valuedRepository.findAll();
	}
	
	public void delete(Valued v) {
		checkPrincipal();
		Ingredient i = v.getIngredient();
		Property p=v.getProperty();
		p.getValueds().remove(v);
		i.getValueds().remove(v);
		
		valuedRepository.delete(v);
		propertyRepository.save(p);
		ingredientRepository.save(i);
		
	}
	
	public void checkPrincipal() {
		Actor a = actorService.findByPrincipal();
		for (Authority b : a.getUserAccount().getAuthorities()) {
			Assert.isTrue(b.getAuthority().equals("NUTRITIONIST"));
		}

	}
}
