package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LoyaltyCardMapperTest {

    private LoyaltyCardMapper loyaltyCardMapper;

    @BeforeEach
    public void setUp() {
        loyaltyCardMapper = new LoyaltyCardMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(loyaltyCardMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(loyaltyCardMapper.fromId(null)).isNull();
    }
}
