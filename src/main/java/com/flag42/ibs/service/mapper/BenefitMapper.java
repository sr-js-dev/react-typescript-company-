package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.BenefitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Benefit} and its DTO {@link BenefitDTO}.
 */
@Mapper(componentModel = "spring", uses = {BenefitTypeMapper.class})
public interface BenefitMapper extends EntityMapper<BenefitDTO, Benefit> {

    @Mapping(source = "benefitType.id", target = "benefitTypeId")
    BenefitDTO toDto(Benefit benefit);

    @Mapping(source = "benefitTypeId", target = "benefitType")
    Benefit toEntity(BenefitDTO benefitDTO);

    default Benefit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Benefit benefit = new Benefit();
        benefit.setId(id);
        return benefit;
    }
}
