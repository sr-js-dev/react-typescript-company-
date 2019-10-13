package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.ProductTypeService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.ProductTypeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.flag42.ibs.domain.ProductType}.
 */
@RestController
@RequestMapping("/api")
public class ProductTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProductTypeResource.class);

    private static final String ENTITY_NAME = "productType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductTypeService productTypeService;

    public ProductTypeResource(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    /**
     * {@code POST  /product-types} : Create a new productType.
     *
     * @param productTypeDTO the productTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productTypeDTO, or with status {@code 400 (Bad Request)} if the productType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-types")
    public ResponseEntity<ProductTypeDTO> createProductType(@Valid @RequestBody ProductTypeDTO productTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ProductType : {}", productTypeDTO);
        if (productTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new productType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductTypeDTO result = productTypeService.save(productTypeDTO);
        return ResponseEntity.created(new URI("/api/product-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-types} : Updates an existing productType.
     *
     * @param productTypeDTO the productTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productTypeDTO,
     * or with status {@code 400 (Bad Request)} if the productTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-types")
    public ResponseEntity<ProductTypeDTO> updateProductType(@Valid @RequestBody ProductTypeDTO productTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ProductType : {}", productTypeDTO);
        if (productTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductTypeDTO result = productTypeService.save(productTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, productTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /product-types} : get all the productTypes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productTypes in body.
     */
    @GetMapping("/product-types")
    public ResponseEntity<List<ProductTypeDTO>> getAllProductTypes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ProductTypes");
        Page<ProductTypeDTO> page = productTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-types/:id} : get the "id" productType.
     *
     * @param id the id of the productTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-types/{id}")
    public ResponseEntity<ProductTypeDTO> getProductType(@PathVariable Long id) {
        log.debug("REST request to get ProductType : {}", id);
        Optional<ProductTypeDTO> productTypeDTO = productTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productTypeDTO);
    }

    /**
     * {@code DELETE  /product-types/:id} : delete the "id" productType.
     *
     * @param id the id of the productTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-types/{id}")
    public ResponseEntity<Void> deleteProductType(@PathVariable Long id) {
        log.debug("REST request to delete ProductType : {}", id);
        productTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
