package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Spam extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Spam() {
		super();

	}

	// Attributes -------------------------------------------------------------

	private String spamWords;


	public String getSpamWords() {

		return spamWords;
	}

	public void setSpamWords(String spamWords) {
		this.spamWords = spamWords;
	}

	// Relationships ----------------------------------------------------------

}
