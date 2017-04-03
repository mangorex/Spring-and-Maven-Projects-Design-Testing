package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Message() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String priority;
	private String sender;
	private String recipient;
	private Date moment;
	private String subject;
	private String body;

	private boolean spam;

	@Pattern(regexp = "^(HIGH|NEUTRAL|LOW)$")
	@NotBlank
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public boolean getSpam() {
		return spam;
	}

	public void setSpam(boolean spam) {
		this.spam = spam;
	}

	@NotBlank
	@Email
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@NotBlank
	@Email
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@NotNull
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@NotNull
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Message clone() {
		Message result = new Message();
		result.setBody(this.getBody());
		result.setFolder(this.getFolder());
		result.setMoment(this.getMoment());
		result.setPriority(this.getPriority());
		result.setRecipient(this.getRecipient());
		result.setSender(this.getSender());
		result.setSpam(this.getSpam());
		result.setSubject(this.getSubject());
		result.setId(this.getId());
		result.setVersion(this.getVersion());
		return result;
	}

	// Relationships ----------------------------------------------------------

	private Folder folder;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}
}