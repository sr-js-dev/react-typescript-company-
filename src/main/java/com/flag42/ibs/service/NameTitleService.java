package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.NameTitleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.NameTitle}.
 */
public interface NameTitleService {

    /**
     * Save a nameTitle.
     *
     * @param nameTitleDTO the entity to save.
     * @return the persisted entity.
     */
    NameTitleDTO save(NameTitleDTO nameTitleDTO);

    /**
     * Get all the nameTitles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NameTitleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" nameTitle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NameTitleDTO> findOne(Long id);

    /**
     * Delete the "id" nameTitle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
