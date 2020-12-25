package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "(^[a-zA-Z]*$)")
    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "(^[a-zA-Z]*$)")
    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 8, max = 8)
    @Pattern(regexp = "(^[0-9]*$)")
    @Column(name = "cin", length = 8, nullable = false, unique = true)
    private String cin;

    @NotNull
    @Size(min = 8, max = 8)
    @Pattern(regexp = "(^[0-9]*$)")
    @Column(name = "phone_number", length = 8, nullable = false, unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "client")
    private Set<Order> orders = new HashSet<>();

    @OneToOne(mappedBy = "client")
    @JsonIgnore
    private LoyaltyCard loyaltyCard;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Client firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Client lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCin() {
        return cin;
    }

    public Client cin(String cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Client phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Client orders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Client addOrder(Order order) {
        this.orders.add(order);
        order.setClient(this);
        return this;
    }

    public Client removeOrder(Order order) {
        this.orders.remove(order);
        order.setClient(null);
        return this;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public LoyaltyCard getLoyaltyCard() {
        return loyaltyCard;
    }

    public Client loyaltyCard(LoyaltyCard loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
        return this;
    }

    public void setLoyaltyCard(LoyaltyCard loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", cin='" + getCin() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
