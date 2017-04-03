package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.Actor;
import domain.SocialIdentity;

@Transactional
@Service
public class SocialIdentityService {
	// Managed repository -------------------------------
	@Autowired
	private SocialIdentityRepository socialIdentityRepository;

	@Autowired
	private ActorService actorService;

	// Constructor --------------------------------------
	public SocialIdentityService() {
		super();

	}

	// Simple CRUD methods ------------------------------

	public SocialIdentity create(Actor a) {
		SocialIdentity b = new SocialIdentity();
		b.setActor(a);
		return b;
	}

	public SocialIdentity save(SocialIdentity c) {
		checkPrincipal(c);
		Assert.notNull(c);
		
		SocialIdentity saved = socialIdentityRepository.save(c);
		
		Collection<SocialIdentity> f = saved.getActor().getSocialIdentities();
		f.add(saved);
		saved.getActor().setSocialIdentities(f);
		actorService.save(saved.getActor());

		return saved;
	}

	public void delete(SocialIdentity c) {
		checkPrincipal(c);
		Collection<SocialIdentity> f = c.getActor().getSocialIdentities();
		f.remove(c);
		c.getActor().setSocialIdentities(f);
		actorService.save(c.getActor());
		socialIdentityRepository.delete(c);
	}

	public Collection<SocialIdentity> findAll() {
		return socialIdentityRepository.findAll();
	}

	// Other business methods ---------------------------

	public void checkPrincipal(SocialIdentity c) {
		Actor sponsor = actorService.findByPrincipal();
		Assert.isTrue(sponsor.equals(c.getActor()));
	}
	
	public SocialIdentity findOne(int id){
		return socialIdentityRepository.findOne(id);
	}
}
