package com.frantzoe.phonebook.users;

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

import com.frantzoe.phonebook.contacts.Contact;
import com.frantzoe.phonebook.relations.Relation;
import com.frantzoe.phonebook.relations.RelationRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RelationRepository relationRepository;

    // Get All Users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get All User Contacts
    @GetMapping("/users/{id}/contacts")
    public List<Contact> getAllContacts(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return null;
        }
        List<Contact> contacts = new ArrayList<>();
        for (Relation relation : relationRepository.findAll()) {
            if (relation.getUser().getId() == userId) {
                contacts.add(relation.getContact());
            }
        }
        return contacts;
    }

    // Create a new User
    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    // Get a Single User
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    // Update a User
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete a User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}