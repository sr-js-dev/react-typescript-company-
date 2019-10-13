package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.CoverTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.CoverType}.
 */
public interface CoverTypeService {

    /**
     * Save a coverType.
     *
     * @param coverTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CoverTypeDTO save(CoverTypeDTO coverTypeDTO);

    /**
     * Get all the coverTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CoverTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" coverType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CoverTypeDTO> findOne(Long id);

    /**
     * Delete the "id" coverType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
