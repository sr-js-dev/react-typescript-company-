package com.flag42.ibs.service.impl;

import com.flag42.ibs.service.ClientPolicyPaymentService;
import com.flag42.ibs.domain.ClientPolicyPayment;
import com.flag42.ibs.repository.ClientPolicyPaymentRepository;
import com.flag42.ibs.service.dto.ClientPolicyPaymentDTO;
import com.flag42.ibs.service.mapper.ClientPolicyPaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClientPolicyPayment}.
 */
@Service
@Transactional
public class ClientPolicyPaymentServiceImpl implements ClientPolicyPaymentService {

    private final Logger log = LoggerFactory.getLogger(ClientPolicyPaymentServiceImpl.class);

    private final ClientPolicyPaymentRepository clientPolicyPaymentRepository;

    private final ClientPolicyPaymentMapper clientPolicyPaymentMapper;

    public ClientPolicyPaymentServiceImpl(ClientPolicyPaymentRepository clientPolicyPaymentRepository, ClientPolicyPaymentMapper clientPolicyPaymentMapper) {
        this.clientPolicyPaymentRepository = clientPolicyPaymentRepository;
        this.clientPolicyPaymentMapper = clientPolicyPaymentMapper;
    }

    /**
     * Save a clientPolicyPayment.
     *
     * @param clientPolicyPaymentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClientPolicyPaymentDTO save(ClientPolicyPaymentDTO clientPolicyPaymentDTO) {
        log.debug("Request to save ClientPolicyPayment : {}", clientPolicyPaymentDTO);
        ClientPolicyPayment clientPolicyPayment = clientPolicyPaymentMapper.toEntity(clientPolicyPaymentDTO);
        clientPolicyPayment = clientPolicyPaymentRepository.save(clientPolicyPayment);
        return clientPolicyPaymentMapper.toDto(clientPolicyPayment);
    }

    /**
     * Get all the clientPolicyPayments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientPolicyPaymentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientPolicyPayments");
        return clientPolicyPaymentRepository.findAll(pageable)
            .map(clientPolicyPaymentMapper::toDto);
    }


    /**
     * Get one clientPolicyPayment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClientPolicyPaymentDTO> findOne(Long id) {
        log.debug("Request to get ClientPolicyPayment : {}", id);
        return clientPolicyPaymentRepository.findById(id)
            .map(clientPolicyPaymentMapper::toDto);
    }

    /**
     * Delete the clientPolicyPayment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientPolicyPayment : {}", id);
        clientPolicyPaymentRepository.deleteById(id);
    }
}
