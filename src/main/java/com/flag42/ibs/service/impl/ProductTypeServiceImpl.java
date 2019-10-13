package com.flag42.ibs.service.impl;

import com.flag42.ibs.service.ProductTypeService;
import com.flag42.ibs.domain.ProductType;
import com.flag42.ibs.repository.ProductTypeRepository;
import com.flag42.ibs.service.dto.ProductTypeDTO;
import com.flag42.ibs.service.mapper.ProductTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductType}.
 */
@Service
@Transactional
public class ProductTypeServiceImpl implements ProductTypeService {

    private final Logger log = LoggerFactory.getLogger(ProductTypeServiceImpl.class);

    private final ProductTypeRepository productTypeRepository;

    private final ProductTypeMapper productTypeMapper;

    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository, ProductTypeMapper productTypeMapper) {
        this.productTypeRepository = productTypeRepository;
        this.productTypeMapper = productTypeMapper;
    }

    /**
     * Save a productType.
     *
     * @param productTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductTypeDTO save(ProductTypeDTO productTypeDTO) {
        log.debug("Request to save ProductType : {}", productTypeDTO);
        ProductType productType = productTypeMapper.toEntity(productTypeDTO);
        productType = productTypeRepository.save(productType);
        return productTypeMapper.toDto(productType);
    }

    /**
     * Get all the productTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductTypes");
        return productTypeRepository.findAll(pageable)
            .map(productTypeMapper::toDto);
    }


    /**
     * Get one productType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductTypeDTO> findOne(Long id) {
        log.debug("Request to get ProductType : {}", id);
        return productTypeRepository.findById(id)
            .map(productTypeMapper::toDto);
    }

    /**
     * Delete the productType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductType : {}", id);
        productTypeRepository.deleteById(id);
    }
}
