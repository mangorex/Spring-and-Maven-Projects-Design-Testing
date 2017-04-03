package services;

import java.util.HashSet;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Property;
import domain.Valued;
import repositories.PropertyRepository;
import repositories.ValuedRepository;
import security.Authority;

@Service
@Transactional
public class PropertyService {
	//Managed repository -------------------------------
	@Autowired
	private PropertyRepository propertyRepository;
	@Autowired
	private ValuedRepository valuedService;
	@Autowired
	private ActorService actorService;
	
	//Constructor --------------------------------------
	public PropertyService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//Simple CRUD methods ------------------------------
	
	public Property create(){
		Property c= new Property();
		c.setValueds(new HashSet<Valued>());

		return c;
	}
	
	public Property save(Property p){
		Assert.notNull(p);
		
		for(Valued v:p.getValueds()){
			if(!v.getProperty().equals(p)){
				v.setProperty(p);
			}
			valuedService.save(v);
		}
		return propertyRepository.save(p);
	}
	
	public Collection<Property> findAll(){
		return propertyRepository.findAll();
	}
	
	public Property findOne(Integer id){
		return propertyRepository.findOne(id);
	}
	
	public void delete(Property p){
		for(Valued v:p.getValueds()){
			if(v.getProperty().equals(p)){
				v.setProperty(new Property());
			}
			valuedService.save(v);
		}
		propertyRepository.delete(p);
	}
	
	//Other Property methods ---------------------------
	
	public void checkPrincipal() {
		Actor a = actorService.findByPrincipal();
		for (Authority b : a.getUserAccount().getAuthorities()) {
			Assert.isTrue(b.getAuthority().equals("NUTRITIONIST"));
		}

	}
}
