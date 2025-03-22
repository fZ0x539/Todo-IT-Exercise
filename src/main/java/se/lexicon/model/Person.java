package se.lexicon.model;

import se.lexicon.DAO.Sequencer.PersonIdSequencer;

import java.util.Objects;

public class Person {
    //Fields
    private int id;
    private AppUser credentials;
    private String firstName;
    private String lastName;
    private String email;

    //Constructor(s)
    public Person(String firstName, String lastName, String email, String username, String password, AppRole role) {
        this.credentials = new AppUser(username, password, role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
//        this.id = PersonIdSequencer.nextId();
    }

    public Person(int id, String firstName, String lastName, String email, String username, String password, AppRole role){
        this(firstName, lastName, email, username, password, role);
        setId(id);
    }



    //Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public AppUser getCredentials() {
        return credentials;
    }

    public void setCredentials(AppUser credentials) {
        this.credentials = credentials;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Methods
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", credentials=" + credentials +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return getId() == person.getId() && getFirstName().equals(person.getFirstName()) && getLastName().equals(person.getLastName()) && getEmail().equals(person.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getEmail());
    }


}
