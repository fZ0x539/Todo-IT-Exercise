package se.lexicon;

import se.lexicon.model.AppRole;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;
import se.lexicon.model.TodoItemTask;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        //Person
        /*System.out.println("Hello world!");
        Person person = new Person("Abel", "Svartsson", "svatsson@hej.nu");
        System.out.println(person.getId());
        Person person2 = new Person("Choco", "Tjocksson", "chocobo@katt.nu");
        Person person3 = new Person("Abel", "Svartsson", "svatsson@hej.nu");
        Person person4 = new Person("Harry", "Svansson", "harris@katt.nu");
        System.out.println(person4.getId());
        System.out.println(person4.getSummary());


        //TodoItem

        var item1 = new TodoItem("Clean room",
                        "Clean the damn room",
                        LocalDate.parse("2025-01-28"),
                        person4);
        var item2 = new TodoItem("Do dishes",
                        "do the dishes",
                        LocalDate.parse("2025-03-05"),
                        person2);
        item2.setDone(true);
        *//*System.out.println(item2.getSummary());
        System.out.println(item1.getSummary());*//*

        var item3 = new TodoItem("Clean litterbox", LocalDate.now().plusDays(7));
        var item4 = new TodoItem("Buy snus", LocalDate.now().plusDays(12), person4);
        System.out.println(item3.getSummary() + "\n");
        System.out.println(item4.getSummary());*/


        var person5 = new Person("Emanuel", "Swedenborg", "emswe@gothenburg.se", "emsweden", "password", AppRole.ROLE_APP_ADMIN);
        var todoItem5 = new TodoItem("Finish the exercise", "finish TodoItemTask", LocalDate.now().plusDays(3), person5);
        var todoItem6 = new TodoItem("Finish the exercis", "finish TodoItemTask", LocalDate.now().plusDays(3), person5);
        var itemTask1 = new TodoItemTask(todoItem5, person5);
        System.out.println(itemTask1.toString());
        System.out.println(itemTask1.getAssignee().getCredentials());

//        var person6 = new Person("Carl", "Wadström", "cawa@gothenburg.se");
//        var person7 = new Person("Anders", "Knös", "aknö@gothenburg.se");
//        var todoItem6 = new TodoItem("Unit testing", LocalDate.now().plusDays(3), person6);
//        var itemTask2 = new TodoItemTask(todoItem6);
//        System.out.println(itemTask2.getSummary());
//        var itemTask3 = new TodoItemTask(todoItem6, person7);
//        System.out.println(itemTask3.getSummary());





    }
}