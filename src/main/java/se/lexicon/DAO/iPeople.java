package se.lexicon.DAO;

import se.lexicon.Model.Person;

import java.util.List;

public interface iPeople {
    Person create(Person person);
    List<Person> findAll();
    Person findById(int id);
    List<Person> findByName(String name);
    Person update(Person person);
    boolean deleteById(int id);
}
