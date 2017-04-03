package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Presentation extends LearningMaterial {

	// Constructors -----------------------------------------------------------

	public Presentation() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String path;

	@URL
	@Pattern(regexp = "^(https?\\:\\/\\/)?es.slideshare.net\\/.+$")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
