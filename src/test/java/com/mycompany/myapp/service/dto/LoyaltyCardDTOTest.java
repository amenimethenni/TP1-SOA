package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LoyaltyCardDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoyaltyCardDTO.class);
        LoyaltyCardDTO loyaltyCardDTO1 = new LoyaltyCardDTO();
        loyaltyCardDTO1.setId(1L);
        LoyaltyCardDTO loyaltyCardDTO2 = new LoyaltyCardDTO();
        assertThat(loyaltyCardDTO1).isNotEqualTo(loyaltyCardDTO2);
        loyaltyCardDTO2.setId(loyaltyCardDTO1.getId());
        assertThat(loyaltyCardDTO1).isEqualTo(loyaltyCardDTO2);
        loyaltyCardDTO2.setId(2L);
        assertThat(loyaltyCardDTO1).isNotEqualTo(loyaltyCardDTO2);
        loyaltyCardDTO1.setId(null);
        assertThat(loyaltyCardDTO1).isNotEqualTo(loyaltyCardDTO2);
    }
}
