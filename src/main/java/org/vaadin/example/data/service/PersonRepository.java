package org.vaadin.example.data.service;

import org.vaadin.example.data.entity.Person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}