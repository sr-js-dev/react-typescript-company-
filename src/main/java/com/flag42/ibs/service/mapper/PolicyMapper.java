package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.PolicyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Policy} and its DTO {@link PolicyDTO}.
 */
@Mapper(componentModel = "spring", uses = {CoverTypeMapper.class, UnderwriterMapper.class})
public interface PolicyMapper extends EntityMapper<PolicyDTO, Policy> {

    @Mapping(source = "coverType.id", target = "coverTypeId")
    @Mapping(source = "underwriter.id", target = "underwriterId")
    PolicyDTO toDto(Policy policy);

    @Mapping(source = "coverTypeId", target = "coverType")
    @Mapping(source = "underwriterId", target = "underwriter")
    Policy toEntity(PolicyDTO policyDTO);

    default Policy fromId(Long id) {
        if (id == null) {
            return null;
        }
        Policy policy = new Policy();
        policy.setId(id);
        return policy;
    }
}
