package se.lexicon.DAO.Impl;

import se.lexicon.DAO.iPeople;
import se.lexicon.Model.Person;
import se.lexicon.View.ConsoleUI;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeopleDaoImpl implements iPeople {
    private final Connection connection;

    public PeopleDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Person create(Person person) {
        String sql = "INSERT INTO person (first_name, last_name) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                ConsoleUI.printInfo(person.getFirstName() + " " + person.getLastName() + " has been added to the DB.");
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        int firstId = keys.getInt(1);
                        person.setId(firstId);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error in PeopleDao.create(): " + e.getMessage());
        }

        return person;
    }

    @Override
    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();
        String sql = "SELECT * from person";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery();) {
            while (rs.next()) {
                people.add(new Person(
                        rs.getInt("person_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name")));
            }
        } catch (SQLException e) {
            System.err.println("Error in PeopleDao.findAll(): " + e.getMessage());
        }
        return people;
    }

    @Override
    public Person findById(int id) {
        String sql = "SELECT * from person WHERE person_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery();) {
                if (rs.next()) {
                    return new Person(
                            rs.getInt("person_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in PeopleDao.findById(): " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Person> findByName(String name) {
        List<Person> resultList = new ArrayList<>();
        String sql = "SELECT * FROM person WHERE first_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, name);
            try (ResultSet rs = preparedStatement.executeQuery();) {
                while (rs.next()) {
                    resultList.add(new Person(
                            rs.getInt("person_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in PeopleDao.findByName(): " + e.getMessage());
        }
        return resultList;
    }

    @Override
    public Person update(Person person) {
        if (person.getId() <= 0) {
            throw new IllegalArgumentException("Person ID must be set for update.");
        }

        String updateStatement = "UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)) {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setInt(3, person.getId());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                ConsoleUI.printSuccess("Successfully updated Person with ID " + person.getId());
                return person;
            } else {
                ConsoleUI.printError("Couldn't find person with ID" + person.getId());
            }
        } catch (SQLException e) {
            System.err.println("Error in PeopleDao.update() " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM person WHERE person_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                ConsoleUI.printWarn("Successfully deleted person with ID " + id);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error in PeopleDao.deleteById() " + e.getMessage());
        }
        System.out.println("Couldn't find person with ID " + id);
        return false;
    }


    //Only to be used in development in order to ensure all prior records are wiped and AUTO_INCREMENT is reset if autoCommit is set to false
    public void resetTableIfACFalse() {
        try {
            // Check if autoCommit is disabled
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
                // Delete all records from the person table
                try (Statement deleteStatement = connection.createStatement()) {
                    deleteStatement.executeUpdate("DELETE FROM person");
                }
                // Reset the Auto_Increment
                try (Statement alterStatement = connection.createStatement()) {
                    alterStatement.executeUpdate("ALTER TABLE person AUTO_INCREMENT = 1");
                }
                //Restore to false
                connection.setAutoCommit(false);
            }
            // If autoCommit is already true, do nothing
        } catch (SQLException e) {
            System.err.println("Failed to reset 'person' table: " + e.getMessage());
        }
    }
}
