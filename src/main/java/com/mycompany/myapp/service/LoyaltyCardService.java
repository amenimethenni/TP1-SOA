package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.LoyaltyCardDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.LoyaltyCard}.
 */
public interface LoyaltyCardService {

    /**
     * Save a loyaltyCard.
     *
     * @param loyaltyCardDTO the entity to save.
     * @return the persisted entity.
     */
    LoyaltyCardDTO save(LoyaltyCardDTO loyaltyCardDTO);

    /**
     * Get all the loyaltyCards.
     *
     * @return the list of entities.
     */
    List<LoyaltyCardDTO> findAll();


    /**
     * Get the "id" loyaltyCard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LoyaltyCardDTO> findOne(Long id);

    /**
     * Delete the "id" loyaltyCard.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
