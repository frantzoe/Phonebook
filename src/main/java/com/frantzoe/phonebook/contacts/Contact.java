package com.frantzoe.phonebook.contacts;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frantzoe.phonebook.users.User;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "Contact")
@Table(name = "contact")
/*
	uniqueConstraints = {
		@UniqueConstraint(columnNames = { "first_name", "last_name" })
})
*/
public class Contact implements Serializable {
    
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

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contacts")
	@JsonIgnore
	private Set<User> users = new HashSet<User>();

	public Contact() {
		super();
	}

	public Contact(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public Contact(String firstName, String lastName, String email, Set<User> users) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.users = users;
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
	
	public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
	}
	
	@Override
    public String toString() {
        String result = String.format("Contact [id=%d, firstName='%s', lastName='%s']%n", id, firstName, lastName);
        if (users != null) {
            for(User user : users) {
                result += String.format("Publisher[id=%d, firstName='%s', lastName='%s']%n", user.getId(), user.getFirstName(), user.getLastName());
            }
        }
        return result;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(firstName, contact.firstName) && Objects.equals(lastName, contact.lastName) && Objects.equals(email, contact.email);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(firstName) + Objects.hash(lastName) + Objects.hash(email);
    }

}
