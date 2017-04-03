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
public class Cook extends Actor {

	// Constructors -----------------------------------------------------------

	public Cook() {
		super();
	}

	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Collection<MasterClass> managedMasterClasses;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "cook")
	public Collection<MasterClass> getManagedMasterClasses() {
		return managedMasterClasses;
	}

	public void setManagedMasterClasses(Collection<MasterClass> masterClasses) {
		this.managedMasterClasses = masterClasses;
	}

}
