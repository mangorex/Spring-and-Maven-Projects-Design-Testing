package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Text extends LearningMaterial {

	// Constructors -----------------------------------------------------------

	public Text() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String body;

	@NotBlank
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	// Relationships ----------------------------------------------------------

}
