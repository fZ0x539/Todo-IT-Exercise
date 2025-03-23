package se.lexicon;

import se.lexicon.DAO.Impl.PeopleDaoImpl;
import se.lexicon.DAO.Impl.TodoDaoImpl;
import se.lexicon.DB.DBConnection;
import se.lexicon.Model.Person;
import se.lexicon.Model.Todo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()) {
            try {
                PeopleDaoImpl pplDao = new PeopleDaoImpl(connection);
                TodoDaoImpl todoDao = new TodoDaoImpl(connection);
                connection.setAutoCommit(false);
                pplDao.resetTableIfACFalse();
                todoDao.resetTableIfACFalse();

                Person person1 = new Person("Daniel", "Karlsson");
                pplDao.create(person1);
                Person person2 = new Person("Mikael", "Johnsson");
                pplDao.create(person2);

                //PeopleDAO
//                Person person3 = new Person("Daniel", "Mikaelsson");
//                pplDao.create(person3);


//                //PeopleDao.findAll
//                List<Person> allPeople = pplDao.findAll();
//                allPeople.forEach(System.out::println);


//                //PeopleDao.findById
//                System.out.println("findById: " + pplDao.findById(person1.getId()));


//                //PeopleDao.findBYName
//                pplDao.findByName("Daniel").forEach(System.out::println);


//                //PeopleDao.update
//                person1.setLastName("Carlsson");
//                pplDao.update(person1);
//                System.out.println(person1);


//                //PeopleDao.deleteById
//                pplDao.deleteById(person1.getId());
//                pplDao.findAll().forEach(System.out::println);


                //TodoDAO
                Todo todo1 = new Todo(
                        "Clean room",
                        "Vacuum the cat hair, organize the book shelf",
                        LocalDate.now().plusDays(1),
                        true,
                        person1);

                Todo todo2 = new Todo(
                        "Do the dishes",
                        "Self explanatory",
                        LocalDate.now().plusDays(1),
                        false,
                        person2);

                Todo todo3 = new Todo(
                        "Cook food",
                        "Cook the ground-beef",
                        LocalDate.now().plusDays(3),
                        true,
                        person2);

                Todo unassignedTodo1 = new Todo(
                        "Assign todos",
                        "you know the drill..",
                        LocalDate.now().plusDays(3),
                        true);

                Todo unassignedTodo2 = new Todo(
                        "Fix bugs because of unassigned todos...",
                        "here we go again..",
                        LocalDate.now().plusDays(3),
                        true);
//                //create
                todoDao.create(todo1);
                todoDao.create(todo2);
                todoDao.create(todo3);

//                //findAll
//                todoDao.findAll().forEach(System.out::println);

//                //findById
//                System.out.println(todoDao.findById(1));

//                //findByDoneStatus
//                todoDao.findByDoneStatus(false).forEach(System.out::println);

//                //findByAssignee(int id)
//                todoDao.findByAssignee(person2.getId()).forEach(System.out::println);

//                //findByAssignee(Person person)
//                todoDao.findByAssignee(person1).forEach(System.out::println);


//                //findByUnassignedStatus
//                todoDao.create(unassignedTodo1);
//                todoDao.create(unassignedTodo2);
//                todoDao.findByUnassignedStatus().forEach(System.out::println);


//                //todoDao.update
//                todoDao.findAll().forEach(System.out::println);
//                todo1.setTitle("Clean garage");
//                todo1 = todoDao.update(todo1);
//                System.out.println(todo1);
//                todoDao.findAll().forEach(System.out::println);


                  //todoDao.deleteById
                  todoDao.findAll().forEach(System.out::println);
                  todoDao.deleteById(todo1.getId());
                  todoDao.deleteById(todo3.getId());
                  todoDao.findAll().forEach(System.out::println);








            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Error: " + e.getMessage());
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}