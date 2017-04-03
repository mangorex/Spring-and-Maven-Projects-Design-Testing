package services;

import java.util.HashSet;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.IngredientRepository;
import security.Authority;
import domain.Actor;
import domain.Ingredient;
import domain.Nutritionist;
import domain.Quantified;
import domain.Recipe;
import domain.Valued;

@Service
@Transactional
public class IngredientService {
	//Managed repository -------------------------------
	@Autowired
	private IngredientRepository ingredientRepository;
	@Autowired
	private NutritionistService nutritionistService;
	@Autowired
	private ValuedService valuedService;
	@Autowired
	private QuantifiedService quantifiedService;
	@Autowired
	private ActorService actorService;

	//Constructor --------------------------------------
	public IngredientService() {
		super();
	}
	
	//Simple CRUD methods ------------------------------
	public Ingredient create(Nutritionist n){
		Assert.notNull(n);
		
		Ingredient i=new Ingredient();
		i.setValueds(new HashSet<Valued>());
		i.setQuantifieds(new HashSet<Quantified>());

		return i;
	}
	
	public Ingredient save(Ingredient i){
		Assert.notNull(i);
		Ingredient ing=ingredientRepository.save(i);
		
		for(Valued v:i.getValueds()){
			v.setIngredient(i);
			valuedService.save(v);
		}
		
		for(Quantified q:i.getQuantifieds()){
			q.setIngredient(i);
			quantifiedService.save(q);
		}
		
		Nutritionist n=checkNutriotionist();
		if(!n.getIngredients().contains(ing)){
			HashSet<Ingredient> ai=new HashSet<Ingredient>();
			ai.add(ing);
			n.setIngredients(ai);
			nutritionistService.save(n);
		}
		return ing;
	}
	
	public Ingredient findOne(Integer id){
		return ingredientRepository.findOne(id);
	}
	
	public Collection<Ingredient> findIngredientByRecipe(Recipe r){
		return ingredientRepository.findIngredientByRecipe(r.getId());
	}
	
	public boolean delete(Ingredient i){
		boolean res=false;
		Assert.notNull(i);
		
		if(ingredientRepository.calculateNumRecipeByIngredient(i.getId())<=0){
			res=true;
			
			for(Valued v:i.getValueds()){
				v.setIngredient(new Ingredient());
				valuedService.save(v);
			}
			
			Nutritionist n=checkNutriotionist();
			if(n.getIngredients().contains(i)){
				n.getIngredients().remove(i);
				nutritionistService.save(n);
			}
			ingredientRepository.delete(i.getId());
		}
		
		return res;
		
	}
	
	//Other business methods ---------------------------
	
	public void checkPrincipal() {
		Actor a = actorService.findByPrincipal();
		for (Authority b : a.getUserAccount().getAuthorities()) {
			Assert.isTrue(b.getAuthority().equals("NUTRITIONIST"));
		}

	}
	
	public Nutritionist checkNutriotionist() {
		Nutritionist a = (Nutritionist) actorService.findByPrincipal();
		return a;
	}
	
	public Ingredient saveWithoutQuantified(Ingredient i){
		Assert.notNull(i);
		
//		Nutritionist n=nutritionistService.findNutritionistByIngredient(i.getId());
//		n.getIngredients().add(i);
//		nutritionistService.save(n);
//		
//		for(Valued v:i.getValueds()){
//			v.setIngredient(i);
//			valuedService.save(v);
//		}

		return ingredientRepository.save(i);
	}

	public Collection<Ingredient> findByNutritionist(Nutritionist n){
		Collection<Ingredient> ingredients;
		
		ingredients=n.getIngredients();
		
		return ingredients;
	}
	
	public Collection<Ingredient> findAll(){
		return ingredientRepository.findAll();
	}
}
