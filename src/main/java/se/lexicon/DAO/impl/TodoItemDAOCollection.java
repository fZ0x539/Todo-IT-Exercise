package se.lexicon.DAO.impl;

import se.lexicon.DAO.Sequencer.TodoItemIdSequencer;
import se.lexicon.DAO.iTodoItemDAO;
import se.lexicon.model.TodoItem;
import se.lexicon.view.ConsoleUI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TodoItemDAOCollection implements iTodoItemDAO {

    private List<TodoItem> storage = new ArrayList<>();

    @Override
    public TodoItem persist(TodoItem todoItem) {
        Objects.requireNonNull(todoItem);
        if (!storage.contains(todoItem)) {
            todoItem.setId(TodoItemIdSequencer.nextId());
            storage.add(todoItem);
            return todoItem;
        } else {
            ConsoleUI.printErrorMessage("This TodoItem has already been added");
        }
        return null;
    }

    @Override
    public TodoItem findById(int id) {
        for (TodoItem item : storage) {
            if (item.getId() == id) {
                return item;
            }
        }
        ConsoleUI.printErrorMessage("TodoItem with this id not found.");
        return null;
    }

    @Override
    public List<TodoItem> findAll() {
        return storage;
    }

    @Override
    public List<TodoItem> findAllByDoneStatus(boolean done) {
        List<TodoItem> returnList = new ArrayList<>();
        for (TodoItem item : storage) {
            if (item.isDone())
                returnList.add(item);
        }
        return returnList;
    }

    @Override
    public List<TodoItem> findByTitleContains(String title) {
        List<TodoItem> returnList = new ArrayList<>();
        for (TodoItem item : storage) {
            if (item.getTitle().equals(title)) {
                returnList.add(item);
            }
        }
        return returnList;
    }

    @Override
    public List<TodoItem> findByPersonId(int personId) {
        List<TodoItem> returnList = new ArrayList<>();
        for (TodoItem item : storage) {
            if (item.getCreator().getId() == personId)
                returnList.add(item);
        }
        return returnList;
    }

    @Override
    public List<TodoItem> findByDeadlineBefore(LocalDate date) {
        List<TodoItem> returnList = new ArrayList<>();
        for (TodoItem item : storage) {
            if (item.getDeadLine().isBefore(date))
                returnList.add(item);
        }
        return returnList;
    }

    @Override
    public List<TodoItem> findByDeadlineAfter(LocalDate date) {
        List<TodoItem> returnList = new ArrayList<>();
        for (TodoItem item : storage) {
            if (item.getDeadLine().isAfter(date))
                returnList.add(item);
        }
        return returnList;
    }

    @Override
    public void remove(int id) {
        if (storage.removeIf(todoItem -> todoItem.getId() == id))
            ConsoleUI.printSuccessMessage("TodoItem with ID: " + id + " has successfully been removed");
        else
            ConsoleUI.printErrorMessage("TodoItem with id " + id + " was not found");

    }
}
