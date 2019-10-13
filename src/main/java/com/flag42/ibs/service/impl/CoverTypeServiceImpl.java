package com.flag42.ibs.service.impl;

import com.flag42.ibs.service.CoverTypeService;
import com.flag42.ibs.domain.CoverType;
import com.flag42.ibs.repository.CoverTypeRepository;
import com.flag42.ibs.service.dto.CoverTypeDTO;
import com.flag42.ibs.service.mapper.CoverTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CoverType}.
 */
@Service
@Transactional
public class CoverTypeServiceImpl implements CoverTypeService {

    private final Logger log = LoggerFactory.getLogger(CoverTypeServiceImpl.class);

    private final CoverTypeRepository coverTypeRepository;

    private final CoverTypeMapper coverTypeMapper;

    public CoverTypeServiceImpl(CoverTypeRepository coverTypeRepository, CoverTypeMapper coverTypeMapper) {
        this.coverTypeRepository = coverTypeRepository;
        this.coverTypeMapper = coverTypeMapper;
    }

    /**
     * Save a coverType.
     *
     * @param coverTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CoverTypeDTO save(CoverTypeDTO coverTypeDTO) {
        log.debug("Request to save CoverType : {}", coverTypeDTO);
        CoverType coverType = coverTypeMapper.toEntity(coverTypeDTO);
        coverType = coverTypeRepository.save(coverType);
        return coverTypeMapper.toDto(coverType);
    }

    /**
     * Get all the coverTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CoverTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CoverTypes");
        return coverTypeRepository.findAll(pageable)
            .map(coverTypeMapper::toDto);
    }


    /**
     * Get one coverType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CoverTypeDTO> findOne(Long id) {
        log.debug("Request to get CoverType : {}", id);
        return coverTypeRepository.findById(id)
            .map(coverTypeMapper::toDto);
    }

    /**
     * Delete the coverType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CoverType : {}", id);
        coverTypeRepository.deleteById(id);
    }
}
