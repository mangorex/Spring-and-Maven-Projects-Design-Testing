package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Ingredient;
import domain.Quantified;
import domain.Recipe;

import repositories.QuantifiedRepository;

@Service
@Transactional
public class QuantifiedService {
	// Managed repository -------------------------------
	
	@Autowired
	private QuantifiedRepository quantifiedRepository;
	
	// Constructor --------------------------------------
	public QuantifiedService(){
		super();
	}
	
	// Simple CRUD methods ------------------------------
	
	public Quantified create(Recipe recipe, Ingredient ingredient){
		Assert.notNull(recipe);
		Assert.notNull(ingredient);
		Quantified quantified = new Quantified();
		
		quantified.setIngredient(ingredient);
		quantified.setRecipe(recipe);
		return quantified;
		
	}
	
	public Quantified create(){
		Quantified quantified = new Quantified();
		quantified.setIngredient(new Ingredient());
		quantified.setRecipe(new Recipe());
		return quantified;
		
	}
	
	public Quantified create(Recipe recipe){
		Assert.notNull(recipe);
		
		Quantified quantified = new Quantified();
		quantified.setIngredient(new Ingredient());
		quantified.setRecipe(recipe);
		return quantified;
		
	}
	
	public Quantified save(Quantified quantified){
		Assert.notNull(quantified);
		return quantifiedRepository.save(quantified);	
	}

	public void delete(Quantified q) {
		quantifiedRepository.delete(q);
	}

	public Quantified findOne(int id){
		return quantifiedRepository.findOne(id);
	}
}
