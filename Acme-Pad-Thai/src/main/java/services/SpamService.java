package services;

import java.util.HashSet;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Spam;

import repositories.SpamRepository;
import security.Authority;

@Service
@Transactional
public class SpamService {
	//Managed repository -------------------------------
	@Autowired
	private SpamRepository spamRepository;
	@Autowired
	private ActorService actorService;
	
	//Constructor --------------------------------------
	public SpamService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	//Simple CRUD methods ------------------------------
	
	public Spam create(){
		Spam s = new Spam();
		s.setSpamWords("viagra,cialis,sex,love");
		return s;
	}
	
	public Spam save(Spam s){
		checkPrincipal();
		Assert.notNull(s);
		return spamRepository.save(s);
	}
	
	public Spam findOne(){
		return spamRepository.findAll().get(0);
	}
	
	public Collection<String> findAll(){
		return new HashSet<String>(Arrays.asList(spamRepository.findAll().get(0).getSpamWords().split(",")));
	}
	
	//Other Spam methods ---------------------------
	public void checkPrincipal() {
		Actor a = actorService.findByPrincipal();
		for (Authority b : a.getUserAccount().getAuthorities()) {
			Assert.isTrue(b.getAuthority().equals("ADMINISTRATOR"));
		}

	}
}
