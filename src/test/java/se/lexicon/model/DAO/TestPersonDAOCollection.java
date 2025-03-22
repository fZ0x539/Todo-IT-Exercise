package se.lexicon.model.DAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.DAO.Sequencer.PersonIdSequencer;
import se.lexicon.DAO.impl.PersonDAOCollection;
import se.lexicon.model.AppRole;
import se.lexicon.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestPersonDAOCollection {

    private Person person1;
    private Person person2;
    private PersonDAOCollection testObject;

    @BeforeEach
    public void setup(){
        person1 = new Person("Olle", "Eriksson", "olle.eriksson@example.com", "user1", "pwd1", AppRole.ROLE_APP_USER );
        person2 = new Person( "Stefan", "Bertilsson", "stefan.bertilsson@example.com", "user2", "pwd2", AppRole.ROLE_APP_USER );
        testObject = new PersonDAOCollection();
    }

    @Test
    public void testAddUser(){
        Assertions.assertEquals(testObject.persist(person1), person1);
        Assertions.assertEquals(testObject.persist(person2), person2);
    }

    @Test
    public void testAlreadyAddedPerson(){
        testObject.persist(person1);
        Assertions.assertNull(testObject.persist(person1));
    }

    @Test
    public void testFindById(){
        testObject.persist(person1);
        Assertions.assertEquals(testObject.findById(PersonIdSequencer.getCurrentId()), person1);
    }

    @Test
    public void testIdNotFound(){
        testObject.persist(person1);
        Assertions.assertNull(testObject.findById(PersonIdSequencer.getCurrentId() + 100));
    }

    @Test
    public void testFindByEmail(){
        testObject.persist(person1);
        Assertions.assertEquals(testObject.findByEmail("olle.eriksson@example.com"), person1);
    }

    @Test
    public void testFindAll(){
        testObject.persist(person1);
        testObject.persist(person2);
        List<Person> tempList = new ArrayList<>(Arrays.asList(person1, person2));

        Assertions.assertEquals(testObject.findAll(), tempList);
    }

    @Test
    public void testRemovePerson(){
        testObject.persist(person1);
        testObject.persist(person2);
        List<Person> tempList1 = new ArrayList<>(Arrays.asList(person1));
        List<Person> tempList2 = new ArrayList<>(Arrays.asList(person2));
        testObject.remove(person2.getId());
        Assertions.assertEquals(testObject.findAll(), tempList1);
        testObject.remove(person1.getId());
        testObject.persist(person2);
        Assertions.assertEquals(testObject.findAll(), tempList2);
    }
}
