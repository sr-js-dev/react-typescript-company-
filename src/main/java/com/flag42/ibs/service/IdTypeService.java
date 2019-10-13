package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.IdTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.IdType}.
 */
public interface IdTypeService {

    /**
     * Save a idType.
     *
     * @param idTypeDTO the entity to save.
     * @return the persisted entity.
     */
    IdTypeDTO save(IdTypeDTO idTypeDTO);

    /**
     * Get all the idTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IdTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" idType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IdTypeDTO> findOne(Long id);

    /**
     * Delete the "id" idType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
