package com.frantzoe.phonebook.contacts;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.frantzoe.phonebook.users.User;

import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "Contact")
@Table(name = "contact",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"firstName", "lastName"})
})
public class Contact implements Serializable {
    
	@Id
	@Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@NotBlank
    private String firstName;

	@NotBlank
    private String lastName;
	
	@NotBlank
    private String email;

    @NotBlank
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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
    
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}