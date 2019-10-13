package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.IdTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link IdType} and its DTO {@link IdTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IdTypeMapper extends EntityMapper<IdTypeDTO, IdType> {



    default IdType fromId(Long id) {
        if (id == null) {
            return null;
        }
        IdType idType = new IdType();
        idType.setId(id);
        return idType;
    }
}
