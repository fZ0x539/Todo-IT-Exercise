package se.lexicon.DAO;


import se.lexicon.model.TodoItemTask;

import java.util.List;

public interface iTodoItemTaskDAO {
    TodoItemTask persist(TodoItemTask todoItemTask);
    TodoItemTask findById(int id);
    List<TodoItemTask> findAll();
    List<TodoItemTask> findByAssignedStatus(boolean assigned);
    List<TodoItemTask> findByPersonId(int personId);
    void remove(int id);
}
