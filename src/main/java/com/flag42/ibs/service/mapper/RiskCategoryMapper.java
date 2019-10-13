package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.RiskCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RiskCategory} and its DTO {@link RiskCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductTypeMapper.class})
public interface RiskCategoryMapper extends EntityMapper<RiskCategoryDTO, RiskCategory> {

    @Mapping(source = "productType.id", target = "productTypeId")
    RiskCategoryDTO toDto(RiskCategory riskCategory);

    @Mapping(source = "productTypeId", target = "productType")
    RiskCategory toEntity(RiskCategoryDTO riskCategoryDTO);

    default RiskCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        RiskCategory riskCategory = new RiskCategory();
        riskCategory.setId(id);
        return riskCategory;
    }
}
