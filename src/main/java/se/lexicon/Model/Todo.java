package se.lexicon.Model;


import java.time.LocalDate;
import java.util.Objects;

public class Todo {
    //Fields
    private int id;
    private String title;
    private String taskDescription;
    private LocalDate deadLine;
    private boolean done = false;
    private Person assignee;

    //Constructor(s)
    public Todo(String title, String taskDescription, LocalDate deadLine, boolean done, Person assignee) {
        setTitle(title);
        setTaskDescription(taskDescription);
        setDeadLine(deadLine);
        setDone(done);
        setAssignee(assignee);
    }

    public Todo( String title, String taskDescription, LocalDate deadLine, boolean done) {
        setId(id);
        setTitle(title);
        setTaskDescription(taskDescription);
        setDeadLine(deadLine);
        setDone(done);
    }
    public Todo(int id, String title, String taskDescription, LocalDate deadLine, boolean done) {
        this(title, taskDescription, deadLine, done);
        setId(id);
    }

    public Todo(String title, String taskDescription, LocalDate deadLine, Person assignee) {
        setTitle(title);
        setTaskDescription(taskDescription);
        setDeadLine(deadLine);
        setAssignee(assignee);
    }

    public Todo(int id, String title, String taskDescription, LocalDate deadLine, boolean done, Person assignee) {
        this(title, taskDescription, deadLine, done, assignee);
        setId(id);
    }

    public Todo(int id, String title, String taskDescription, LocalDate deadLine, Person assignee) {
        this(title, taskDescription, deadLine, assignee);
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
//        if(title == null || title.trim().isEmpty()){
//            throw new IllegalArgumentException("Description cannot be null or empty");
//        }
        if (title.length() > 50){
            throw new StringIndexOutOfBoundsException("Title cannot exceed 50 characters");
        }
        this.title = title;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String description) {
//        if(description == null || description.trim().isEmpty()){
//            throw new IllegalArgumentException("Description cannot be null or empty");
//        }
        if (description.length() > 1000){
            throw new StringIndexOutOfBoundsException("Description cannot exceed 1000 characters");
        }
        this.taskDescription = description;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
//        if (deadLine == null) {
//            throw new IllegalArgumentException("Deadline cannot be null");
//        }
        this.deadLine = deadLine;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Person getAssignee() {
        return assignee;
    }

    public void setAssignee(Person person) {
//        Objects.requireNonNull(person, "Person cannot be null.");
        this.assignee = person;
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
                ", assignee=" + assignee +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Todo todoItem)) return false;
        return getId() == todoItem.getId() && isDone() == todoItem.isDone() && getTitle().equals(todoItem.getTitle()) && getTaskDescription().equals(todoItem.getTaskDescription()) && getDeadLine().equals(todoItem.getDeadLine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}


