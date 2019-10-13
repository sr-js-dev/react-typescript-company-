package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.CoverTypeService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.CoverTypeDTO;

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
 * REST controller for managing {@link com.flag42.ibs.domain.CoverType}.
 */
@RestController
@RequestMapping("/api")
public class CoverTypeResource {

    private final Logger log = LoggerFactory.getLogger(CoverTypeResource.class);

    private static final String ENTITY_NAME = "coverType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoverTypeService coverTypeService;

    public CoverTypeResource(CoverTypeService coverTypeService) {
        this.coverTypeService = coverTypeService;
    }

    /**
     * {@code POST  /cover-types} : Create a new coverType.
     *
     * @param coverTypeDTO the coverTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coverTypeDTO, or with status {@code 400 (Bad Request)} if the coverType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cover-types")
    public ResponseEntity<CoverTypeDTO> createCoverType(@Valid @RequestBody CoverTypeDTO coverTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CoverType : {}", coverTypeDTO);
        if (coverTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new coverType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoverTypeDTO result = coverTypeService.save(coverTypeDTO);
        return ResponseEntity.created(new URI("/api/cover-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cover-types} : Updates an existing coverType.
     *
     * @param coverTypeDTO the coverTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coverTypeDTO,
     * or with status {@code 400 (Bad Request)} if the coverTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coverTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cover-types")
    public ResponseEntity<CoverTypeDTO> updateCoverType(@Valid @RequestBody CoverTypeDTO coverTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CoverType : {}", coverTypeDTO);
        if (coverTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CoverTypeDTO result = coverTypeService.save(coverTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, coverTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cover-types} : get all the coverTypes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coverTypes in body.
     */
    @GetMapping("/cover-types")
    public ResponseEntity<List<CoverTypeDTO>> getAllCoverTypes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of CoverTypes");
        Page<CoverTypeDTO> page = coverTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cover-types/:id} : get the "id" coverType.
     *
     * @param id the id of the coverTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coverTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cover-types/{id}")
    public ResponseEntity<CoverTypeDTO> getCoverType(@PathVariable Long id) {
        log.debug("REST request to get CoverType : {}", id);
        Optional<CoverTypeDTO> coverTypeDTO = coverTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coverTypeDTO);
    }

    /**
     * {@code DELETE  /cover-types/:id} : delete the "id" coverType.
     *
     * @param id the id of the coverTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cover-types/{id}")
    public ResponseEntity<Void> deleteCoverType(@PathVariable Long id) {
        log.debug("REST request to delete CoverType : {}", id);
        coverTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
