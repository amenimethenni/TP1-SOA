package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.LoyaltyCardService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.LoyaltyCardDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.LoyaltyCard}.
 */
@RestController
@RequestMapping("/api")
public class LoyaltyCardResource {

    private final Logger log = LoggerFactory.getLogger(LoyaltyCardResource.class);

    private static final String ENTITY_NAME = "loyaltyCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoyaltyCardService loyaltyCardService;

    public LoyaltyCardResource(LoyaltyCardService loyaltyCardService) {
        this.loyaltyCardService = loyaltyCardService;
    }

    /**
     * {@code POST  /loyalty-cards} : Create a new loyaltyCard.
     *
     * @param loyaltyCardDTO the loyaltyCardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loyaltyCardDTO, or with status {@code 400 (Bad Request)} if the loyaltyCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loyalty-cards")
    public ResponseEntity<LoyaltyCardDTO> createLoyaltyCard(@Valid @RequestBody LoyaltyCardDTO loyaltyCardDTO) throws URISyntaxException {
        log.debug("REST request to save LoyaltyCard : {}", loyaltyCardDTO);
        if (loyaltyCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new loyaltyCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(loyaltyCardDTO.getClientId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        LoyaltyCardDTO result = loyaltyCardService.save(loyaltyCardDTO);
        return ResponseEntity.created(new URI("/api/loyalty-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loyalty-cards} : Updates an existing loyaltyCard.
     *
     * @param loyaltyCardDTO the loyaltyCardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loyaltyCardDTO,
     * or with status {@code 400 (Bad Request)} if the loyaltyCardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loyaltyCardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loyalty-cards")
    public ResponseEntity<LoyaltyCardDTO> updateLoyaltyCard(@Valid @RequestBody LoyaltyCardDTO loyaltyCardDTO) throws URISyntaxException {
        log.debug("REST request to update LoyaltyCard : {}", loyaltyCardDTO);
        if (loyaltyCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LoyaltyCardDTO result = loyaltyCardService.save(loyaltyCardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loyaltyCardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /loyalty-cards} : get all the loyaltyCards.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loyaltyCards in body.
     */
    @GetMapping("/loyalty-cards")
    public List<LoyaltyCardDTO> getAllLoyaltyCards() {
        log.debug("REST request to get all LoyaltyCards");
        return loyaltyCardService.findAll();
    }

    /**
     * {@code GET  /loyalty-cards/:id} : get the "id" loyaltyCard.
     *
     * @param id the id of the loyaltyCardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loyaltyCardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loyalty-cards/{id}")
    public ResponseEntity<LoyaltyCardDTO> getLoyaltyCard(@PathVariable Long id) {
        log.debug("REST request to get LoyaltyCard : {}", id);
        Optional<LoyaltyCardDTO> loyaltyCardDTO = loyaltyCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loyaltyCardDTO);
    }

    /**
     * {@code DELETE  /loyalty-cards/:id} : delete the "id" loyaltyCard.
     *
     * @param id the id of the loyaltyCardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loyalty-cards/{id}")
    public ResponseEntity<Void> deleteLoyaltyCard(@PathVariable Long id) {
        log.debug("REST request to delete LoyaltyCard : {}", id);
        loyaltyCardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
