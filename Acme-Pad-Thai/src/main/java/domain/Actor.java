package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Actor() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String name;
	private String surname;
	private String emailAddress;
	private String phoneNumber;
	private String postalAddress;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	@Pattern(regexp = "^((([+])([0-9]{1,3})([ ])?)?(([0-9]{1})([0-9]{1})([0-9]{1}))?([ ])?([a-zA-Z0-9- ]{4,}))?$")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	// Relationships ----------------------------------------------------------

	private Collection<SocialIdentity> socialIdentities;
	private Collection<MasterClass> masterClasses;
	private Collection<Folder> folders;
	private UserAccount userAccount;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "actor")
	public Collection<SocialIdentity> getSocialIdentities() {
		return socialIdentities;
	}

	public void setSocialIdentities(Collection<SocialIdentity> socialIdentity) {
		this.socialIdentities = socialIdentity;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "actor", cascade = CascadeType.PERSIST)
	public Collection<Folder> getFolders() {
		return folders;
	}

	public void setFolders(Collection<Folder> folder) {
		this.folders = folder;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "actors")
	public Collection<MasterClass> getMasterClasses() {
		return masterClasses;
	}

	public void setMasterClasses(Collection<MasterClass> masterClasses) {
		this.masterClasses = masterClasses;
	}

	@Valid
	@NotNull
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
