package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A LoyaltyCard.
 */
@Entity
@Table(name = "loyalty_card")
public class LoyaltyCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "reward_points", nullable = false)
    private Integer rewardPoints;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "expired_at", nullable = false)
    private ZonedDateTime expiredAt;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    }

    public LoyaltyCard rewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
        return this;
    }

    public void setRewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public LoyaltyCard createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getExpiredAt() {
        return expiredAt;
    }

    public LoyaltyCard expiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
        return this;
    }

    public void setExpiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Client getClient() {
        return client;
    }

    public LoyaltyCard client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoyaltyCard)) {
            return false;
        }
        return id != null && id.equals(((LoyaltyCard) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoyaltyCard{" +
            "id=" + getId() +
            ", rewardPoints=" + getRewardPoints() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", expiredAt='" + getExpiredAt() + "'" +
            "}";
    }
}
