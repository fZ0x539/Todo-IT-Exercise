package se.lexicon.Model;


import java.util.Objects;

public class Person {
    //Fields
    private int id;
    private String firstName;
    private String lastName;

    //Constructor(s)
    public Person(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);

    }

    public Person(int id, String firstName, String lastName){
        this(firstName, lastName);
        setId(id);
    }


    //Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }


    //Methods
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return getId() == person.getId() && getFirstName().equals(person.getFirstName()) && getLastName().equals(person.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName());
    }


}
