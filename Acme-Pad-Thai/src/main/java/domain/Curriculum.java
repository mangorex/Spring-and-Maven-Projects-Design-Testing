package domain;

import java.util.Collection;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Curriculum() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String photo;
	private String educationSection;
	private String experienceSection;
	private String hobbySection;

	@URL
	@NotBlank
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@NotBlank
	public String getEducationSection() {
		return educationSection;
	}

	public void setEducationSection(String educationSection) {
		this.educationSection = educationSection;
	}

	@NotBlank
	public String getExperienceSection() {
		return experienceSection;
	}

	public void setExperienceSection(String experienceSection) {
		this.experienceSection = experienceSection;
	}

	@NotBlank
	public String getHobbySection() {
		return hobbySection;
	}

	public void setHobbySection(String hobbySection) {
		this.hobbySection = hobbySection;
	}

	// Relationships ----------------------------------------------------------

	private Nutritionist nutritionist;
	private Collection<Endorser> endorsers;

	@Valid
	@NotNull
	@OneToOne(optional = false, cascade = CascadeType.PERSIST)
	public Nutritionist getNutritionist() {
		return nutritionist;
	}

	public void setNutritionist(Nutritionist nutricionist) {
		this.nutritionist = nutricionist;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Endorser> getEndorsers() {
		return endorsers;
	}

	public void setEndorsers(Collection<Endorser> endorsers) {
		this.endorsers = endorsers;
	}

}
