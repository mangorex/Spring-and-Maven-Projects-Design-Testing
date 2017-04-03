package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Recipe extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Recipe() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String ticker;
	private String title;
	private String summary;
	private Date creationDate;
	private Date updateDate;
	private Collection<String> pictures;
	private String hints;

	@Pattern(regexp = "^([0-9]{2})([0-9]{2})([0-9]{2})([-])([a-zA-Z]{4})$")
	@Column(unique = true)
	@NotBlank
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creation_Date) {
		this.creationDate = creation_Date;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date update_Date) {
		this.updateDate = update_Date;
	}

	@ElementCollection
	public Collection<String> getPictures() {
		return pictures;
	}

	public void setPictures(Collection<String> picture) {
		this.pictures = picture;
	}

	public String getHints() {
		return hints;
	}

	public void setHints(String hints) {
		this.hints = hints;
	}

	// Relationships ----------------------------------------------------------

	private Collection<Category> categories;
	private Collection<Customer> likedCustomers;
	private Collection<Customer> dislikedCustomers;
	private User author;
	private Collection<Contest> contests;
	private Collection<Step> steps;
	private Collection<Comment> comments;
	private Collection<Quantified> quantifieds;

	@Valid
	@NotNull
	@ManyToMany(cascade = CascadeType.PERSIST)
	public Collection<Category> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}

	@Valid
	@NotNull
	@ManyToMany(cascade = CascadeType.PERSIST)
	public Collection<Customer> getLikedCustomers() {
		return likedCustomers;
	}

	public void setLikedCustomers(Collection<Customer> customers) {
		this.likedCustomers = customers;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "dislikedRecipes", cascade = CascadeType.PERSIST)
	public Collection<Customer> getDislikedCustomers() {
		return dislikedCustomers;
	}

	public void setDislikedCustomers(Collection<Customer> customers) {
		this.dislikedCustomers = customers;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	@Valid
	@NotNull
	@ManyToMany(cascade = CascadeType.PERSIST)
	public Collection<Contest> getContests() {
		return contests;
	}

	public void setContests(Collection<Contest> contests) {
		this.contests = contests;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "recipe")
	public Collection<Step> getSteps() {
		return steps;
	}

	public void setSteps(Collection<Step> steps) {
		this.steps = steps;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "recipe")
	public Collection<Quantified> getQuantifieds() {
		return quantifieds;
	}

	public void setQuantifieds(Collection<Quantified> quantifieds) {
		this.quantifieds = quantifieds;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "recipe")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

}
