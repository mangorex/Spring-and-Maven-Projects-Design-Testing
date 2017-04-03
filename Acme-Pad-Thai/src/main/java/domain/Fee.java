package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Fee extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Fee() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private double costPerBanner = 0.25;

	@Min(0)
	public double getCostPerBanner() {
		return costPerBanner;
	}

	public void setCostPerBanner(double costPerBanner) {
		this.costPerBanner = costPerBanner;
	}

	// Relationships ----------------------------------------------------------
}
