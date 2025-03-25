package se.lexicon.DAO.Impl;

import se.lexicon.DAO.iTodoItems;
import se.lexicon.Model.Person;
import se.lexicon.Model.Todo;
import se.lexicon.View.ConsoleUI;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDaoImpl implements iTodoItems {
    private final Connection connection;

    public TodoDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Todo create(Todo todo) {
        //Validate whether the assignee has been added to the DB since assignee_id is a foreign key.
        if (todo.getAssignee() != null && !doesAssigneeExist(todo.getAssignee().getId()))
            throw new IllegalArgumentException("Assignee does not exist in the DB.");

        String sql = "INSERT INTO todo_item (title, description, deadline, done,  assignee_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, todo.getTitle());
            preparedStatement.setString(2, todo.getTaskDescription());
            preparedStatement.setDate(3, java.sql.Date.valueOf(todo.getDeadLine()));
            preparedStatement.setBoolean(4, todo.isDone());
            // Set assignee_id (or NULL if no assignee)
            if (todo.getAssignee() == null) {
                preparedStatement.setNull(5, java.sql.Types.INTEGER); // Set assignee_id to NULL
            } else {
                preparedStatement.setInt(5, todo.getAssignee().getId());
            }
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                ConsoleUI.printInfo("'" + todo.getTitle() + "'" + " has been added to the DB.");
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        todo.setId(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating Person: " + e.getMessage());
        }
        return todo;
    }


    @Override
    public List<Todo> findAll() {
        List<Todo> resultList = new ArrayList<>();
        String sql = "SELECT ti.*, p.person_id, p.first_name, p.last_name " +
                "FROM todo_item ti " +
                "LEFT JOIN person p ON ti.assignee_id = p.person_id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Person assignee = null;
                // Only create Person object if assignee_id exists
                if (rs.getObject("assignee_id") != null) {
                    assignee = new Person(
                            rs.getInt("person_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name")
                    );
                }

                resultList.add(new Todo(
                        rs.getInt("todo_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("deadline").toLocalDate(),
                        rs.getBoolean("done"),
                        assignee
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error in TodoDao.findAll() " + e.getMessage());
        }
        return resultList;
    }

    @Override
    public Todo findById(int id) {
        String sql = "SELECT * FROM todo_item ti LEFT JOIN person p ON ti.assignee_id = p.person_id WHERE todo_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Person assignee = null;
                    if (rs.getObject("assignee_id") != null) {
                        assignee = new Person(
                                rs.getInt("person_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name")
                        );
                    }

                    return new Todo(
                            rs.getInt("todo_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDate("deadline").toLocalDate(),
                            rs.getBoolean("done"),
                            assignee
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in TodoDao.findById() " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Todo> findByDoneStatus(boolean done) {
        List<Todo> resultList = new ArrayList<>();
        String sql = "SELECT * FROM todo_item ti LEFT JOIN person p ON ti.assignee_id = p.person_id WHERE done = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, done);
            try (ResultSet rs = preparedStatement.executeQuery();) {
                while (rs.next()) {
                    Person assignee = null;
                    if (rs.getObject("assignee_id") != null) {
                        assignee = new Person(
                                rs.getInt("person_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name")
                        );
                    }

                    resultList.add(new Todo(
                            rs.getInt("todo_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDate("deadline").toLocalDate(),
                            rs.getBoolean("done"),
                            assignee
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in TodoDao.findByDoneStatus() " + e.getMessage());
        }
        return resultList;
    }

    @Override
    public List<Todo> findByAssignee(int id) {
        List<Todo> resultList = new ArrayList<>();
        String sql = "SELECT * FROM todo_item ti JOIN person p ON ti.assignee_id = p.person_id WHERE assignee_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery();) {
                while (rs.next()) {
                    resultList.add(new Todo(
                            rs.getInt("todo_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDate("deadline").toLocalDate(),
                            rs.getBoolean("done"),
                            new Person(
                                    rs.getInt("person_id"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"))
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in TodoDao.findByAssignee() " + e.getMessage());
        }
        return resultList;
    }

    @Override
    public List<Todo> findByAssignee(Person assignee) {
        List<Todo> resultList = new ArrayList<>();
        String sql = "SELECT * FROM todo_item ti JOIN person p ON ti.assignee_id = p.person_id WHERE assignee_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, assignee.getId());
            try (ResultSet rs = preparedStatement.executeQuery();) {
                while (rs.next()) {
                    resultList.add(new Todo(
                            rs.getInt("todo_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDate("deadline").toLocalDate(),
                            rs.getBoolean("done"),
                            new Person(
                                    rs.getInt("person_id"),
                                    rs.getString("first_name"),
                                    rs.getString("last_name"))
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in TodoDao.findByAssignee() " + e.getMessage());
        }
        return resultList;
    }

    @Override
    public List<Todo> findByUnassignedStatus() {
        List<Todo> resultList = new ArrayList<>();
        String sql = "SELECT * FROM todo_item WHERE assignee_id IS NULL";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet rs = preparedStatement.executeQuery();) {
                while (rs.next()) {
                    resultList.add(new Todo(
                            rs.getInt("todo_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDate("deadline").toLocalDate(),
                            rs.getBoolean("done"),
                            null
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in TodoDao.findByDoneStatus() " + e.getMessage());
        }
        return resultList;
    }

    @Override
    public Todo update(Todo todo) {
        //Validate that the todo has an ID
        if (todo.getId() <= 0){
            throw new IllegalArgumentException("Todo must have a valid ID.");
        }

        // Validate assignee exists if one is specified
        if (todo.getAssignee() != null && !doesAssigneeExist(todo.getAssignee().getId())) {
            throw new IllegalArgumentException("New assignee does not exist in the DB.");
        }

        String sql = "UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, todo.getTitle());
            preparedStatement.setString(2, todo.getTaskDescription());
            preparedStatement.setDate(3, java.sql.Date.valueOf(todo.getDeadLine()));
            preparedStatement.setBoolean(4, todo.isDone());

            if(todo.getAssignee() == null){
                preparedStatement.setNull(5, Types.INTEGER);
            } else {
                preparedStatement.setInt(5, todo.getAssignee().getId());
            }

            preparedStatement.setInt(6, todo.getId());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                ConsoleUI.printInfo("Successfully updated todo_item with ID " + todo.getId());
                return todo;
            } else {
                ConsoleUI.printError("Couldn't find todo_item with ID" + todo.getId());
            }
        } catch (SQLException e) {
            System.err.println("Error in TodoDao.update() " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM todo_item WHERE todo_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                ConsoleUI.printWarn("Successfully deleted todo_item with ID " + id);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error in TodoDao.deleteById() " + e.getMessage());
        }
        ConsoleUI.printError("Couldn't find todo_item with ID " + id);
        return false;
    }

    public boolean doesAssigneeExist(int assigneeId) {
        String sql = "SELECT COUNT(*) FROM person WHERE person_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, assigneeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // Returns true if assignee_id exists
                }
            }
        } catch (SQLException e) {
            System.err.println("TodoDao.doesAssigneeExist() " + e.getMessage());
        }
        return false;
    }


    //Only to be used in development in order to ensure all prior records are wiped and AUTO_INCREMENT is reset if autoCommit is set to false
    public void resetTableIfACFalse() {
        try {
            // Check if autoCommit is disabled
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
                // Delete all records from the todo_item table
                try (Statement deleteStatement = connection.createStatement()) {
                    deleteStatement.execute("DELETE FROM todo_item");
                }
                // Reset the Auto_Increment
                try (Statement alterStatement = connection.createStatement()) {
                    alterStatement.execute("ALTER TABLE todo_item AUTO_INCREMENT = 1");
                }
                //Restore to false
                connection.setAutoCommit(false);
            }
            // If autoCommit is already true, do nothing
        } catch (SQLException e) {
            System.out.println("Failed to reset 'todo_item' table: " + e.getMessage());
        }
    }
}
