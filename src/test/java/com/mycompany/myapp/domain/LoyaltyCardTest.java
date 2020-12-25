package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LoyaltyCardTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoyaltyCard.class);
        LoyaltyCard loyaltyCard1 = new LoyaltyCard();
        loyaltyCard1.setId(1L);
        LoyaltyCard loyaltyCard2 = new LoyaltyCard();
        loyaltyCard2.setId(loyaltyCard1.getId());
        assertThat(loyaltyCard1).isEqualTo(loyaltyCard2);
        loyaltyCard2.setId(2L);
        assertThat(loyaltyCard1).isNotEqualTo(loyaltyCard2);
        loyaltyCard1.setId(null);
        assertThat(loyaltyCard1).isNotEqualTo(loyaltyCard2);
    }
}
