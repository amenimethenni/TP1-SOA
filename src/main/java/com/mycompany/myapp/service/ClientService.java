package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ClientDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Client}.
 */
public interface ClientService {

    /**
     * Save a client.
     *
     * @param clientDTO the entity to save.
     * @return the persisted entity.
     */
    ClientDTO save(ClientDTO clientDTO);

    /**
     * Get all the clients.
     *
     * @return the list of entities.
     */
    List<ClientDTO> findAll();
    /**
     * Get all the ClientDTO where LoyaltyCard is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ClientDTO> findAllWhereLoyaltyCardIsNull();


    /**
     * Get the "id" client.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientDTO> findOne(Long id);

    /**
     * Delete the "id" client.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
