package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.RiskCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.RiskCategory}.
 */
public interface RiskCategoryService {

    /**
     * Save a riskCategory.
     *
     * @param riskCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    RiskCategoryDTO save(RiskCategoryDTO riskCategoryDTO);

    /**
     * Get all the riskCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RiskCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" riskCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RiskCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" riskCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
