package se.lexicon.model;

import java.util.Objects;

public class TodoItemTask {
    //Fields
    private int id;
    private TodoItem todoItem;
    private Person assignee;
    private boolean assigned = false;

    //Constructors

    public TodoItemTask(TodoItem todoItem, Person assignee){
        this(todoItem);
        setAssignee(assignee);
    }

    public TodoItemTask(int id, TodoItem todoItem, Person assignee){
        this(todoItem);
        setAssignee(assignee);
        setId(id);
    }

    public TodoItemTask(TodoItem todoItem){
        setTodoItem(todoItem);
    }

    public TodoItemTask(int id, TodoItem todoItem){
        setTodoItem(todoItem);
        setId(id);
    }

    //Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public boolean isAssigned(){
        return assigned;
    }

    public TodoItem getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(TodoItem todoItem) {
        if(todoItem == null){
            throw new IllegalArgumentException("Invalid TodoItem");
        }
        this.todoItem = todoItem;
    }

    public Person getAssignee() {
        return assignee;
    }

    public void setAssignee(Person assignee) {
        this.assignee = assignee;
        this.assigned = true;
    }

    //Methods

    @Override
    public String toString() {
        return "TodoItemTask" + "\n" +
                "{id=" + id +
                ", todoItem= " + todoItem + "\n" +
                ", assignee= " + assignee +
                ", assigned=" + assigned +
                "}\n";
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTodoItem());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TodoItemTask todoItemTask)) return false;
        return getId() == todoItemTask.getId() && isAssigned() == todoItemTask.isAssigned() && getTodoItem().equals(todoItemTask.getTodoItem());
    }

}
