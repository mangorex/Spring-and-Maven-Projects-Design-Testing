package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

	// Constructors -----------------------------------------------------------

	public Administrator() {
		super();
	}

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Collection<Contest> contests;
	private Collection<MasterClass> promotedMasterClasses;
	private Collection<Category> categories;
	private Collection<Cook> cooks;

	@Valid
	@NotNull
	@OneToMany
	public Collection<Cook> getCooks() {
		return cooks;
	}

	public void setCooks(Collection<Cook> cooks) {
		this.cooks = cooks;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Contest> getContests() {
		return contests;
	}

	public void setContests(Collection<Contest> contests) {
		this.contests = contests;
	}

	@Valid
	@NotNull
	@ManyToMany(cascade = CascadeType.PERSIST)
	public Collection<MasterClass> getPromotedMasterClasses() {
		return promotedMasterClasses;
	}

	public void setPromotedMasterClasses(Collection<MasterClass> masterClasses) {
		this.promotedMasterClasses = masterClasses;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Category> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}

}
