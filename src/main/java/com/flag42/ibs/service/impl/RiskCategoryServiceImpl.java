package com.flag42.ibs.service.impl;

import com.flag42.ibs.service.RiskCategoryService;
import com.flag42.ibs.domain.RiskCategory;
import com.flag42.ibs.repository.RiskCategoryRepository;
import com.flag42.ibs.service.dto.RiskCategoryDTO;
import com.flag42.ibs.service.mapper.RiskCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RiskCategory}.
 */
@Service
@Transactional
public class RiskCategoryServiceImpl implements RiskCategoryService {

    private final Logger log = LoggerFactory.getLogger(RiskCategoryServiceImpl.class);

    private final RiskCategoryRepository riskCategoryRepository;

    private final RiskCategoryMapper riskCategoryMapper;

    public RiskCategoryServiceImpl(RiskCategoryRepository riskCategoryRepository, RiskCategoryMapper riskCategoryMapper) {
        this.riskCategoryRepository = riskCategoryRepository;
        this.riskCategoryMapper = riskCategoryMapper;
    }

    /**
     * Save a riskCategory.
     *
     * @param riskCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RiskCategoryDTO save(RiskCategoryDTO riskCategoryDTO) {
        log.debug("Request to save RiskCategory : {}", riskCategoryDTO);
        RiskCategory riskCategory = riskCategoryMapper.toEntity(riskCategoryDTO);
        riskCategory = riskCategoryRepository.save(riskCategory);
        return riskCategoryMapper.toDto(riskCategory);
    }

    /**
     * Get all the riskCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RiskCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RiskCategories");
        return riskCategoryRepository.findAll(pageable)
            .map(riskCategoryMapper::toDto);
    }


    /**
     * Get one riskCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RiskCategoryDTO> findOne(Long id) {
        log.debug("Request to get RiskCategory : {}", id);
        return riskCategoryRepository.findById(id)
            .map(riskCategoryMapper::toDto);
    }

    /**
     * Delete the riskCategory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RiskCategory : {}", id);
        riskCategoryRepository.deleteById(id);
    }
}
