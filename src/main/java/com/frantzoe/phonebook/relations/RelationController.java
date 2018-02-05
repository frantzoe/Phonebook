package com.frantzoe.phonebook.relations;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RelationController {

    @Autowired
    RelationRepository relationRepository;

    // Get All Relations
    @GetMapping("/relations")
    public List<Relation> getAllRelations() {
        return relationRepository.findAll();
    }

    // Create a new Relation
    @PostMapping("/relations")
    public Relation createRelation(@Valid @RequestBody Relation relation) {
        return relationRepository.save(relation);
    }

}