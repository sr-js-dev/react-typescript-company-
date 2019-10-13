package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.ClientCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClientCategory} and its DTO {@link ClientCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientCategoryMapper extends EntityMapper<ClientCategoryDTO, ClientCategory> {



    default ClientCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientCategory clientCategory = new ClientCategory();
        clientCategory.setId(id);
        return clientCategory;
    }
}
