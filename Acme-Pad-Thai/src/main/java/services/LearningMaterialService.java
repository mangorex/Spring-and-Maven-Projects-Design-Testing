package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LearningMaterialRepository;
import domain.LearningMaterial;

@Service
@Transactional
public class LearningMaterialService {
	// Managed repository -------------------------------
	@Autowired
	private LearningMaterialRepository learningMaterialRepository;
	
	// Constructor --------------------------------------
	
	public LearningMaterialService()
	{
		super();
	}
	
	// Simple CRUD methods ------------------------------
	public LearningMaterial findOneLM(int lm){
		Assert.notNull(lm);
		return learningMaterialRepository.findOne(lm);
	}
	
	public Collection<LearningMaterial> findAllLMByMC(int mc){
		Assert.notNull(mc);
		return learningMaterialRepository.findAllLMByMClass(mc);
	}
	
	public Collection<Object[]> calculateAvgFromMClass(){
		return learningMaterialRepository.calculateAvgFromMClass();
	}
	
	public Collection<LearningMaterial> findAll()
	{
		return learningMaterialRepository.findAll();
	}
}
