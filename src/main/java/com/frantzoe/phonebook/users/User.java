package com.frantzoe.phonebook.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frantzoe.phonebook.contacts.Contact;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "User")
@Table(name = "user")
/*
	uniqueConstraints = {
		@UniqueConstraint(columnNames = { "first_name", "last_name" })
})
*/
public class User implements Serializable {
	
	@Id
	@Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@NotBlank
	@Column(name = "first_name")
    private String firstName;

	@NotBlank
	@Column(name = "last_name")
    private String lastName;
	
	@NotBlank
	@Column(name = "email")
	private String email;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "relation",
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "contact_id", referencedColumnName = "id"))
	@JsonIgnore
	private Set<Contact> contacts = new HashSet<Contact>();

	public User() {
		super();
	}

	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User(String firstName, String lastName, String email, Set<Contact> contacts) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.contacts = contacts;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Contact> getContacts() {
        return this.contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(firstName) + Objects.hash(lastName) + Objects.hash(email);
    }

}
