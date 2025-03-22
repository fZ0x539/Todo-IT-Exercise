package se.lexicon.model;

import se.lexicon.DAO.Sequencer.TodoItemIdSequencer;

import java.time.LocalDate;
import java.util.Objects;

public class TodoItem {
    //Fields
    private int id;
    private String title;
    private String taskDescription;
    private LocalDate deadLine;
    private boolean done = false;
    private Person creator;

    //Constructors
    public TodoItem(String title, String taskDescription, LocalDate deadLine, Person creator) {
        setTitle(title);
        setTaskDescription(taskDescription);
        setDeadLine(deadLine);
        setCreator(creator);
//        this.id = TodoItemIdSequencer.nextId();
    }

    public TodoItem(int id, String title, String taskDescription, LocalDate deadLine, Person creator) {
        this(title, taskDescription, deadLine, creator);
        setId(id);
    }


    //Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().equals("")) {
            throw new IllegalArgumentException("Please enter a valid title name");
        }
        this.title = title;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String description) {
        this.taskDescription = description;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        if (deadLine == null) {
            throw new IllegalArgumentException("Deadline cannot be null");
        }
        this.deadLine = deadLine;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person person) {
        Objects.requireNonNull(person, "Person cannot be null.");
        this.creator = person;
    }

    //Methods
    public boolean isOverdue() {
        var currentDate = LocalDate.now();
        return (currentDate.isAfter(deadLine));
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", deadLine=" + deadLine +
                ", done=" + done +
                ", creator=" + creator +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TodoItem todoItem)) return false;
        return getId() == todoItem.getId() && isDone() == todoItem.isDone() && getTitle().equals(todoItem.getTitle()) && getTaskDescription().equals(todoItem.getTaskDescription()) && getDeadLine().equals(todoItem.getDeadLine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}


