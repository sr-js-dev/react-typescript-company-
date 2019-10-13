package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.IdTypeService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.IdTypeDTO;

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
 * REST controller for managing {@link com.flag42.ibs.domain.IdType}.
 */
@RestController
@RequestMapping("/api")
public class IdTypeResource {

    private final Logger log = LoggerFactory.getLogger(IdTypeResource.class);

    private static final String ENTITY_NAME = "idType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdTypeService idTypeService;

    public IdTypeResource(IdTypeService idTypeService) {
        this.idTypeService = idTypeService;
    }

    /**
     * {@code POST  /id-types} : Create a new idType.
     *
     * @param idTypeDTO the idTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new idTypeDTO, or with status {@code 400 (Bad Request)} if the idType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/id-types")
    public ResponseEntity<IdTypeDTO> createIdType(@Valid @RequestBody IdTypeDTO idTypeDTO) throws URISyntaxException {
        log.debug("REST request to save IdType : {}", idTypeDTO);
        if (idTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new idType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IdTypeDTO result = idTypeService.save(idTypeDTO);
        return ResponseEntity.created(new URI("/api/id-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /id-types} : Updates an existing idType.
     *
     * @param idTypeDTO the idTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated idTypeDTO,
     * or with status {@code 400 (Bad Request)} if the idTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the idTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/id-types")
    public ResponseEntity<IdTypeDTO> updateIdType(@Valid @RequestBody IdTypeDTO idTypeDTO) throws URISyntaxException {
        log.debug("REST request to update IdType : {}", idTypeDTO);
        if (idTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IdTypeDTO result = idTypeService.save(idTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, idTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /id-types} : get all the idTypes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of idTypes in body.
     */
    @GetMapping("/id-types")
    public ResponseEntity<List<IdTypeDTO>> getAllIdTypes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of IdTypes");
        Page<IdTypeDTO> page = idTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /id-types/:id} : get the "id" idType.
     *
     * @param id the id of the idTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the idTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/id-types/{id}")
    public ResponseEntity<IdTypeDTO> getIdType(@PathVariable Long id) {
        log.debug("REST request to get IdType : {}", id);
        Optional<IdTypeDTO> idTypeDTO = idTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(idTypeDTO);
    }

    /**
     * {@code DELETE  /id-types/:id} : delete the "id" idType.
     *
     * @param id the id of the idTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/id-types/{id}")
    public ResponseEntity<Void> deleteIdType(@PathVariable Long id) {
        log.debug("REST request to delete IdType : {}", id);
        idTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
