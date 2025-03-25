package se.lexicon.DAO.Impl;

import org.junit.jupiter.api.*;
import se.lexicon.Model.Person;
import se.lexicon.Model.Todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTodoDAOImpl {
    private static Connection connection;
    private static TodoDaoImpl todoDao;
    private static PeopleDaoImpl peopleDao;

    @BeforeAll
    static void beforeAll() throws SQLException {
        // Initialize database connection
        String url = "jdbc:mysql://localhost:3306/todoit";
        String username = "root";
        String password = "1234";
        connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(false); // For transaction control

        // Initialize DAOs
        todoDao = new TodoDaoImpl(connection);
        peopleDao = new PeopleDaoImpl(connection);
    }

    @BeforeEach
    void setUp() throws SQLException {
        // Start transaction
        connection.setAutoCommit(false);
        // Clear all data reliably
        clearAllTables();
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Always rollback after each test
        connection.rollback();
    }

    @AfterAll
    static void afterAll() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    private void clearAllTables() throws SQLException {
        // Disable foreign key checks
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");

            // Clear tables
            stmt.execute("TRUNCATE TABLE todo_item");
            stmt.execute("TRUNCATE TABLE person");

            // Reset auto increment
            stmt.execute("ALTER TABLE todo_item AUTO_INCREMENT = 1");
            stmt.execute("ALTER TABLE person AUTO_INCREMENT = 1");

            // Re-enable foreign key checks
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    @Test
    void create_shouldPersistTodoWithAssignee() {
        // Arrange
        Person assignee = peopleDao.create(new Person("John", "Doe"));
        Todo todo = new Todo("Test Task", "Test Description", LocalDate.now().plusDays(7), false, assignee);

        // Act
        Todo result = todoDao.create(todo);

        // Assert
        assertNotNull(result);
        assertTrue(result.getId() > 0);
        assertEquals("Test Task", result.getTitle());
        assertEquals("Test Description", result.getTaskDescription());
        assertEquals(assignee.getId(), result.getAssignee().getId());

        // Verify by finding the todo
        Todo found = todoDao.findById(result.getId());
        assertNotNull(found);
        assertEquals(result.getId(), found.getId());
        assertEquals(assignee.getId(), found.getAssignee().getId());
    }

    @Test
    void create_shouldPersistTodoWithoutAssignee() {
        // Arrange
        Todo todo = new Todo("Unassigned Task", "Description", LocalDate.now().plusDays(1), false);

        // Act
        Todo result = todoDao.create(todo);

        // Assert
        assertNotNull(result);
        assertTrue(result.getId() > 0);
        assertNull(result.getAssignee());

        // Verify by finding the todo
        Todo found = todoDao.findById(result.getId());
        assertNotNull(found);
        assertNull(found.getAssignee());
    }

    @Test
    void create_shouldThrowWhenAssigneeDoesNotExist() {
        // Arrange
        Person nonExistentAssignee = new Person(9999, "Non", "Existent");
        Todo todo = new Todo("Task", "Desc", LocalDate.now(), false, nonExistentAssignee);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> todoDao.create(todo));
    }

    @Test
    void findAll_shouldReturnAllTodos() {
        // Arrange
        Person assignee = peopleDao.create(new Person("Alice", "Smith"));
        todoDao.create(new Todo("Task 1", "Desc 1", LocalDate.now().plusDays(1), false));
        todoDao.create(new Todo("Task 2", "Desc 2", LocalDate.now().plusDays(2), true, assignee));

        // Act
        List<Todo> result = todoDao.findAll();
        result.forEach(System.out::println);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(t -> "Task 1".equals(t.getTitle())));
        assertTrue(result.stream().anyMatch(t -> "Task 2".equals(t.getTitle())));
        assertTrue(result.stream().anyMatch(t -> t.getAssignee() == null));
        assertTrue(result.stream().anyMatch(t -> t.getAssignee() != null));
    }

    @Test
    void findById_shouldReturnCorrectTodo() {
        // Arrange
        Todo expected = todoDao.create(new Todo("Find Me", "Description", LocalDate.now(), false));
        int id = expected.getId();

        // Act
        Todo result = todoDao.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Find Me", result.getTitle());
    }

    @Test
    void findById_shouldReturnNullForNonExistingId() {
        // Act
        Todo result = todoDao.findById(9999);

        // Assert
        assertNull(result);
    }

    @Test
    void findByDoneStatus_shouldFilterCompletedTasks() {
        // Arrange
        todoDao.create(new Todo("Task 1", "Desc", LocalDate.now(), false));
        todoDao.create(new Todo("Task 2", "Desc", LocalDate.now(), true));
        todoDao.create(new Todo("Task 3", "Desc", LocalDate.now(), true));

        // Act
        List<Todo> completed = todoDao.findByDoneStatus(true);
        List<Todo> notCompleted = todoDao.findByDoneStatus(false);

        // Assert
        assertEquals(2, completed.size());
        assertEquals(1, notCompleted.size());
        assertTrue(completed.stream().allMatch(Todo::isDone));
        assertTrue(notCompleted.stream().noneMatch(Todo::isDone));
    }

    @Test
    void findByAssignee_withPersonId_shouldReturnAssignedTasks() {
        // Arrange
        Person assignee1 = peopleDao.create(new Person("John", "Doe"));
        Person assignee2 = peopleDao.create(new Person("Jane", "Smith"));

        todoDao.create(new Todo("Task 1", "Desc", LocalDate.now(), false, assignee1));
        todoDao.create(new Todo("Task 2", "Desc", LocalDate.now(), true, assignee1));
        todoDao.create(new Todo("Task 3", "Desc", LocalDate.now(), false, assignee2));

        // Act
        List<Todo> result = todoDao.findByAssignee(assignee1.getId());

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(t -> t.getAssignee().getId() == assignee1.getId()));
    }

    @Test
    void findByAssignee_withPersonObject_shouldReturnAssignedTasks() {
        // Arrange
        Person assignee = peopleDao.create(new Person("Bob", "Johnson"));
        todoDao.create(new Todo("Task 1", "Desc", LocalDate.now(), false, assignee));
        todoDao.create(new Todo("Task 2", "Desc", LocalDate.now(), true));

        // Act
        List<Todo> result = todoDao.findByAssignee(assignee);

        // Assert
        assertEquals(1, result.size());
        assertEquals(assignee.getId(), result.get(0).getAssignee().getId());
    }

    @Test
    void findByUnassignedStatus_shouldReturnUnassignedTasks() {


        // Arrange
        Person assignee = peopleDao.create(new Person("Alice", "Cooper"));
        todoDao.create(new Todo("Assigned", "Desc", LocalDate.now(), false, assignee));
        todoDao.create(new Todo("Unassigned 1", "Desc", LocalDate.now(), false));
        todoDao.create(new Todo("Unassigned 2", "Desc", LocalDate.now(), true));

        // Act
        List<Todo> result = todoDao.findByUnassignedStatus();
        result.forEach(System.out::println);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(t -> t.getAssignee() == null));
    }

    @Test
    void update_shouldModifyExistingTodo() {
        // Arrange
        Todo original = todoDao.create(new Todo("Original", "Desc", LocalDate.now(), false));
        Todo updated = new Todo(
                original.getId(),
                "Updated",
                "New Desc",
                LocalDate.now().plusDays(5),
                true
        );

        // Act
        Todo result = todoDao.update(updated);

        // Assert
        assertNotNull(result);
        assertEquals(original.getId(), result.getId());
        assertEquals("Updated", result.getTitle());
        assertEquals("New Desc", result.getTaskDescription());
        assertTrue(result.isDone());

        // Verify the change persisted
        Todo fromDb = todoDao.findById(original.getId());
        assertEquals("Updated", fromDb.getTitle());
    }

    @Test
    void update_shouldThrowForInvalidId() {
        // Arrange
        Todo invalid = new Todo(0, "Invalid", "ID", LocalDate.now(), false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> todoDao.update(invalid));
    }

    @Test
    void deleteById_shouldRemoveTodo() {
        // Arrange
        Todo toDelete = todoDao.create(new Todo("Delete Me", "Desc", LocalDate.now(), false));
        int id = toDelete.getId();

        // Act
        boolean result = todoDao.deleteById(id);

        // Assert
        assertTrue(result);
        assertNull(todoDao.findById(id));
    }

    @Test
    void deleteById_shouldReturnFalseForNonExistingId() {
        // Act
        boolean result = todoDao.deleteById(9999);

        // Assert
        assertFalse(result);
    }

    @Test
    void doesAssigneeExist_shouldReturnCorrectStatus() {
        // Arrange
        Person existing = peopleDao.create(new Person("Existing", "Person"));

        // Act & Assert
        assertTrue(todoDao.doesAssigneeExist(existing.getId()));
        assertFalse(todoDao.doesAssigneeExist(9999));
    }

    @Test
    void update_shouldChangeAssignee() {
        // Create initial assignee and todo
        Person originalAssignee = peopleDao.create(new Person("Original", "Assignee"));
        Todo todo = todoDao.create(new Todo("Task", "Desc", LocalDate.now(), false, originalAssignee));

        // Create new assignee
        Person newAssignee = peopleDao.create(new Person("New", "Assignee"));

        // Update the todo with new assignee
        todo.setAssignee(newAssignee);
        Todo updated = todoDao.update(todo);

        // Verify the change
        assertNotNull(updated);
        Todo fromDb = todoDao.findById(todo.getId());
        assertNotNull(fromDb.getAssignee());
        assertEquals(newAssignee.getId(), fromDb.getAssignee().getId());
    }

    @Test
    void update_shouldHandleNullAssignee() {
        // Create todo with assignee
        Person assignee = peopleDao.create(new Person("Test", "Person"));
        Todo todo = todoDao.create(new Todo("Task", "Desc", LocalDate.now(), false, assignee));

        // Remove assignee
        todo.setAssignee(null);
        Todo updated = todoDao.update(todo);

        // Verify
        assertNotNull(updated);
        Todo fromDb = todoDao.findById(todo.getId());
        assertNull(fromDb.getAssignee());
    }
}
