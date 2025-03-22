package se.lexicon.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPerson {
    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person(1,"John", "Doe", "john.doe@example.com", "emsweden", "password", AppRole.ROLE_APP_ADMIN);
    }

    @Test
    void testPersonConstructor() {
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals("john.doe@example.com", person.getEmail());
        assertTrue(person.getId() > 0);
    }

    @Test
    void testSetFirstName() {
        person.setFirstName("Jane");
        assertEquals("Jane", person.getFirstName());
    }

    @Test
    void testSetLastName() {
        person.setLastName("Smith");
        assertEquals("Smith", person.getLastName());
    }

    @Test
    void testSetEmail() {
        person.setEmail("jane.smith@example.com");
        assertEquals("jane.smith@example.com", person.getEmail());
    }

}