package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.LoyaltyCardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoyaltyCard} and its DTO {@link LoyaltyCardDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface LoyaltyCardMapper extends EntityMapper<LoyaltyCardDTO, LoyaltyCard> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.cin", target = "clientCin")
    LoyaltyCardDTO toDto(LoyaltyCard loyaltyCard);

    @Mapping(source = "clientId", target = "client")
    LoyaltyCard toEntity(LoyaltyCardDTO loyaltyCardDTO);

    default LoyaltyCard fromId(Long id) {
        if (id == null) {
            return null;
        }
        LoyaltyCard loyaltyCard = new LoyaltyCard();
        loyaltyCard.setId(id);
        return loyaltyCard;
    }
}
