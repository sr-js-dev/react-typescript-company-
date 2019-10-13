package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.PolicyBenefitDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.PolicyBenefit}.
 */
public interface PolicyBenefitService {

    /**
     * Save a policyBenefit.
     *
     * @param policyBenefitDTO the entity to save.
     * @return the persisted entity.
     */
    PolicyBenefitDTO save(PolicyBenefitDTO policyBenefitDTO);

    /**
     * Get all the policyBenefits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PolicyBenefitDTO> findAll(Pageable pageable);


    /**
     * Get the "id" policyBenefit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PolicyBenefitDTO> findOne(Long id);

    /**
     * Delete the "id" policyBenefit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
