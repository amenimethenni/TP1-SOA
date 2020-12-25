package com.mycompany.myapp.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.LoyaltyCard} entity.
 */
public class LoyaltyCardDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer rewardPoints;

    @NotNull
    private ZonedDateTime createdAt;

    @NotNull
    private ZonedDateTime expiredAt;


    private Long clientId;

    private String clientCin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
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
        if (!(o instanceof LoyaltyCardDTO)) {
            return false;
        }

        return id != null && id.equals(((LoyaltyCardDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoyaltyCardDTO{" +
            "id=" + getId() +
            ", rewardPoints=" + getRewardPoints() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", expiredAt='" + getExpiredAt() + "'" +
            ", clientId=" + getClientId() +
            ", clientCin='" + getClientCin() + "'" +
            "}";
    }
}
