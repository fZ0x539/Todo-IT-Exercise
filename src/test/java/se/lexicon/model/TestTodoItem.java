package se.lexicon.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TestTodoItem {
    private Person person;
    private TodoItem todoItem;

    @BeforeEach
    void setUp() {
        person = new Person(1,"John", "Doe", "john.doe@example.com", "emsweden", "password", AppRole.ROLE_APP_ADMIN);
        todoItem = new TodoItem(1,"Test Task", "This is a test task", LocalDate.now().plusDays(1), person);
    }


    @Test
    void testTodoItemConstructor() {
        assertEquals("Test Task", todoItem.getTitle());
        assertEquals("This is a test task", todoItem.getTaskDescription());
        assertEquals(person, todoItem.getCreator());
        assertFalse(todoItem.isDone());
    }

    @Test
    void testSetTitle() {
        todoItem.setTitle("New Task Title");
        assertEquals("New Task Title", todoItem.getTitle());
    }

    @Test
    void testSetTaskDescription() {
        todoItem.setTaskDescription("Updated Description");
        assertEquals("Updated Description", todoItem.getTaskDescription());
    }

    @Test
    void testSetDeadline() {
        LocalDate newDeadline = LocalDate.now().plusDays(5);
        todoItem.setDeadLine(newDeadline);
        assertEquals(newDeadline, todoItem.getDeadLine());
    }

    @Test
    void testSetDone() {
        todoItem.setDone(true);
        assertTrue(todoItem.isDone());
    }

    @Test
    void testIsOverdue() {
        TodoItem overdueItem = new TodoItem("Overdue Task", "Should be overdue", LocalDate.now().minusDays(1), person);
        assertTrue(overdueItem.isOverdue());
    }


}
