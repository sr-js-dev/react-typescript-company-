package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.ClientPolicyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClientPolicy} and its DTO {@link ClientPolicyDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class, PolicyMapper.class})
public interface ClientPolicyMapper extends EntityMapper<ClientPolicyDTO, ClientPolicy> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "policy.id", target = "policyId")
    ClientPolicyDTO toDto(ClientPolicy clientPolicy);

    @Mapping(source = "clientId", target = "client")
    @Mapping(source = "policyId", target = "policy")
    ClientPolicy toEntity(ClientPolicyDTO clientPolicyDTO);

    default ClientPolicy fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientPolicy clientPolicy = new ClientPolicy();
        clientPolicy.setId(id);
        return clientPolicy;
    }
}
