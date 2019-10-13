package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.ClientPolicyPaymentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.ClientPolicyPayment}.
 */
public interface ClientPolicyPaymentService {

    /**
     * Save a clientPolicyPayment.
     *
     * @param clientPolicyPaymentDTO the entity to save.
     * @return the persisted entity.
     */
    ClientPolicyPaymentDTO save(ClientPolicyPaymentDTO clientPolicyPaymentDTO);

    /**
     * Get all the clientPolicyPayments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClientPolicyPaymentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" clientPolicyPayment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientPolicyPaymentDTO> findOne(Long id);

    /**
     * Delete the "id" clientPolicyPayment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
