package com.mycompany.myapp.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Order} entity.
 */
public class OrderDTO implements Serializable {
    
    private Long id;

    @NotNull
    private ZonedDateTime date;

    @NotNull
    @Pattern(regexp = "(^[a-zA-Z]*$)")
    private String status;

    private Double price;


    private Long clientId;

    private String clientCin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientCin() {
        return clientCin;
    }

    public void setClientCin(String clientCin) {
        this.clientCin = clientCin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", price=" + getPrice() +
            ", clientId=" + getClientId() +
            ", clientCin='" + getClientCin() + "'" +
            "}";
    }
}
