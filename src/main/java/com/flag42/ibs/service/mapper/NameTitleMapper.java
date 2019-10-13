package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.NameTitleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NameTitle} and its DTO {@link NameTitleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NameTitleMapper extends EntityMapper<NameTitleDTO, NameTitle> {



    default NameTitle fromId(Long id) {
        if (id == null) {
            return null;
        }
        NameTitle nameTitle = new NameTitle();
        nameTitle.setId(id);
        return nameTitle;
    }
}
