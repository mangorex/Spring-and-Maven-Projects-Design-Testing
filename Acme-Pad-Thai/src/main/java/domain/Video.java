package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Video extends LearningMaterial {

	// Constructors -----------------------------------------------------------

	public Video() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String identifier;

	@Pattern(regexp = "^(((http:\\/\\/|https:\\/\\/|)(www\\.|)youtube\\.(es|com)\\/watch\\?v=([a-zA-Z0-9-]{11}))|)$")
	@URL
	@NotBlank
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	// Relationships ----------------------------------------------------------

}