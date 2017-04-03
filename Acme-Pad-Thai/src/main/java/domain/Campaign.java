package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Campaign extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Campaign() {
		super();
	}

	// Attributes -------------------------------------------------------------
	private String name;
	private Date startDate;
	private Date endDate;
	private String banners;
	private int numBanners;
	private int maxBannersDisplayed;
	private boolean star;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean getStar() {
		return star;
	}

	public void setStar(boolean star) {
		this.star = star;
	}

	@Min(0)
	public int getNumBanners() {
		return numBanners;
	}

	public void setNumBanners(int numBanners) {
		this.numBanners = numBanners;
	}

	@NotBlank
	public String getBanners() {
		return banners;
	}

	public void setBanners(String banners) {
		this.banners = banners;
	}

	@Min(0)
	public int getMaxBannersDisplayed() {
		return maxBannersDisplayed;
	}

	public void setMaxBannersDisplayed(int maxBannerDisplayed) {
		maxBannersDisplayed = maxBannerDisplayed;
	}

	// Relationships ----------------------------------------------------------

	private Sponsor sponsor;
	private Collection<Bill> bills;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "campaign")
	public Collection<Bill> getBills() {
		return bills;
	}

	public void setBills(Collection<Bill> bills) {
		this.bills = bills;
	}

}
