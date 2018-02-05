package com.frantzoe.phonebook.contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import com.frantzoe.phonebook.relations.Relation;
import com.frantzoe.phonebook.relations.RelationRepository;
import com.frantzoe.phonebook.users.User;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContactController {

    @Autowired
    ContactRepository contactRepository;
    @Autowired
    RelationRepository relationRepository;

    // Get All Contacts
    @GetMapping("/contacts")
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // Get Contact User
    @GetMapping("/contacts/{id}/user")
    public ResponseEntity<User> getContactUser(@PathVariable(value = "id") Long contactId) {
        Contact contact = contactRepository.findOne(contactId);
        if (contact == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(contact.getUser());
    }

    // Get Contact Users
    @GetMapping("/contacts/{id}/users")
    public List<User> getContactUsers(@PathVariable(value = "id") Long contactId) {
        Contact contact = contactRepository.findOne(contactId);
        if (contact == null) {
            return null;
        }
        List<User> users = new ArrayList<>();
        for (Relation relation : relationRepository.findAll()) {
            if (relation.getContact().getId() == contactId) {
                users.add(relation.getUser());
            }
        }
        return users;
    }

    // Create a new Contact
    @PostMapping("/contacts")
    public Contact createContact(@Valid @RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    // Get a Single Contact
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable(value = "id") Long contactId) {
        Contact contact = contactRepository.findOne(contactId);
        if (contact == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(contact);
    }

    // Update a Contact
    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable(value = "id") Long contactId, @Valid @RequestBody Contact contactDetails) {
        Contact contact = contactRepository.findOne(contactId);
        if (contact == null) {
            return ResponseEntity.notFound().build();
        }
        contact.setFirstName(contactDetails.getFirstName());
        contact.setLastName(contactDetails.getLastName());
        contact.setEmail(contactDetails.getEmail());

        Contact updatedContact = contactRepository.save(contact);
        return ResponseEntity.ok(updatedContact);
    }

    // Delete a Contact
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Contact> deleteContact(@PathVariable(value = "id") Long contactId) {
        Contact contact = contactRepository.findOne(contactId);
        if (contact == null) {
            return ResponseEntity.notFound().build();
        }

        contactRepository.delete(contact);
        return ResponseEntity.ok().build();
    }
}