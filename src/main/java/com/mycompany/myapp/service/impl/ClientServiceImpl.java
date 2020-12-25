package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ClientService;
import com.mycompany.myapp.domain.Client;
import com.mycompany.myapp.repository.ClientRepository;
import com.mycompany.myapp.service.dto.ClientDTO;
import com.mycompany.myapp.service.mapper.ClientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        log.debug("Request to save Client : {}", clientDTO);
        Client client = clientMapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> findAll() {
        log.debug("Request to get all Clients");
        return clientRepository.findAll().stream()
            .map(clientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  Get all the clients where LoyaltyCard is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ClientDTO> findAllWhereLoyaltyCardIsNull() {
        log.debug("Request to get all clients where LoyaltyCard is null");
        return StreamSupport
            .stream(clientRepository.findAll().spliterator(), false)
            .filter(client -> client.getLoyaltyCard() == null)
            .map(clientMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientDTO> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id)
            .map(clientMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
