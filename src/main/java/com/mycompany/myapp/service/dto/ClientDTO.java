package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Client} entity.
 */
public class ClientDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "(^[a-zA-Z]*$)")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "(^[a-zA-Z]*$)")
    private String lastName;

    @NotNull
    @Size(min = 8, max = 8)
    @Pattern(regexp = "(^[0-9]*$)")
    private String cin;

    @NotNull
    @Size(min = 8, max = 8)
    @Pattern(regexp = "(^[0-9]*$)")
    private String phoneNumber;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDTO)) {
            return false;
        }

        return id != null && id.equals(((ClientDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", cin='" + getCin() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
