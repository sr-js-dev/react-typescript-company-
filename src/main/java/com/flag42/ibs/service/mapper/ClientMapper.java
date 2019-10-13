package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.ClientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientCategoryMapper.class, NameTitleMapper.class, IdTypeMapper.class})
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "title.id", target = "titleId")
    @Mapping(source = "idType.id", target = "idTypeId")
    ClientDTO toDto(Client client);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "titleId", target = "title")
    @Mapping(source = "idTypeId", target = "idType")
    Client toEntity(ClientDTO clientDTO);

    default Client fromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
