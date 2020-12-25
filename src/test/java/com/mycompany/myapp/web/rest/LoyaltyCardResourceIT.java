package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TpsoaApp;
import com.mycompany.myapp.domain.LoyaltyCard;
import com.mycompany.myapp.domain.Client;
import com.mycompany.myapp.repository.LoyaltyCardRepository;
import com.mycompany.myapp.service.LoyaltyCardService;
import com.mycompany.myapp.service.dto.LoyaltyCardDTO;
import com.mycompany.myapp.service.mapper.LoyaltyCardMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LoyaltyCardResource} REST controller.
 */
@SpringBootTest(classes = TpsoaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LoyaltyCardResourceIT {

    private static final Integer DEFAULT_REWARD_POINTS = 0;
    private static final Integer UPDATED_REWARD_POINTS = 1;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_EXPIRED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private LoyaltyCardRepository loyaltyCardRepository;

    @Autowired
    private LoyaltyCardMapper loyaltyCardMapper;

    @Autowired
    private LoyaltyCardService loyaltyCardService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoyaltyCardMockMvc;

    private LoyaltyCard loyaltyCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoyaltyCard createEntity(EntityManager em) {
        LoyaltyCard loyaltyCard = new LoyaltyCard()
            .rewardPoints(DEFAULT_REWARD_POINTS)
            .createdAt(DEFAULT_CREATED_AT)
            .expiredAt(DEFAULT_EXPIRED_AT);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        loyaltyCard.setClient(client);
        return loyaltyCard;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoyaltyCard createUpdatedEntity(EntityManager em) {
        LoyaltyCard loyaltyCard = new LoyaltyCard()
            .rewardPoints(UPDATED_REWARD_POINTS)
            .createdAt(UPDATED_CREATED_AT)
            .expiredAt(UPDATED_EXPIRED_AT);
        // Add required entity
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            client = ClientResourceIT.createUpdatedEntity(em);
            em.persist(client);
            em.flush();
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        loyaltyCard.setClient(client);
        return loyaltyCard;
    }

    @BeforeEach
    public void initTest() {
        loyaltyCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoyaltyCard() throws Exception {
        int databaseSizeBeforeCreate = loyaltyCardRepository.findAll().size();
        // Create the LoyaltyCard
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.toDto(loyaltyCard);
        restLoyaltyCardMockMvc.perform(post("/api/loyalty-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loyaltyCardDTO)))
            .andExpect(status().isCreated());

        // Validate the LoyaltyCard in the database
        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeCreate + 1);
        LoyaltyCard testLoyaltyCard = loyaltyCardList.get(loyaltyCardList.size() - 1);
        assertThat(testLoyaltyCard.getRewardPoints()).isEqualTo(DEFAULT_REWARD_POINTS);
        assertThat(testLoyaltyCard.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLoyaltyCard.getExpiredAt()).isEqualTo(DEFAULT_EXPIRED_AT);

        // Validate the id for MapsId, the ids must be same
        assertThat(testLoyaltyCard.getId()).isEqualTo(testLoyaltyCard.getClient().getId());
    }

    @Test
    @Transactional
    public void createLoyaltyCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loyaltyCardRepository.findAll().size();

        // Create the LoyaltyCard with an existing ID
        loyaltyCard.setId(1L);
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.toDto(loyaltyCard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoyaltyCardMockMvc.perform(post("/api/loyalty-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loyaltyCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LoyaltyCard in the database
        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateLoyaltyCardMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        loyaltyCardRepository.saveAndFlush(loyaltyCard);
        int databaseSizeBeforeCreate = loyaltyCardRepository.findAll().size();

        // Add a new parent entity
        Client client = ClientResourceIT.createUpdatedEntity(em);
        em.persist(client);
        em.flush();

        // Load the loyaltyCard
        LoyaltyCard updatedLoyaltyCard = loyaltyCardRepository.findById(loyaltyCard.getId()).get();
        // Disconnect from session so that the updates on updatedLoyaltyCard are not directly saved in db
        em.detach(updatedLoyaltyCard);

        // Update the Client with new association value
        updatedLoyaltyCard.setClient(client);
        LoyaltyCardDTO updatedLoyaltyCardDTO = loyaltyCardMapper.toDto(updatedLoyaltyCard);

        // Update the entity
        restLoyaltyCardMockMvc.perform(put("/api/loyalty-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLoyaltyCardDTO)))
            .andExpect(status().isOk());

        // Validate the LoyaltyCard in the database
        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeCreate);
        LoyaltyCard testLoyaltyCard = loyaltyCardList.get(loyaltyCardList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testLoyaltyCard.getId()).isEqualTo(testLoyaltyCard.getClient().getId());
    }

    @Test
    @Transactional
    public void checkRewardPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = loyaltyCardRepository.findAll().size();
        // set the field null
        loyaltyCard.setRewardPoints(null);

        // Create the LoyaltyCard, which fails.
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.toDto(loyaltyCard);


        restLoyaltyCardMockMvc.perform(post("/api/loyalty-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loyaltyCardDTO)))
            .andExpect(status().isBadRequest());

        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = loyaltyCardRepository.findAll().size();
        // set the field null
        loyaltyCard.setCreatedAt(null);

        // Create the LoyaltyCard, which fails.
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.toDto(loyaltyCard);


        restLoyaltyCardMockMvc.perform(post("/api/loyalty-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loyaltyCardDTO)))
            .andExpect(status().isBadRequest());

        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpiredAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = loyaltyCardRepository.findAll().size();
        // set the field null
        loyaltyCard.setExpiredAt(null);

        // Create the LoyaltyCard, which fails.
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.toDto(loyaltyCard);


        restLoyaltyCardMockMvc.perform(post("/api/loyalty-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loyaltyCardDTO)))
            .andExpect(status().isBadRequest());

        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLoyaltyCards() throws Exception {
        // Initialize the database
        loyaltyCardRepository.saveAndFlush(loyaltyCard);

        // Get all the loyaltyCardList
        restLoyaltyCardMockMvc.perform(get("/api/loyalty-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loyaltyCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].rewardPoints").value(hasItem(DEFAULT_REWARD_POINTS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].expiredAt").value(hasItem(sameInstant(DEFAULT_EXPIRED_AT))));
    }
    
    @Test
    @Transactional
    public void getLoyaltyCard() throws Exception {
        // Initialize the database
        loyaltyCardRepository.saveAndFlush(loyaltyCard);

        // Get the loyaltyCard
        restLoyaltyCardMockMvc.perform(get("/api/loyalty-cards/{id}", loyaltyCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loyaltyCard.getId().intValue()))
            .andExpect(jsonPath("$.rewardPoints").value(DEFAULT_REWARD_POINTS))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.expiredAt").value(sameInstant(DEFAULT_EXPIRED_AT)));
    }
    @Test
    @Transactional
    public void getNonExistingLoyaltyCard() throws Exception {
        // Get the loyaltyCard
        restLoyaltyCardMockMvc.perform(get("/api/loyalty-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoyaltyCard() throws Exception {
        // Initialize the database
        loyaltyCardRepository.saveAndFlush(loyaltyCard);

        int databaseSizeBeforeUpdate = loyaltyCardRepository.findAll().size();

        // Update the loyaltyCard
        LoyaltyCard updatedLoyaltyCard = loyaltyCardRepository.findById(loyaltyCard.getId()).get();
        // Disconnect from session so that the updates on updatedLoyaltyCard are not directly saved in db
        em.detach(updatedLoyaltyCard);
        updatedLoyaltyCard
            .rewardPoints(UPDATED_REWARD_POINTS)
            .createdAt(UPDATED_CREATED_AT)
            .expiredAt(UPDATED_EXPIRED_AT);
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.toDto(updatedLoyaltyCard);

        restLoyaltyCardMockMvc.perform(put("/api/loyalty-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loyaltyCardDTO)))
            .andExpect(status().isOk());

        // Validate the LoyaltyCard in the database
        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeUpdate);
        LoyaltyCard testLoyaltyCard = loyaltyCardList.get(loyaltyCardList.size() - 1);
        assertThat(testLoyaltyCard.getRewardPoints()).isEqualTo(UPDATED_REWARD_POINTS);
        assertThat(testLoyaltyCard.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLoyaltyCard.getExpiredAt()).isEqualTo(UPDATED_EXPIRED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingLoyaltyCard() throws Exception {
        int databaseSizeBeforeUpdate = loyaltyCardRepository.findAll().size();

        // Create the LoyaltyCard
        LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.toDto(loyaltyCard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoyaltyCardMockMvc.perform(put("/api/loyalty-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(loyaltyCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LoyaltyCard in the database
        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLoyaltyCard() throws Exception {
        // Initialize the database
        loyaltyCardRepository.saveAndFlush(loyaltyCard);

        int databaseSizeBeforeDelete = loyaltyCardRepository.findAll().size();

        // Delete the loyaltyCard
        restLoyaltyCardMockMvc.perform(delete("/api/loyalty-cards/{id}", loyaltyCard.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoyaltyCard> loyaltyCardList = loyaltyCardRepository.findAll();
        assertThat(loyaltyCardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
