package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.BenefitTypeService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.BenefitTypeDTO;

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
 * REST controller for managing {@link com.flag42.ibs.domain.BenefitType}.
 */
@RestController
@RequestMapping("/api")
public class BenefitTypeResource {

    private final Logger log = LoggerFactory.getLogger(BenefitTypeResource.class);

    private static final String ENTITY_NAME = "benefitType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BenefitTypeService benefitTypeService;

    public BenefitTypeResource(BenefitTypeService benefitTypeService) {
        this.benefitTypeService = benefitTypeService;
    }

    /**
     * {@code POST  /benefit-types} : Create a new benefitType.
     *
     * @param benefitTypeDTO the benefitTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benefitTypeDTO, or with status {@code 400 (Bad Request)} if the benefitType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/benefit-types")
    public ResponseEntity<BenefitTypeDTO> createBenefitType(@Valid @RequestBody BenefitTypeDTO benefitTypeDTO) throws URISyntaxException {
        log.debug("REST request to save BenefitType : {}", benefitTypeDTO);
        if (benefitTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new benefitType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BenefitTypeDTO result = benefitTypeService.save(benefitTypeDTO);
        return ResponseEntity.created(new URI("/api/benefit-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /benefit-types} : Updates an existing benefitType.
     *
     * @param benefitTypeDTO the benefitTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefitTypeDTO,
     * or with status {@code 400 (Bad Request)} if the benefitTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benefitTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/benefit-types")
    public ResponseEntity<BenefitTypeDTO> updateBenefitType(@Valid @RequestBody BenefitTypeDTO benefitTypeDTO) throws URISyntaxException {
        log.debug("REST request to update BenefitType : {}", benefitTypeDTO);
        if (benefitTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BenefitTypeDTO result = benefitTypeService.save(benefitTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, benefitTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /benefit-types} : get all the benefitTypes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benefitTypes in body.
     */
    @GetMapping("/benefit-types")
    public ResponseEntity<List<BenefitTypeDTO>> getAllBenefitTypes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of BenefitTypes");
        Page<BenefitTypeDTO> page = benefitTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /benefit-types/:id} : get the "id" benefitType.
     *
     * @param id the id of the benefitTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the benefitTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/benefit-types/{id}")
    public ResponseEntity<BenefitTypeDTO> getBenefitType(@PathVariable Long id) {
        log.debug("REST request to get BenefitType : {}", id);
        Optional<BenefitTypeDTO> benefitTypeDTO = benefitTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(benefitTypeDTO);
    }

    /**
     * {@code DELETE  /benefit-types/:id} : delete the "id" benefitType.
     *
     * @param id the id of the benefitTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/benefit-types/{id}")
    public ResponseEntity<Void> deleteBenefitType(@PathVariable Long id) {
        log.debug("REST request to delete BenefitType : {}", id);
        benefitTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
