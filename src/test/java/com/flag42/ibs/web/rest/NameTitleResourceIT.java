package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.NameTitle;
import com.flag42.ibs.repository.NameTitleRepository;
import com.flag42.ibs.service.NameTitleService;
import com.flag42.ibs.service.dto.NameTitleDTO;
import com.flag42.ibs.service.mapper.NameTitleMapper;
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
 * Integration tests for the {@Link NameTitleResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class NameTitleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private NameTitleRepository nameTitleRepository;

    @Autowired
    private NameTitleMapper nameTitleMapper;

    @Autowired
    private NameTitleService nameTitleService;

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

    private MockMvc restNameTitleMockMvc;

    private NameTitle nameTitle;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NameTitleResource nameTitleResource = new NameTitleResource(nameTitleService);
        this.restNameTitleMockMvc = MockMvcBuilders.standaloneSetup(nameTitleResource)
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
    public static NameTitle createEntity(EntityManager em) {
        NameTitle nameTitle = new NameTitle()
            .name(DEFAULT_NAME);
        return nameTitle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NameTitle createUpdatedEntity(EntityManager em) {
        NameTitle nameTitle = new NameTitle()
            .name(UPDATED_NAME);
        return nameTitle;
    }

    @BeforeEach
    public void initTest() {
        nameTitle = createEntity(em);
    }

    @Test
    @Transactional
    public void createNameTitle() throws Exception {
        int databaseSizeBeforeCreate = nameTitleRepository.findAll().size();

        // Create the NameTitle
        NameTitleDTO nameTitleDTO = nameTitleMapper.toDto(nameTitle);
        restNameTitleMockMvc.perform(post("/api/name-titles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nameTitleDTO)))
            .andExpect(status().isCreated());

        // Validate the NameTitle in the database
        List<NameTitle> nameTitleList = nameTitleRepository.findAll();
        assertThat(nameTitleList).hasSize(databaseSizeBeforeCreate + 1);
        NameTitle testNameTitle = nameTitleList.get(nameTitleList.size() - 1);
        assertThat(testNameTitle.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createNameTitleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nameTitleRepository.findAll().size();

        // Create the NameTitle with an existing ID
        nameTitle.setId(1L);
        NameTitleDTO nameTitleDTO = nameTitleMapper.toDto(nameTitle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNameTitleMockMvc.perform(post("/api/name-titles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nameTitleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NameTitle in the database
        List<NameTitle> nameTitleList = nameTitleRepository.findAll();
        assertThat(nameTitleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nameTitleRepository.findAll().size();
        // set the field null
        nameTitle.setName(null);

        // Create the NameTitle, which fails.
        NameTitleDTO nameTitleDTO = nameTitleMapper.toDto(nameTitle);

        restNameTitleMockMvc.perform(post("/api/name-titles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nameTitleDTO)))
            .andExpect(status().isBadRequest());

        List<NameTitle> nameTitleList = nameTitleRepository.findAll();
        assertThat(nameTitleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNameTitles() throws Exception {
        // Initialize the database
        nameTitleRepository.saveAndFlush(nameTitle);

        // Get all the nameTitleList
        restNameTitleMockMvc.perform(get("/api/name-titles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nameTitle.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getNameTitle() throws Exception {
        // Initialize the database
        nameTitleRepository.saveAndFlush(nameTitle);

        // Get the nameTitle
        restNameTitleMockMvc.perform(get("/api/name-titles/{id}", nameTitle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nameTitle.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNameTitle() throws Exception {
        // Get the nameTitle
        restNameTitleMockMvc.perform(get("/api/name-titles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNameTitle() throws Exception {
        // Initialize the database
        nameTitleRepository.saveAndFlush(nameTitle);

        int databaseSizeBeforeUpdate = nameTitleRepository.findAll().size();

        // Update the nameTitle
        NameTitle updatedNameTitle = nameTitleRepository.findById(nameTitle.getId()).get();
        // Disconnect from session so that the updates on updatedNameTitle are not directly saved in db
        em.detach(updatedNameTitle);
        updatedNameTitle
            .name(UPDATED_NAME);
        NameTitleDTO nameTitleDTO = nameTitleMapper.toDto(updatedNameTitle);

        restNameTitleMockMvc.perform(put("/api/name-titles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nameTitleDTO)))
            .andExpect(status().isOk());

        // Validate the NameTitle in the database
        List<NameTitle> nameTitleList = nameTitleRepository.findAll();
        assertThat(nameTitleList).hasSize(databaseSizeBeforeUpdate);
        NameTitle testNameTitle = nameTitleList.get(nameTitleList.size() - 1);
        assertThat(testNameTitle.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNameTitle() throws Exception {
        int databaseSizeBeforeUpdate = nameTitleRepository.findAll().size();

        // Create the NameTitle
        NameTitleDTO nameTitleDTO = nameTitleMapper.toDto(nameTitle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNameTitleMockMvc.perform(put("/api/name-titles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nameTitleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NameTitle in the database
        List<NameTitle> nameTitleList = nameTitleRepository.findAll();
        assertThat(nameTitleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNameTitle() throws Exception {
        // Initialize the database
        nameTitleRepository.saveAndFlush(nameTitle);

        int databaseSizeBeforeDelete = nameTitleRepository.findAll().size();

        // Delete the nameTitle
        restNameTitleMockMvc.perform(delete("/api/name-titles/{id}", nameTitle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NameTitle> nameTitleList = nameTitleRepository.findAll();
        assertThat(nameTitleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NameTitle.class);
        NameTitle nameTitle1 = new NameTitle();
        nameTitle1.setId(1L);
        NameTitle nameTitle2 = new NameTitle();
        nameTitle2.setId(nameTitle1.getId());
        assertThat(nameTitle1).isEqualTo(nameTitle2);
        nameTitle2.setId(2L);
        assertThat(nameTitle1).isNotEqualTo(nameTitle2);
        nameTitle1.setId(null);
        assertThat(nameTitle1).isNotEqualTo(nameTitle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NameTitleDTO.class);
        NameTitleDTO nameTitleDTO1 = new NameTitleDTO();
        nameTitleDTO1.setId(1L);
        NameTitleDTO nameTitleDTO2 = new NameTitleDTO();
        assertThat(nameTitleDTO1).isNotEqualTo(nameTitleDTO2);
        nameTitleDTO2.setId(nameTitleDTO1.getId());
        assertThat(nameTitleDTO1).isEqualTo(nameTitleDTO2);
        nameTitleDTO2.setId(2L);
        assertThat(nameTitleDTO1).isNotEqualTo(nameTitleDTO2);
        nameTitleDTO1.setId(null);
        assertThat(nameTitleDTO1).isNotEqualTo(nameTitleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(nameTitleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(nameTitleMapper.fromId(null)).isNull();
    }
}
