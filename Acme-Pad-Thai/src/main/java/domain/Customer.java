package domain;

import java.util.Collection;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Customer extends Actor {

	// Constructors -----------------------------------------------------------

	public Customer() {
		super();
	}

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Collection<Comment> comments;
	private Collection<Customer> following;
	private Collection<Customer> followers;
	private Collection<Recipe> likedRecipes;
	private Collection<Recipe> dislikedRecipes;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "customer")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy="followers")
	public Collection<Customer> getFollowing() {
		return following;
	}

	public void setFollowing(Collection<Customer> following) {
		this.following = following;
	}
	
	@Valid
	@NotNull
	@ManyToMany
	public Collection<Customer> getFollowers() {
		return followers;
	}

	public void setFollowers(Collection<Customer> followers) {
		this.followers = followers;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "likedCustomers")
	public Collection<Recipe> getLikedRecipes() {
		return likedRecipes;
	}

	public void setLikedRecipes(Collection<Recipe> recipes) {
		this.likedRecipes = recipes;
	}

	@Valid
	@NotNull
	@ManyToMany(cascade = CascadeType.PERSIST)
	public Collection<Recipe> getDislikedRecipes() {
		return dislikedRecipes;
	}

	public void setDislikedRecipes(Collection<Recipe> recipes) {
		this.dislikedRecipes = recipes;
	}

}
