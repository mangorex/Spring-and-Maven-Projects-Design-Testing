package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Ingredient extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Ingredient() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String name;
	private String description;
	private String picture;

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

	// Relationships ----------------------------------------------------------

	private Collection<Quantified> quantifieds;
	private Collection<Valued> valueds;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "ingredient")
	public Collection<Quantified> getQuantifieds() {
		return quantifieds;
	}

	public void setQuantifieds(Collection<Quantified> quantified) {
		this.quantifieds = quantified;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy="ingredient")
	public Collection<Valued> getValueds() {
		return valueds;
	}

	public void setValueds(Collection<Valued> valueds) {
		this.valueds = valueds;
	}

}
