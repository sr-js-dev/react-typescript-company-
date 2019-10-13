package com.flag42.ibs.service.impl;

import com.flag42.ibs.service.NameTitleService;
import com.flag42.ibs.domain.NameTitle;
import com.flag42.ibs.repository.NameTitleRepository;
import com.flag42.ibs.service.dto.NameTitleDTO;
import com.flag42.ibs.service.mapper.NameTitleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NameTitle}.
 */
@Service
@Transactional
public class NameTitleServiceImpl implements NameTitleService {

    private final Logger log = LoggerFactory.getLogger(NameTitleServiceImpl.class);

    private final NameTitleRepository nameTitleRepository;

    private final NameTitleMapper nameTitleMapper;

    public NameTitleServiceImpl(NameTitleRepository nameTitleRepository, NameTitleMapper nameTitleMapper) {
        this.nameTitleRepository = nameTitleRepository;
        this.nameTitleMapper = nameTitleMapper;
    }

    /**
     * Save a nameTitle.
     *
     * @param nameTitleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NameTitleDTO save(NameTitleDTO nameTitleDTO) {
        log.debug("Request to save NameTitle : {}", nameTitleDTO);
        NameTitle nameTitle = nameTitleMapper.toEntity(nameTitleDTO);
        nameTitle = nameTitleRepository.save(nameTitle);
        return nameTitleMapper.toDto(nameTitle);
    }

    /**
     * Get all the nameTitles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NameTitleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NameTitles");
        return nameTitleRepository.findAll(pageable)
            .map(nameTitleMapper::toDto);
    }


    /**
     * Get one nameTitle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NameTitleDTO> findOne(Long id) {
        log.debug("Request to get NameTitle : {}", id);
        return nameTitleRepository.findById(id)
            .map(nameTitleMapper::toDto);
    }

    /**
     * Delete the nameTitle by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NameTitle : {}", id);
        nameTitleRepository.deleteById(id);
    }
}
