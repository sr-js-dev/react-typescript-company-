package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.ProductTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductType} and its DTO {@link ProductTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductTypeMapper extends EntityMapper<ProductTypeDTO, ProductType> {



    default ProductType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductType productType = new ProductType();
        productType.setId(id);
        return productType;
    }
}
