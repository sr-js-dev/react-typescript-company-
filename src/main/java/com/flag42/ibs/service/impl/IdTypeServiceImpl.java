package com.flag42.ibs.service.impl;

import com.flag42.ibs.service.IdTypeService;
import com.flag42.ibs.domain.IdType;
import com.flag42.ibs.repository.IdTypeRepository;
import com.flag42.ibs.service.dto.IdTypeDTO;
import com.flag42.ibs.service.mapper.IdTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IdType}.
 */
@Service
@Transactional
public class IdTypeServiceImpl implements IdTypeService {

    private final Logger log = LoggerFactory.getLogger(IdTypeServiceImpl.class);

    private final IdTypeRepository idTypeRepository;

    private final IdTypeMapper idTypeMapper;

    public IdTypeServiceImpl(IdTypeRepository idTypeRepository, IdTypeMapper idTypeMapper) {
        this.idTypeRepository = idTypeRepository;
        this.idTypeMapper = idTypeMapper;
    }

    /**
     * Save a idType.
     *
     * @param idTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IdTypeDTO save(IdTypeDTO idTypeDTO) {
        log.debug("Request to save IdType : {}", idTypeDTO);
        IdType idType = idTypeMapper.toEntity(idTypeDTO);
        idType = idTypeRepository.save(idType);
        return idTypeMapper.toDto(idType);
    }

    /**
     * Get all the idTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IdTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IdTypes");
        return idTypeRepository.findAll(pageable)
            .map(idTypeMapper::toDto);
    }


    /**
     * Get one idType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IdTypeDTO> findOne(Long id) {
        log.debug("Request to get IdType : {}", id);
        return idTypeRepository.findById(id)
            .map(idTypeMapper::toDto);
    }

    /**
     * Delete the idType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IdType : {}", id);
        idTypeRepository.deleteById(id);
    }
}
