package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.ClientPolicyPaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClientPolicyPayment} and its DTO {@link ClientPolicyPaymentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientPolicyMapper.class})
public interface ClientPolicyPaymentMapper extends EntityMapper<ClientPolicyPaymentDTO, ClientPolicyPayment> {

    @Mapping(source = "clientPolicy.id", target = "clientPolicyId")
    ClientPolicyPaymentDTO toDto(ClientPolicyPayment clientPolicyPayment);

    @Mapping(source = "clientPolicyId", target = "clientPolicy")
    ClientPolicyPayment toEntity(ClientPolicyPaymentDTO clientPolicyPaymentDTO);

    default ClientPolicyPayment fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientPolicyPayment clientPolicyPayment = new ClientPolicyPayment();
        clientPolicyPayment.setId(id);
        return clientPolicyPayment;
    }
}
