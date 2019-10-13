package com.flag42.ibs.service.impl;

import com.flag42.ibs.service.RiskClassService;
import com.flag42.ibs.domain.RiskClass;
import com.flag42.ibs.repository.RiskClassRepository;
import com.flag42.ibs.service.dto.RiskClassDTO;
import com.flag42.ibs.service.mapper.RiskClassMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RiskClass}.
 */
@Service
@Transactional
public class RiskClassServiceImpl implements RiskClassService {

    private final Logger log = LoggerFactory.getLogger(RiskClassServiceImpl.class);

    private final RiskClassRepository riskClassRepository;

    private final RiskClassMapper riskClassMapper;

    public RiskClassServiceImpl(RiskClassRepository riskClassRepository, RiskClassMapper riskClassMapper) {
        this.riskClassRepository = riskClassRepository;
        this.riskClassMapper = riskClassMapper;
    }

    /**
     * Save a riskClass.
     *
     * @param riskClassDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RiskClassDTO save(RiskClassDTO riskClassDTO) {
        log.debug("Request to save RiskClass : {}", riskClassDTO);
        RiskClass riskClass = riskClassMapper.toEntity(riskClassDTO);
        riskClass = riskClassRepository.save(riskClass);
        return riskClassMapper.toDto(riskClass);
    }

    /**
     * Get all the riskClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RiskClassDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RiskClasses");
        return riskClassRepository.findAll(pageable)
            .map(riskClassMapper::toDto);
    }


    /**
     * Get one riskClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RiskClassDTO> findOne(Long id) {
        log.debug("Request to get RiskClass : {}", id);
        return riskClassRepository.findById(id)
            .map(riskClassMapper::toDto);
    }

    /**
     * Delete the riskClass by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RiskClass : {}", id);
        riskClassRepository.deleteById(id);
    }
}
