package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor {

	// Constructors -----------------------------------------------------------

	public Sponsor() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String companyName;

	@NotBlank
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	// Relationships ----------------------------------------------------------

	private Collection<Bill> bills;
	private Collection<CreditCard> creditCards;
	private Collection<Campaign> campaigns;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "sponsor")
	public Collection<Bill> getBills() {
		return bills;
	}

	public void setBills(Collection<Bill> bills) {
		this.bills = bills;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "sponsor")
	public Collection<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(Collection<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "sponsor")
	public Collection<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(Collection<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

}
