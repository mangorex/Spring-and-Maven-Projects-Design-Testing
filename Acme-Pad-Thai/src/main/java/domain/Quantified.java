package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Entity
@Access(AccessType.PROPERTY)
public class Quantified extends DomainEntity {
	// Constructors -----------------------------------------------------------

	public Quantified() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private Double quantityDouble;
	private Integer quantityInteger;
	private String unit;

	@Min(0)
	@Digits(integer = 6, fraction = 2)
	public Double getQuantityDouble() {
		return quantityDouble;
	}

	public void setQuantityDouble(Double quantityDouble) {
		this.quantityDouble = quantityDouble;
	}

	@Min(1)
	public Integer getQuantityInteger() {
		return quantityInteger;
	}

	public void setQuantityInteger(Integer quantityInteger) {
		this.quantityInteger = quantityInteger;
	}

	@Pattern(regexp = "^(grams|kilo-grams|ounces|pounds|millilitres|litres|spoons|cups|pieces)$")
	@NotNull
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	// Relationships ----------------------------------------------------------

	private Recipe recipe;
	private Ingredient ingredient;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	@Valid
	@NotNull
	@ManyToOne(cascade=CascadeType.PERSIST)
	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

}
