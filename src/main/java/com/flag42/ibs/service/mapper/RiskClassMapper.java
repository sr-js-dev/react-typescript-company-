package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.RiskClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RiskClass} and its DTO {@link RiskClassDTO}.
 */
@Mapper(componentModel = "spring", uses = {RiskCategoryMapper.class})
public interface RiskClassMapper extends EntityMapper<RiskClassDTO, RiskClass> {

    @Mapping(source = "riskCategory.id", target = "riskCategoryId")
    RiskClassDTO toDto(RiskClass riskClass);

    @Mapping(source = "riskCategoryId", target = "riskCategory")
    RiskClass toEntity(RiskClassDTO riskClassDTO);

    default RiskClass fromId(Long id) {
        if (id == null) {
            return null;
        }
        RiskClass riskClass = new RiskClass();
        riskClass.setId(id);
        return riskClass;
    }
}
