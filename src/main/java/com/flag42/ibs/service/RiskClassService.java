package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.RiskClassDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.RiskClass}.
 */
public interface RiskClassService {

    /**
     * Save a riskClass.
     *
     * @param riskClassDTO the entity to save.
     * @return the persisted entity.
     */
    RiskClassDTO save(RiskClassDTO riskClassDTO);

    /**
     * Get all the riskClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RiskClassDTO> findAll(Pageable pageable);


    /**
     * Get the "id" riskClass.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RiskClassDTO> findOne(Long id);

    /**
     * Delete the "id" riskClass.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
