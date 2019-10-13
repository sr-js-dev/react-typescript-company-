package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.PolicyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.Policy}.
 */
public interface PolicyService {

    /**
     * Save a policy.
     *
     * @param policyDTO the entity to save.
     * @return the persisted entity.
     */
    PolicyDTO save(PolicyDTO policyDTO);

    /**
     * Get all the policies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PolicyDTO> findAll(Pageable pageable);


    /**
     * Get the "id" policy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PolicyDTO> findOne(Long id);

    /**
     * Delete the "id" policy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
