package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Property extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Property() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String name;

	@Column(unique = true)
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Relationships ----------------------------------------------------------
	private Collection<Valued> valueds;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "property")
	public Collection<Valued> getValueds() {
		return valueds;
	}

	public void setValueds(Collection<Valued> valueds) {
		this.valueds = valueds;
	}
}
