package com.example.payroll.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Employee {

    private @Id
    @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private String name;
    private String role;

    Employee() {
    }

    public Employee(String firstName, String lastName, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public Employee setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public Employee setName(String name) {
        String[] parts = name.split(" ");

        this.firstName = parts[0];
        this.lastName = parts[1];

        return this;
    }

    public String getRole() {
        return role;
    }

    public Employee setRole(String role) {
        this.role = role;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.role);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Employee))
            return false;

        Employee employee = (Employee) obj;

        return Objects.equals(this.id, employee.id)
                && Objects.equals(this.firstName, employee.firstName)
                && Objects.equals(this.lastName, employee.lastName)
                && Objects.equals(this.role, employee.role);
    }

    @Override
    public String toString() {
        return "Employee {" + "id=" + this.id + ", firstName=" + this.firstName +
                ", lastName=" + this.lastName + ", role=" + this.role + "}";
    }
}
