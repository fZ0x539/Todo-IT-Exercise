package se.lexicon.DAO.impl;


import se.lexicon.DAO.Sequencer.TodoItemIdSequencer;
import se.lexicon.DAO.iTodoItemTaskDAO;


import se.lexicon.model.TodoItemTask;
import se.lexicon.view.ConsoleUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TodoItemTaskDAOCollection implements iTodoItemTaskDAO {

    private List<TodoItemTask> storage = new ArrayList<>();


    @Override
    public TodoItemTask persist(TodoItemTask todoItemTask) {
        Objects.requireNonNull(todoItemTask, "Person cant be null");
        if (!storage.contains(todoItemTask)) {
            todoItemTask.setId(TodoItemIdSequencer.nextId());
            storage.add(todoItemTask);
            return todoItemTask;
        } else
            ConsoleUI.printErrorMessage("This TodoItemTask has already been added");
        return null;
    }

    @Override
    public TodoItemTask findById(int id) {
        for (TodoItemTask item : storage) {
            if (item.getId() == id) {
                return item;
            }
        }
        ConsoleUI.printErrorMessage("TodoItemTask with this id not found.");
        return null;
    }

    @Override
    public List<TodoItemTask> findAll() {
        return storage;
    }

    @Override
    public List<TodoItemTask> findByAssignedStatus(boolean assigned) {
        List<TodoItemTask> returnList = new ArrayList<>();
        for (TodoItemTask item : storage) {
            if (item.isAssigned())
                returnList.add(item);
        }
        return returnList;
    }

    @Override
    public List<TodoItemTask> findByPersonId(int personId) {
        List<TodoItemTask> returnList = new ArrayList<>();
        for (TodoItemTask item : storage) {
            if (item.getAssignee().getId() == personId)
                returnList.add(item);
        }
        return returnList;
    }

    @Override
    public void remove(int id) {
        if (storage.removeIf(todoItemTask -> todoItemTask.getId() == id))
            ConsoleUI.printSuccessMessage("TodoItemTask with ID: " + id + " has successfully been removed");
        else
            ConsoleUI.printErrorMessage("TodoItemTask with id " + id + " was not found");
    }
}
