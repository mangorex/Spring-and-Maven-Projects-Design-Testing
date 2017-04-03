package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Customer {
	// Constructors -----------------------------------------------------------

	public User() {
		super();
	}

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	
	private Collection<Recipe> authoredRecipes;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "author")
	public Collection<Recipe> getAuthoredRecipes() {
		return authoredRecipes;
	}

	public void setAuthoredRecipes(Collection<Recipe> authoredRecipes) {
		this.authoredRecipes = authoredRecipes;
	}

}
