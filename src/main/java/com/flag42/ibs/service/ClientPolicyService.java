package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.ClientPolicyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.ClientPolicy}.
 */
public interface ClientPolicyService {

    /**
     * Save a clientPolicy.
     *
     * @param clientPolicyDTO the entity to save.
     * @return the persisted entity.
     */
    ClientPolicyDTO save(ClientPolicyDTO clientPolicyDTO);

    /**
     * Get all the clientPolicies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClientPolicyDTO> findAll(Pageable pageable);


    /**
     * Get the "id" clientPolicy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientPolicyDTO> findOne(Long id);

    /**
     * Delete the "id" clientPolicy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
