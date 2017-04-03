package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StepRepository;
import domain.Recipe;
import domain.Step;

@Service
@Transactional
public class StepService {
	// Managed repository -------------------------------
	@Autowired
	private RecipeService recipeService;

	@Autowired
	private StepRepository stepRepository;

	// Constructor --------------------------------------

	public StepService() {
		super();
	}

	public Step create(Recipe recipe) {
		Assert.notNull(recipe);
		Step s = new Step();
		s.setNumber(1);
		s.setRecipe(recipe);
		return s;
	}

	public Step create() {

		Step s = new Step();
		s.setRecipe(new Recipe());
		s.setNumber(1);
		return s;
	}

	public Step save(Step step) {
		Assert.notNull(step);
		step.setNumber(step.getRecipe().getSteps().size() + 1);
		Step saved = stepRepository.save(step);

		Recipe recipe = saved.getRecipe();

		Collection<Step> steps = recipe.getSteps();
		steps.add(saved);
		recipe.setSteps(steps);

		recipeService.saveRelations(recipe);

		return saved;
	}

	public void delete(Step step) {
		Assert.notNull(step);
		int numberStep = 1;
		List<Step> steps = new ArrayList<Step>();
		Recipe r = recipeService.findOne(step.getRecipe().getId());
		System.out.println("Id step a borrar" + step.getId() + ", Number: "
				+ step.getNumber() + ", Description: " + step.getDescription());
		steps.addAll(r.getSteps());
		steps.remove(step);
		r.setSteps(steps);
		Recipe savedRecipe = recipeService.saveRelations(r);

		stepRepository.delete(step);

		for (Step s : savedRecipe.getSteps()) {
			s.setNumber(numberStep);
			numberStep++;
			stepRepository.save(s);
			System.out.println("Id Step: " + s.getId() + ", Number: "
					+ s.getNumber() + ", Description: " + s.getDescription());
		}
	}


	public Step findOne(int s) {
		return stepRepository.findOne(s);
	}

	public Collection<Step> findAllStepByRecipe(int r) {
		return stepRepository.findAllStepByRecipe(r);
	}

	public Step saveWithoutRecipe(Step step) {
		Assert.notNull(step);
		Step saved = stepRepository.save(step);
		return saved;
	}

	public Object[] calculateAvgStdRecipe() {
		return stepRepository.calculateAvgStdRecipe();
	}

	public Collection<Step> findAll() {
		return stepRepository.findAll();
	}

	public void deleteWithoutRecipe(Step step) {
		Assert.notNull(step);
		stepRepository.delete(step);
	}
}
