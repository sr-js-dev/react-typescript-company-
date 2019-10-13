package com.flag42.ibs.service.impl;

import com.flag42.ibs.service.ClientCategoryService;
import com.flag42.ibs.domain.ClientCategory;
import com.flag42.ibs.repository.ClientCategoryRepository;
import com.flag42.ibs.service.dto.ClientCategoryDTO;
import com.flag42.ibs.service.mapper.ClientCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClientCategory}.
 */
@Service
@Transactional
public class ClientCategoryServiceImpl implements ClientCategoryService {

    private final Logger log = LoggerFactory.getLogger(ClientCategoryServiceImpl.class);

    private final ClientCategoryRepository clientCategoryRepository;

    private final ClientCategoryMapper clientCategoryMapper;

    public ClientCategoryServiceImpl(ClientCategoryRepository clientCategoryRepository, ClientCategoryMapper clientCategoryMapper) {
        this.clientCategoryRepository = clientCategoryRepository;
        this.clientCategoryMapper = clientCategoryMapper;
    }

    /**
     * Save a clientCategory.
     *
     * @param clientCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClientCategoryDTO save(ClientCategoryDTO clientCategoryDTO) {
        log.debug("Request to save ClientCategory : {}", clientCategoryDTO);
        ClientCategory clientCategory = clientCategoryMapper.toEntity(clientCategoryDTO);
        clientCategory = clientCategoryRepository.save(clientCategory);
        return clientCategoryMapper.toDto(clientCategory);
    }

    /**
     * Get all the clientCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientCategories");
        return clientCategoryRepository.findAll(pageable)
            .map(clientCategoryMapper::toDto);
    }


    /**
     * Get one clientCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClientCategoryDTO> findOne(Long id) {
        log.debug("Request to get ClientCategory : {}", id);
        return clientCategoryRepository.findById(id)
            .map(clientCategoryMapper::toDto);
    }

    /**
     * Delete the clientCategory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientCategory : {}", id);
        clientCategoryRepository.deleteById(id);
    }
}
