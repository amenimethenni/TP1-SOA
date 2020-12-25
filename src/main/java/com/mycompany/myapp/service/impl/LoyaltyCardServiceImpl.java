package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.LoyaltyCardService;
import com.mycompany.myapp.domain.LoyaltyCard;
import com.mycompany.myapp.repository.LoyaltyCardRepository;
import com.mycompany.myapp.repository.ClientRepository;
import com.mycompany.myapp.service.dto.LoyaltyCardDTO;
import com.mycompany.myapp.service.mapper.LoyaltyCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link LoyaltyCard}.
 */
@Service
@Transactional
public class LoyaltyCardServiceImpl implements LoyaltyCardService {

    private final Logger log = LoggerFactory.getLogger(LoyaltyCardServiceImpl.class);

    private final LoyaltyCardRepository loyaltyCardRepository;

    private final LoyaltyCardMapper loyaltyCardMapper;

    private final ClientRepository clientRepository;

    public LoyaltyCardServiceImpl(LoyaltyCardRepository loyaltyCardRepository, LoyaltyCardMapper loyaltyCardMapper, ClientRepository clientRepository) {
        this.loyaltyCardRepository = loyaltyCardRepository;
        this.loyaltyCardMapper = loyaltyCardMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    public LoyaltyCardDTO save(LoyaltyCardDTO loyaltyCardDTO) {
        log.debug("Request to save LoyaltyCard : {}", loyaltyCardDTO);
        LoyaltyCard loyaltyCard = loyaltyCardMapper.toEntity(loyaltyCardDTO);
        Long clientId = loyaltyCardDTO.getClientId();
        clientRepository.findById(clientId).ifPresent(loyaltyCard::client);
        loyaltyCard = loyaltyCardRepository.save(loyaltyCard);
        return loyaltyCardMapper.toDto(loyaltyCard);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoyaltyCardDTO> findAll() {
        log.debug("Request to get all LoyaltyCards");
        return loyaltyCardRepository.findAll().stream()
            .map(loyaltyCardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LoyaltyCardDTO> findOne(Long id) {
        log.debug("Request to get LoyaltyCard : {}", id);
        return loyaltyCardRepository.findById(id)
            .map(loyaltyCardMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LoyaltyCard : {}", id);
        loyaltyCardRepository.deleteById(id);
    }
}
