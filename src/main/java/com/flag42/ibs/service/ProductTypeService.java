package com.flag42.ibs.service;

import com.flag42.ibs.service.dto.ProductTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.flag42.ibs.domain.ProductType}.
 */
public interface ProductTypeService {

    /**
     * Save a productType.
     *
     * @param productTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ProductTypeDTO save(ProductTypeDTO productTypeDTO);

    /**
     * Get all the productTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" productType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductTypeDTO> findOne(Long id);

    /**
     * Delete the "id" productType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
