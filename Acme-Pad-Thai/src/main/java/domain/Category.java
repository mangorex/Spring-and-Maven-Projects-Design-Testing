package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Category() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String name;
	private String description;
	private String picture;
	private String tags;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@URL
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	// Relationships ----------------------------------------------------------

	private Collection<Recipe> recipes;
	private Collection<Category> categories;
	private Category categoryFather;

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "categories")
	public Collection<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Collection<Recipe> recipes) {
		this.recipes = recipes;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "categoryFather")
	public Collection<Category> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}

	@Valid
	@ManyToOne(optional = true)
	public Category getCategoryFather() {
		return categoryFather;
	}

	public void setCategoryFather(Category categoryFather) {
		this.categoryFather = categoryFather;
	}

}
