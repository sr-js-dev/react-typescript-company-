package com.flag42.ibs.service.impl;

import com.flag42.ibs.service.ClientPolicyService;
import com.flag42.ibs.domain.ClientPolicy;
import com.flag42.ibs.repository.ClientPolicyRepository;
import com.flag42.ibs.service.dto.ClientPolicyDTO;
import com.flag42.ibs.service.mapper.ClientPolicyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClientPolicy}.
 */
@Service
@Transactional
public class ClientPolicyServiceImpl implements ClientPolicyService {

    private final Logger log = LoggerFactory.getLogger(ClientPolicyServiceImpl.class);

    private final ClientPolicyRepository clientPolicyRepository;

    private final ClientPolicyMapper clientPolicyMapper;

    public ClientPolicyServiceImpl(ClientPolicyRepository clientPolicyRepository, ClientPolicyMapper clientPolicyMapper) {
        this.clientPolicyRepository = clientPolicyRepository;
        this.clientPolicyMapper = clientPolicyMapper;
    }

    /**
     * Save a clientPolicy.
     *
     * @param clientPolicyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClientPolicyDTO save(ClientPolicyDTO clientPolicyDTO) {
        log.debug("Request to save ClientPolicy : {}", clientPolicyDTO);
        ClientPolicy clientPolicy = clientPolicyMapper.toEntity(clientPolicyDTO);
        clientPolicy = clientPolicyRepository.save(clientPolicy);
        return clientPolicyMapper.toDto(clientPolicy);
    }

    /**
     * Get all the clientPolicies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientPolicyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientPolicies");
        return clientPolicyRepository.findAll(pageable)
            .map(clientPolicyMapper::toDto);
    }


    /**
     * Get one clientPolicy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClientPolicyDTO> findOne(Long id) {
        log.debug("Request to get ClientPolicy : {}", id);
        return clientPolicyRepository.findById(id)
            .map(clientPolicyMapper::toDto);
    }

    /**
     * Delete the clientPolicy by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientPolicy : {}", id);
        clientPolicyRepository.deleteById(id);
    }
}
