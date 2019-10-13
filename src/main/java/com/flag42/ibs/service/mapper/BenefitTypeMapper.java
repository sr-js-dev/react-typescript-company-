package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.BenefitTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BenefitType} and its DTO {@link BenefitTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BenefitTypeMapper extends EntityMapper<BenefitTypeDTO, BenefitType> {



    default BenefitType fromId(Long id) {
        if (id == null) {
            return null;
        }
        BenefitType benefitType = new BenefitType();
        benefitType.setId(id);
        return benefitType;
    }
}
