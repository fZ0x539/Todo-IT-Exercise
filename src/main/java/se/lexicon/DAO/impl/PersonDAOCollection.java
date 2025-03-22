package se.lexicon.DAO.impl;


import se.lexicon.DAO.Sequencer.PersonIdSequencer;
import se.lexicon.DAO.iPersonDAO;
import se.lexicon.model.Person;
import se.lexicon.view.ConsoleUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PersonDAOCollection implements iPersonDAO {

    private List<Person> storage = new ArrayList<>();

    @Override
    public Person persist(Person person) {
        Objects.requireNonNull(person, "Person cant be null");
        if (!storage.contains(person)) {
            person.setId(PersonIdSequencer.nextId());
            storage.add(person);
            return person;
        } else
            ConsoleUI.printErrorMessage("This person has already been added");

        return null;
    }

    @Override
    public Person findById(int id) {
        for (Person person : storage) {
            if (person.getId() == id)
                return person;
        }
        ConsoleUI.printErrorMessage("Person with this id not found.");
        return null;
    }

    @Override
    public Person findByEmail(String email) {
        for (Person person : storage) {
            if (person.getEmail().equals(email))
                return person;
        }
        ConsoleUI.printErrorMessage("Person with this email not found.");
        return null;
    }

    @Override
    public List<Person> findAll() {
        return storage;
    }

    @Override
    public void remove(int id) {
        if (storage.removeIf(person -> person.getId() == id))
            ConsoleUI.printSuccessMessage("Person with ID: " + id + " has successfully been removed");
        else
            ConsoleUI.printErrorMessage("Person with id " + id + " not found");

    }
}
