package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.RiskClass;
import com.flag42.ibs.domain.RiskCategory;
import com.flag42.ibs.repository.RiskClassRepository;
import com.flag42.ibs.service.RiskClassService;
import com.flag42.ibs.service.dto.RiskClassDTO;
import com.flag42.ibs.service.mapper.RiskClassMapper;
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
 * Integration tests for the {@Link RiskClassResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class RiskClassResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RiskClassRepository riskClassRepository;

    @Autowired
    private RiskClassMapper riskClassMapper;

    @Autowired
    private RiskClassService riskClassService;

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

    private MockMvc restRiskClassMockMvc;

    private RiskClass riskClass;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RiskClassResource riskClassResource = new RiskClassResource(riskClassService);
        this.restRiskClassMockMvc = MockMvcBuilders.standaloneSetup(riskClassResource)
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
    public static RiskClass createEntity(EntityManager em) {
        RiskClass riskClass = new RiskClass()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        RiskCategory riskCategory;
        if (TestUtil.findAll(em, RiskCategory.class).isEmpty()) {
            riskCategory = RiskCategoryResourceIT.createEntity(em);
            em.persist(riskCategory);
            em.flush();
        } else {
            riskCategory = TestUtil.findAll(em, RiskCategory.class).get(0);
        }
        riskClass.setRiskCategory(riskCategory);
        return riskClass;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RiskClass createUpdatedEntity(EntityManager em) {
        RiskClass riskClass = new RiskClass()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        RiskCategory riskCategory;
        if (TestUtil.findAll(em, RiskCategory.class).isEmpty()) {
            riskCategory = RiskCategoryResourceIT.createUpdatedEntity(em);
            em.persist(riskCategory);
            em.flush();
        } else {
            riskCategory = TestUtil.findAll(em, RiskCategory.class).get(0);
        }
        riskClass.setRiskCategory(riskCategory);
        return riskClass;
    }

    @BeforeEach
    public void initTest() {
        riskClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createRiskClass() throws Exception {
        int databaseSizeBeforeCreate = riskClassRepository.findAll().size();

        // Create the RiskClass
        RiskClassDTO riskClassDTO = riskClassMapper.toDto(riskClass);
        restRiskClassMockMvc.perform(post("/api/risk-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskClassDTO)))
            .andExpect(status().isCreated());

        // Validate the RiskClass in the database
        List<RiskClass> riskClassList = riskClassRepository.findAll();
        assertThat(riskClassList).hasSize(databaseSizeBeforeCreate + 1);
        RiskClass testRiskClass = riskClassList.get(riskClassList.size() - 1);
        assertThat(testRiskClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRiskClass.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRiskClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = riskClassRepository.findAll().size();

        // Create the RiskClass with an existing ID
        riskClass.setId(1L);
        RiskClassDTO riskClassDTO = riskClassMapper.toDto(riskClass);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiskClassMockMvc.perform(post("/api/risk-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RiskClass in the database
        List<RiskClass> riskClassList = riskClassRepository.findAll();
        assertThat(riskClassList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskClassRepository.findAll().size();
        // set the field null
        riskClass.setName(null);

        // Create the RiskClass, which fails.
        RiskClassDTO riskClassDTO = riskClassMapper.toDto(riskClass);

        restRiskClassMockMvc.perform(post("/api/risk-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskClassDTO)))
            .andExpect(status().isBadRequest());

        List<RiskClass> riskClassList = riskClassRepository.findAll();
        assertThat(riskClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRiskClasses() throws Exception {
        // Initialize the database
        riskClassRepository.saveAndFlush(riskClass);

        // Get all the riskClassList
        restRiskClassMockMvc.perform(get("/api/risk-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(riskClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getRiskClass() throws Exception {
        // Initialize the database
        riskClassRepository.saveAndFlush(riskClass);

        // Get the riskClass
        restRiskClassMockMvc.perform(get("/api/risk-classes/{id}", riskClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(riskClass.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRiskClass() throws Exception {
        // Get the riskClass
        restRiskClassMockMvc.perform(get("/api/risk-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRiskClass() throws Exception {
        // Initialize the database
        riskClassRepository.saveAndFlush(riskClass);

        int databaseSizeBeforeUpdate = riskClassRepository.findAll().size();

        // Update the riskClass
        RiskClass updatedRiskClass = riskClassRepository.findById(riskClass.getId()).get();
        // Disconnect from session so that the updates on updatedRiskClass are not directly saved in db
        em.detach(updatedRiskClass);
        updatedRiskClass
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        RiskClassDTO riskClassDTO = riskClassMapper.toDto(updatedRiskClass);

        restRiskClassMockMvc.perform(put("/api/risk-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskClassDTO)))
            .andExpect(status().isOk());

        // Validate the RiskClass in the database
        List<RiskClass> riskClassList = riskClassRepository.findAll();
        assertThat(riskClassList).hasSize(databaseSizeBeforeUpdate);
        RiskClass testRiskClass = riskClassList.get(riskClassList.size() - 1);
        assertThat(testRiskClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRiskClass.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRiskClass() throws Exception {
        int databaseSizeBeforeUpdate = riskClassRepository.findAll().size();

        // Create the RiskClass
        RiskClassDTO riskClassDTO = riskClassMapper.toDto(riskClass);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskClassMockMvc.perform(put("/api/risk-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RiskClass in the database
        List<RiskClass> riskClassList = riskClassRepository.findAll();
        assertThat(riskClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRiskClass() throws Exception {
        // Initialize the database
        riskClassRepository.saveAndFlush(riskClass);

        int databaseSizeBeforeDelete = riskClassRepository.findAll().size();

        // Delete the riskClass
        restRiskClassMockMvc.perform(delete("/api/risk-classes/{id}", riskClass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RiskClass> riskClassList = riskClassRepository.findAll();
        assertThat(riskClassList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskClass.class);
        RiskClass riskClass1 = new RiskClass();
        riskClass1.setId(1L);
        RiskClass riskClass2 = new RiskClass();
        riskClass2.setId(riskClass1.getId());
        assertThat(riskClass1).isEqualTo(riskClass2);
        riskClass2.setId(2L);
        assertThat(riskClass1).isNotEqualTo(riskClass2);
        riskClass1.setId(null);
        assertThat(riskClass1).isNotEqualTo(riskClass2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskClassDTO.class);
        RiskClassDTO riskClassDTO1 = new RiskClassDTO();
        riskClassDTO1.setId(1L);
        RiskClassDTO riskClassDTO2 = new RiskClassDTO();
        assertThat(riskClassDTO1).isNotEqualTo(riskClassDTO2);
        riskClassDTO2.setId(riskClassDTO1.getId());
        assertThat(riskClassDTO1).isEqualTo(riskClassDTO2);
        riskClassDTO2.setId(2L);
        assertThat(riskClassDTO1).isNotEqualTo(riskClassDTO2);
        riskClassDTO1.setId(null);
        assertThat(riskClassDTO1).isNotEqualTo(riskClassDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(riskClassMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(riskClassMapper.fromId(null)).isNull();
    }
}
