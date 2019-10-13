package com.flag42.ibs.service.impl;

import com.flag42.ibs.service.UnderwriterService;
import com.flag42.ibs.domain.Underwriter;
import com.flag42.ibs.repository.UnderwriterRepository;
import com.flag42.ibs.service.dto.UnderwriterDTO;
import com.flag42.ibs.service.mapper.UnderwriterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Underwriter}.
 */
@Service
@Transactional
public class UnderwriterServiceImpl implements UnderwriterService {

    private final Logger log = LoggerFactory.getLogger(UnderwriterServiceImpl.class);

    private final UnderwriterRepository underwriterRepository;

    private final UnderwriterMapper underwriterMapper;

    public UnderwriterServiceImpl(UnderwriterRepository underwriterRepository, UnderwriterMapper underwriterMapper) {
        this.underwriterRepository = underwriterRepository;
        this.underwriterMapper = underwriterMapper;
    }

    /**
     * Save a underwriter.
     *
     * @param underwriterDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UnderwriterDTO save(UnderwriterDTO underwriterDTO) {
        log.debug("Request to save Underwriter : {}", underwriterDTO);
        Underwriter underwriter = underwriterMapper.toEntity(underwriterDTO);
        underwriter = underwriterRepository.save(underwriter);
        return underwriterMapper.toDto(underwriter);
    }

    /**
     * Get all the underwriters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UnderwriterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Underwriters");
        return underwriterRepository.findAll(pageable)
            .map(underwriterMapper::toDto);
    }


    /**
     * Get one underwriter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UnderwriterDTO> findOne(Long id) {
        log.debug("Request to get Underwriter : {}", id);
        return underwriterRepository.findById(id)
            .map(underwriterMapper::toDto);
    }

    /**
     * Delete the underwriter by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Underwriter : {}", id);
        underwriterRepository.deleteById(id);
    }
}
