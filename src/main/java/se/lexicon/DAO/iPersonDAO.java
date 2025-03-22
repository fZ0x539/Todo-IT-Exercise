package se.lexicon.DAO;

import se.lexicon.model.Person;

import java.util.List;

public interface iPersonDAO {
    Person persist(Person person);
    Person findById(int id);
    Person findByEmail(String email);
    List<Person> findAll();
    void remove(int id);
}
