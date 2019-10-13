package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.PolicyBenefitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PolicyBenefit} and its DTO {@link PolicyBenefitDTO}.
 */
@Mapper(componentModel = "spring", uses = {BenefitMapper.class, PolicyMapper.class})
public interface PolicyBenefitMapper extends EntityMapper<PolicyBenefitDTO, PolicyBenefit> {

    @Mapping(source = "benefit.id", target = "benefitId")
    @Mapping(source = "policy.id", target = "policyId")
    PolicyBenefitDTO toDto(PolicyBenefit policyBenefit);

    @Mapping(source = "benefitId", target = "benefit")
    @Mapping(source = "policyId", target = "policy")
    PolicyBenefit toEntity(PolicyBenefitDTO policyBenefitDTO);

    default PolicyBenefit fromId(Long id) {
        if (id == null) {
            return null;
        }
        PolicyBenefit policyBenefit = new PolicyBenefit();
        policyBenefit.setId(id);
        return policyBenefit;
    }
}
