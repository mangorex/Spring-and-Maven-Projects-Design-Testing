package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Endorser extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Endorser() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String name;
	private String homepage;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@URL
	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	// Relationships ----------------------------------------------------------
	
	private Collection<Curriculum> curricula;

	@Valid
	@NotNull
	@ManyToMany(cascade = CascadeType.PERSIST)
	public Collection<Curriculum> getCurricula() {
		return curricula;
	}

	public void setCurricula(Collection<Curriculum> curricula) {
		this.curricula = curricula;
	}
}
