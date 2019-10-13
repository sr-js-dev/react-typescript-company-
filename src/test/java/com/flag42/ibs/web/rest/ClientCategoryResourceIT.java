package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.ClientCategory;
import com.flag42.ibs.repository.ClientCategoryRepository;
import com.flag42.ibs.service.ClientCategoryService;
import com.flag42.ibs.service.dto.ClientCategoryDTO;
import com.flag42.ibs.service.mapper.ClientCategoryMapper;
import com.flag42.ibs.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.flag42.ibs.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ClientCategoryResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class ClientCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ClientCategoryRepository clientCategoryRepository;

    @Autowired
    private ClientCategoryMapper clientCategoryMapper;

    @Autowired
    private ClientCategoryService clientCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restClientCategoryMockMvc;

    private ClientCategory clientCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientCategoryResource clientCategoryResource = new ClientCategoryResource(clientCategoryService);
        this.restClientCategoryMockMvc = MockMvcBuilders.standaloneSetup(clientCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientCategory createEntity(EntityManager em) {
        ClientCategory clientCategory = new ClientCategory()
            .name(DEFAULT_NAME);
        return clientCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientCategory createUpdatedEntity(EntityManager em) {
        ClientCategory clientCategory = new ClientCategory()
            .name(UPDATED_NAME);
        return clientCategory;
    }

    @BeforeEach
    public void initTest() {
        clientCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientCategory() throws Exception {
        int databaseSizeBeforeCreate = clientCategoryRepository.findAll().size();

        // Create the ClientCategory
        ClientCategoryDTO clientCategoryDTO = clientCategoryMapper.toDto(clientCategory);
        restClientCategoryMockMvc.perform(post("/api/client-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ClientCategory in the database
        List<ClientCategory> clientCategoryList = clientCategoryRepository.findAll();
        assertThat(clientCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ClientCategory testClientCategory = clientCategoryList.get(clientCategoryList.size() - 1);
        assertThat(testClientCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createClientCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientCategoryRepository.findAll().size();

        // Create the ClientCategory with an existing ID
        clientCategory.setId(1L);
        ClientCategoryDTO clientCategoryDTO = clientCategoryMapper.toDto(clientCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientCategoryMockMvc.perform(post("/api/client-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientCategory in the database
        List<ClientCategory> clientCategoryList = clientCategoryRepository.findAll();
        assertThat(clientCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientCategoryRepository.findAll().size();
        // set the field null
        clientCategory.setName(null);

        // Create the ClientCategory, which fails.
        ClientCategoryDTO clientCategoryDTO = clientCategoryMapper.toDto(clientCategory);

        restClientCategoryMockMvc.perform(post("/api/client-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<ClientCategory> clientCategoryList = clientCategoryRepository.findAll();
        assertThat(clientCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientCategories() throws Exception {
        // Initialize the database
        clientCategoryRepository.saveAndFlush(clientCategory);

        // Get all the clientCategoryList
        restClientCategoryMockMvc.perform(get("/api/client-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getClientCategory() throws Exception {
        // Initialize the database
        clientCategoryRepository.saveAndFlush(clientCategory);

        // Get the clientCategory
        restClientCategoryMockMvc.perform(get("/api/client-categories/{id}", clientCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClientCategory() throws Exception {
        // Get the clientCategory
        restClientCategoryMockMvc.perform(get("/api/client-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientCategory() throws Exception {
        // Initialize the database
        clientCategoryRepository.saveAndFlush(clientCategory);

        int databaseSizeBeforeUpdate = clientCategoryRepository.findAll().size();

        // Update the clientCategory
        ClientCategory updatedClientCategory = clientCategoryRepository.findById(clientCategory.getId()).get();
        // Disconnect from session so that the updates on updatedClientCategory are not directly saved in db
        em.detach(updatedClientCategory);
        updatedClientCategory
            .name(UPDATED_NAME);
        ClientCategoryDTO clientCategoryDTO = clientCategoryMapper.toDto(updatedClientCategory);

        restClientCategoryMockMvc.perform(put("/api/client-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the ClientCategory in the database
        List<ClientCategory> clientCategoryList = clientCategoryRepository.findAll();
        assertThat(clientCategoryList).hasSize(databaseSizeBeforeUpdate);
        ClientCategory testClientCategory = clientCategoryList.get(clientCategoryList.size() - 1);
        assertThat(testClientCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingClientCategory() throws Exception {
        int databaseSizeBeforeUpdate = clientCategoryRepository.findAll().size();

        // Create the ClientCategory
        ClientCategoryDTO clientCategoryDTO = clientCategoryMapper.toDto(clientCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientCategoryMockMvc.perform(put("/api/client-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClientCategory in the database
        List<ClientCategory> clientCategoryList = clientCategoryRepository.findAll();
        assertThat(clientCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientCategory() throws Exception {
        // Initialize the database
        clientCategoryRepository.saveAndFlush(clientCategory);

        int databaseSizeBeforeDelete = clientCategoryRepository.findAll().size();

        // Delete the clientCategory
        restClientCategoryMockMvc.perform(delete("/api/client-categories/{id}", clientCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientCategory> clientCategoryList = clientCategoryRepository.findAll();
        assertThat(clientCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientCategory.class);
        ClientCategory clientCategory1 = new ClientCategory();
        clientCategory1.setId(1L);
        ClientCategory clientCategory2 = new ClientCategory();
        clientCategory2.setId(clientCategory1.getId());
        assertThat(clientCategory1).isEqualTo(clientCategory2);
        clientCategory2.setId(2L);
        assertThat(clientCategory1).isNotEqualTo(clientCategory2);
        clientCategory1.setId(null);
        assertThat(clientCategory1).isNotEqualTo(clientCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientCategoryDTO.class);
        ClientCategoryDTO clientCategoryDTO1 = new ClientCategoryDTO();
        clientCategoryDTO1.setId(1L);
        ClientCategoryDTO clientCategoryDTO2 = new ClientCategoryDTO();
        assertThat(clientCategoryDTO1).isNotEqualTo(clientCategoryDTO2);
        clientCategoryDTO2.setId(clientCategoryDTO1.getId());
        assertThat(clientCategoryDTO1).isEqualTo(clientCategoryDTO2);
        clientCategoryDTO2.setId(2L);
        assertThat(clientCategoryDTO1).isNotEqualTo(clientCategoryDTO2);
        clientCategoryDTO1.setId(null);
        assertThat(clientCategoryDTO1).isNotEqualTo(clientCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clientCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clientCategoryMapper.fromId(null)).isNull();
    }
}
