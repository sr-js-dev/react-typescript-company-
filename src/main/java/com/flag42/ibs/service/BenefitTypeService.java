package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.BenefitTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.BenefitType}.
 */
public interface BenefitTypeService {

    /**
     * Save a benefitType.
     *
     * @param benefitTypeDTO the entity to save.
     * @return the persisted entity.
     */
    BenefitTypeDTO save(BenefitTypeDTO benefitTypeDTO);

    /**
     * Get all the benefitTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BenefitTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" benefitType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BenefitTypeDTO> findOne(Long id);

    /**
     * Delete the "id" benefitType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
