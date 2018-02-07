package com.frantzoe.phonebook.contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.frantzoe.phonebook.users.User;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ContactController {

    @Autowired
    ContactRepository contactRepository;

    // Get All Contacts
    @GetMapping("/contacts")
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // Get Contact Users
    @GetMapping("/contacts/{id}/users")
    public Set<User> getContactUsers(@PathVariable(value = "id") Long contactId) {
        Contact contact = contactRepository.findOne(contactId);
        if (contact != null) {
            return contact.getUsers();
        }
        return null;
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
