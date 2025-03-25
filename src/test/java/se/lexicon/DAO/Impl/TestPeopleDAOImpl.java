package se.lexicon.DAO.Impl;

import org.junit.jupiter.api.*;
import se.lexicon.Model.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestPeopleDAOImpl {

    private static Connection connection;
    private static PeopleDaoImpl dao;

    @BeforeAll
    static void beforeAll() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/todoit";
        String username = "root";
        String password = "1234";
        connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(false); // For transaction control
        dao = new PeopleDaoImpl(connection);
    }

    @AfterAll
    static void afterAll() throws SQLException{
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @AfterEach
    void setUp(){
        dao.resetTableIfACFalse(); // Clear the table before each test
    }

    @Test
    void create_PersonAndReturnWithId(){
        //Arrange
        Person person1 = new Person("Johnny", "Silverhand");
        //Act
        Person result = dao.create(person1);

        //Assert
        assertNotNull(result);
        assertTrue(result.getId() > 0);
        assertEquals("Johnny", result.getFirstName());
        assertEquals("Silverhand", result.getLastName());
        //Verify by finding ID
        Person found = dao.findById(result.getId());
        assertNotNull(found);
        assertEquals(result.getId(), found.getId());
        assertEquals("Johnny", found.getFirstName());
        assertEquals("Silverhand", found.getLastName());
    }

    @Test
    void findAll_AndReturnAllPersistedPeople(){
        // Arrange
        dao.create(new Person("Goro", "Takemura"));
        dao.create(new Person("Everlyn", "Parker"));

        // Act
        List<Person> result = dao.findAll();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> "Goro".equals(p.getFirstName())));
        assertTrue(result.stream().anyMatch(p -> "Everlyn".equals(p.getFirstName())));
    }

    @Test
    void findById_shouldReturnCorrectPerson() {
        // Arrange
        Person expected = dao.create(new Person("Emma", "Watson"));
        int id = expected.getId();

        // Act
        Person result = dao.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Emma", result.getFirstName());
        assertEquals("Watson", result.getLastName());
    }

    @Test
    void findById_shouldReturnNullForNonExistingId() {
        // Act
        Person result = dao.findById(9999);

        // Assert
        assertNull(result);
    }

    @Test
    void findByName_shouldReturnMatchingPeople() {
        // Arrange
        dao.create(new Person("John", "Doe"));
        dao.create(new Person("John", "Smith"));
        dao.create(new Person("Jane", "Doe"));

        // Act
        List<Person> result = dao.findByName("John");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> "John".equals(p.getFirstName())));
    }

    @Test
    void update_shouldModifyExistingPerson() {
        // Arrange
        Person original = dao.create(new Person("Original", "Name"));
        Person updated = new Person(original.getId(), "Updated", "Name");

        // Act
        Person result = dao.update(updated);

        // Assert
        assertNotNull(result);
        assertEquals(original.getId(), result.getId());
        assertEquals("Updated", result.getFirstName());
        assertEquals("Name", result.getLastName());

        // Verify the change persisted
        Person fromDb = dao.findById(original.getId());
        assertEquals("Updated", fromDb.getFirstName());
    }

    @Test
    void update_shouldThrowForInvalidId() {
        // Arrange
        Person invalid = new Person(-1, "Invalid", "ID");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> dao.update(invalid));
    }

    @Test
    void deleteById_shouldRemovePerson() {
        // Arrange
        Person toDelete = dao.create(new Person("To", "Delete"));
        int id = toDelete.getId();

        // Act
        boolean result = dao.deleteById(id);

        // Assert
        assertTrue(result);
        assertNull(dao.findById(id));
    }

    @Test
    void deleteById_shouldReturnFalseForNonExistingId() {
        // Act
        boolean result = dao.deleteById(9999);

        // Assert
        assertFalse(result);
    }
}
