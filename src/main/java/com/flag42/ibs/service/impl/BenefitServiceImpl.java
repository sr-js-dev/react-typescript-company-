package com.flag42.ibs.service.impl;

import com.flag42.ibs.service.BenefitService;
import com.flag42.ibs.domain.Benefit;
import com.flag42.ibs.repository.BenefitRepository;
import com.flag42.ibs.service.dto.BenefitDTO;
import com.flag42.ibs.service.mapper.BenefitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Benefit}.
 */
@Service
@Transactional
public class BenefitServiceImpl implements BenefitService {

    private final Logger log = LoggerFactory.getLogger(BenefitServiceImpl.class);

    private final BenefitRepository benefitRepository;

    private final BenefitMapper benefitMapper;

    public BenefitServiceImpl(BenefitRepository benefitRepository, BenefitMapper benefitMapper) {
        this.benefitRepository = benefitRepository;
        this.benefitMapper = benefitMapper;
    }

    /**
     * Save a benefit.
     *
     * @param benefitDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BenefitDTO save(BenefitDTO benefitDTO) {
        log.debug("Request to save Benefit : {}", benefitDTO);
        Benefit benefit = benefitMapper.toEntity(benefitDTO);
        benefit = benefitRepository.save(benefit);
        return benefitMapper.toDto(benefit);
    }

    /**
     * Get all the benefits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BenefitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Benefits");
        return benefitRepository.findAll(pageable)
            .map(benefitMapper::toDto);
    }


    /**
     * Get one benefit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BenefitDTO> findOne(Long id) {
        log.debug("Request to get Benefit : {}", id);
        return benefitRepository.findById(id)
            .map(benefitMapper::toDto);
    }

    /**
     * Delete the benefit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Benefit : {}", id);
        benefitRepository.deleteById(id);
    }
}
