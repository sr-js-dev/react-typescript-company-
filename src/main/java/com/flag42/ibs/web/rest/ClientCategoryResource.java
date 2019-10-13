package com.flag42.ibs.web.rest;

import com.flag42.ibs.service.ClientCategoryService;
import com.flag42.ibs.web.rest.errors.BadRequestAlertException;
import com.flag42.ibs.service.dto.ClientCategoryDTO;

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
 * REST controller for managing {@link com.flag42.ibs.domain.ClientCategory}.
 */
@RestController
@RequestMapping("/api")
public class ClientCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ClientCategoryResource.class);

    private static final String ENTITY_NAME = "clientCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientCategoryService clientCategoryService;

    public ClientCategoryResource(ClientCategoryService clientCategoryService) {
        this.clientCategoryService = clientCategoryService;
    }

    /**
     * {@code POST  /client-categories} : Create a new clientCategory.
     *
     * @param clientCategoryDTO the clientCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientCategoryDTO, or with status {@code 400 (Bad Request)} if the clientCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-categories")
    public ResponseEntity<ClientCategoryDTO> createClientCategory(@Valid @RequestBody ClientCategoryDTO clientCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ClientCategory : {}", clientCategoryDTO);
        if (clientCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientCategoryDTO result = clientCategoryService.save(clientCategoryDTO);
        return ResponseEntity.created(new URI("/api/client-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-categories} : Updates an existing clientCategory.
     *
     * @param clientCategoryDTO the clientCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the clientCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-categories")
    public ResponseEntity<ClientCategoryDTO> updateClientCategory(@Valid @RequestBody ClientCategoryDTO clientCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update ClientCategory : {}", clientCategoryDTO);
        if (clientCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientCategoryDTO result = clientCategoryService.save(clientCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clientCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /client-categories} : get all the clientCategories.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientCategories in body.
     */
    @GetMapping("/client-categories")
    public ResponseEntity<List<ClientCategoryDTO>> getAllClientCategories(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ClientCategories");
        Page<ClientCategoryDTO> page = clientCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-categories/:id} : get the "id" clientCategory.
     *
     * @param id the id of the clientCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-categories/{id}")
    public ResponseEntity<ClientCategoryDTO> getClientCategory(@PathVariable Long id) {
        log.debug("REST request to get ClientCategory : {}", id);
        Optional<ClientCategoryDTO> clientCategoryDTO = clientCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientCategoryDTO);
    }

    /**
     * {@code DELETE  /client-categories/:id} : delete the "id" clientCategory.
     *
     * @param id the id of the clientCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-categories/{id}")
    public ResponseEntity<Void> deleteClientCategory(@PathVariable Long id) {
        log.debug("REST request to delete ClientCategory : {}", id);
        clientCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
