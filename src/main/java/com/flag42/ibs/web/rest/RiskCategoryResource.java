package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.RiskCategoryService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.RiskCategoryDTO;

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
 * REST controller for managing {@link com.flag42.ibs.domain.RiskCategory}.
 */
@RestController
@RequestMapping("/api")
public class RiskCategoryResource {

    private final Logger log = LoggerFactory.getLogger(RiskCategoryResource.class);

    private static final String ENTITY_NAME = "riskCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiskCategoryService riskCategoryService;

    public RiskCategoryResource(RiskCategoryService riskCategoryService) {
        this.riskCategoryService = riskCategoryService;
    }

    /**
     * {@code POST  /risk-categories} : Create a new riskCategory.
     *
     * @param riskCategoryDTO the riskCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riskCategoryDTO, or with status {@code 400 (Bad Request)} if the riskCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/risk-categories")
    public ResponseEntity<RiskCategoryDTO> createRiskCategory(@Valid @RequestBody RiskCategoryDTO riskCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save RiskCategory : {}", riskCategoryDTO);
        if (riskCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new riskCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RiskCategoryDTO result = riskCategoryService.save(riskCategoryDTO);
        return ResponseEntity.created(new URI("/api/risk-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /risk-categories} : Updates an existing riskCategory.
     *
     * @param riskCategoryDTO the riskCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riskCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the riskCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riskCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/risk-categories")
    public ResponseEntity<RiskCategoryDTO> updateRiskCategory(@Valid @RequestBody RiskCategoryDTO riskCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update RiskCategory : {}", riskCategoryDTO);
        if (riskCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RiskCategoryDTO result = riskCategoryService.save(riskCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, riskCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /risk-categories} : get all the riskCategories.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riskCategories in body.
     */
    @GetMapping("/risk-categories")
    public ResponseEntity<List<RiskCategoryDTO>> getAllRiskCategories(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of RiskCategories");
        Page<RiskCategoryDTO> page = riskCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /risk-categories/:id} : get the "id" riskCategory.
     *
     * @param id the id of the riskCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riskCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/risk-categories/{id}")
    public ResponseEntity<RiskCategoryDTO> getRiskCategory(@PathVariable Long id) {
        log.debug("REST request to get RiskCategory : {}", id);
        Optional<RiskCategoryDTO> riskCategoryDTO = riskCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(riskCategoryDTO);
    }

    /**
     * {@code DELETE  /risk-categories/:id} : delete the "id" riskCategory.
     *
     * @param id the id of the riskCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/risk-categories/{id}")
    public ResponseEntity<Void> deleteRiskCategory(@PathVariable Long id) {
        log.debug("REST request to delete RiskCategory : {}", id);
        riskCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
