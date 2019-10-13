package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.BenefitDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.Benefit}.
 */
public interface BenefitService {

    /**
     * Save a benefit.
     *
     * @param benefitDTO the entity to save.
     * @return the persisted entity.
     */
    BenefitDTO save(BenefitDTO benefitDTO);

    /**
     * Get all the benefits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BenefitDTO> findAll(Pageable pageable);


    /**
     * Get the "id" benefit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BenefitDTO> findOne(Long id);

    /**
     * Delete the "id" benefit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
