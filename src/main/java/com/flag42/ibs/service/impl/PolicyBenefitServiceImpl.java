package com.flag42.ibs.service.impl;

import com.flag42.ibs.service.PolicyBenefitService;
import com.flag42.ibs.domain.PolicyBenefit;
import com.flag42.ibs.repository.PolicyBenefitRepository;
import com.flag42.ibs.service.dto.PolicyBenefitDTO;
import com.flag42.ibs.service.mapper.PolicyBenefitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PolicyBenefit}.
 */
@Service
@Transactional
public class PolicyBenefitServiceImpl implements PolicyBenefitService {

    private final Logger log = LoggerFactory.getLogger(PolicyBenefitServiceImpl.class);

    private final PolicyBenefitRepository policyBenefitRepository;

    private final PolicyBenefitMapper policyBenefitMapper;

    public PolicyBenefitServiceImpl(PolicyBenefitRepository policyBenefitRepository, PolicyBenefitMapper policyBenefitMapper) {
        this.policyBenefitRepository = policyBenefitRepository;
        this.policyBenefitMapper = policyBenefitMapper;
    }

    /**
     * Save a policyBenefit.
     *
     * @param policyBenefitDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PolicyBenefitDTO save(PolicyBenefitDTO policyBenefitDTO) {
        log.debug("Request to save PolicyBenefit : {}", policyBenefitDTO);
        PolicyBenefit policyBenefit = policyBenefitMapper.toEntity(policyBenefitDTO);
        policyBenefit = policyBenefitRepository.save(policyBenefit);
        return policyBenefitMapper.toDto(policyBenefit);
    }

    /**
     * Get all the policyBenefits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PolicyBenefitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PolicyBenefits");
        return policyBenefitRepository.findAll(pageable)
            .map(policyBenefitMapper::toDto);
    }


    /**
     * Get one policyBenefit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PolicyBenefitDTO> findOne(Long id) {
        log.debug("Request to get PolicyBenefit : {}", id);
        return policyBenefitRepository.findById(id)
            .map(policyBenefitMapper::toDto);
    }

    /**
     * Delete the policyBenefit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PolicyBenefit : {}", id);
        policyBenefitRepository.deleteById(id);
    }
}
