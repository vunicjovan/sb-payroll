package com.example.payroll.model;

import com.example.payroll.enums.Status;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {

    private @Id @GeneratedValue Long id;

    private String description;
    private Status status;

    public Order() {}

    public Order(String description, Status status) {
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Order setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Order setDescription(String description) {
        this.description = description;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Order setStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.description, this.status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Order))
            return false;

        Order order = (Order) obj;

        return Objects.equals(this.id, order.id) &&
                Objects.equals(this.description, order.description) &&
                this.status == order.status;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + this.id + ", description=" + this.description +
                ", status=" + this.status + "}";
    }
}
