package se.lexicon.DAO;

import se.lexicon.Model.Person;
import se.lexicon.Model.Todo;

import java.util.List;

public interface iTodoItems {
    Todo create(Todo todo);
    List<Todo> findAll();
    Todo findById(int id);
    List<Todo> findByDoneStatus(boolean done);
    List<Todo> findByAssignee(int id);
    List<Todo> findByAssignee(Person assignee);
    List<Todo> findByUnassignedStatus();
    Todo update(Todo todo);
    boolean deleteById(int id);

}
