package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Nutritionist extends Customer{

	// Constructors -----------------------------------------------------------
	
	public Nutritionist(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private Collection<Ingredient> ingredients;
	private Curriculum curriculum;
	
	@Valid
	@NotNull
	@ManyToMany
	public Collection<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Collection<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Valid
	@OneToOne(optional=true)
	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}
	
	
}
