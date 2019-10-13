package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.ClientCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.ClientCategory}.
 */
public interface ClientCategoryService {

    /**
     * Save a clientCategory.
     *
     * @param clientCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    ClientCategoryDTO save(ClientCategoryDTO clientCategoryDTO);

    /**
     * Get all the clientCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClientCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" clientCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" clientCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
