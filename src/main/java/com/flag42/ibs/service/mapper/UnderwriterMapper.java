package com.flag42.ibs.service.mapper;

import com.flag42.ibs.domain.*;
import com.flag42.ibs.service.dto.UnderwriterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Underwriter} and its DTO {@link UnderwriterDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnderwriterMapper extends EntityMapper<UnderwriterDTO, Underwriter> {



    default Underwriter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Underwriter underwriter = new Underwriter();
        underwriter.setId(id);
        return underwriter;
    }
}
