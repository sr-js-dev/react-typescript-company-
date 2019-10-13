package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.UnderwriterDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.Underwriter}.
 */
public interface UnderwriterService {

    /**
     * Save a underwriter.
     *
     * @param underwriterDTO the entity to save.
     * @return the persisted entity.
     */
    UnderwriterDTO save(UnderwriterDTO underwriterDTO);

    /**
     * Get all the underwriters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UnderwriterDTO> findAll(Pageable pageable);


    /**
     * Get the "id" underwriter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UnderwriterDTO> findOne(Long id);

    /**
     * Delete the "id" underwriter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
