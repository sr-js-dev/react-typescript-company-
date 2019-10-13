package com.flag42.ibs.service.impl;

import com.flag42.ibs.service.BenefitTypeService;
import com.flag42.ibs.domain.BenefitType;
import com.flag42.ibs.repository.BenefitTypeRepository;
import com.flag42.ibs.service.dto.BenefitTypeDTO;
import com.flag42.ibs.service.mapper.BenefitTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BenefitType}.
 */
@Service
@Transactional
public class BenefitTypeServiceImpl implements BenefitTypeService {

    private final Logger log = LoggerFactory.getLogger(BenefitTypeServiceImpl.class);

    private final BenefitTypeRepository benefitTypeRepository;

    private final BenefitTypeMapper benefitTypeMapper;

    public BenefitTypeServiceImpl(BenefitTypeRepository benefitTypeRepository, BenefitTypeMapper benefitTypeMapper) {
        this.benefitTypeRepository = benefitTypeRepository;
        this.benefitTypeMapper = benefitTypeMapper;
    }

    /**
     * Save a benefitType.
     *
     * @param benefitTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BenefitTypeDTO save(BenefitTypeDTO benefitTypeDTO) {
        log.debug("Request to save BenefitType : {}", benefitTypeDTO);
        BenefitType benefitType = benefitTypeMapper.toEntity(benefitTypeDTO);
        benefitType = benefitTypeRepository.save(benefitType);
        return benefitTypeMapper.toDto(benefitType);
    }

    /**
     * Get all the benefitTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BenefitTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BenefitTypes");
        return benefitTypeRepository.findAll(pageable)
            .map(benefitTypeMapper::toDto);
    }


    /**
     * Get one benefitType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BenefitTypeDTO> findOne(Long id) {
        log.debug("Request to get BenefitType : {}", id);
        return benefitTypeRepository.findById(id)
            .map(benefitTypeMapper::toDto);
    }

    /**
     * Delete the benefitType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BenefitType : {}", id);
        benefitTypeRepository.deleteById(id);
    }
}
