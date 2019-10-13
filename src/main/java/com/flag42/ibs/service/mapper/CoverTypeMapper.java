package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.CoverTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CoverType} and its DTO {@link CoverTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {RiskClassMapper.class})
public interface CoverTypeMapper extends EntityMapper<CoverTypeDTO, CoverType> {

    @Mapping(source = "riskClass.id", target = "riskClassId")
    CoverTypeDTO toDto(CoverType coverType);

    @Mapping(source = "riskClassId", target = "riskClass")
    CoverType toEntity(CoverTypeDTO coverTypeDTO);

    default CoverType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CoverType coverType = new CoverType();
        coverType.setId(id);
        return coverType;
    }
}
