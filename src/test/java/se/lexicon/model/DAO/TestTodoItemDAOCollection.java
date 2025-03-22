package se.lexicon.model.DAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.DAO.impl.TodoItemDAOCollection;
import se.lexicon.model.AppRole;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.time.LocalDate;


public class TestTodoItemDAOCollection {
    private TodoItem todoItem1;
    private Person person1;
    private TodoItem todoItem2;
    private TodoItemDAOCollection testObject;

    @BeforeEach
    public void setup(){
        person1 = new Person("Olle", "Eriksson", "olle.eriksson@example.com", "user1", "pwd1", AppRole.ROLE_APP_USER );
        todoItem1 = new TodoItem("Make Bed", "Make the damn bed", LocalDate.now().plusDays(1), person1);
        testObject = new TodoItemDAOCollection();
    }

    @Test
    public void testAddTask(){

    }
}
