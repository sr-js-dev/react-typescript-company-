package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.CoverType;
import com.flag42.ibs.domain.RiskClass;
import com.flag42.ibs.repository.CoverTypeRepository;
import com.flag42.ibs.service.CoverTypeService;
import com.flag42.ibs.service.dto.CoverTypeDTO;
import com.flag42.ibs.service.mapper.CoverTypeMapper;
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
 * Integration tests for the {@Link CoverTypeResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class CoverTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_BROKER_COMMISSION = 1D;
    private static final Double UPDATED_BROKER_COMMISSION = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CoverTypeRepository coverTypeRepository;

    @Autowired
    private CoverTypeMapper coverTypeMapper;

    @Autowired
    private CoverTypeService coverTypeService;

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

    private MockMvc restCoverTypeMockMvc;

    private CoverType coverType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoverTypeResource coverTypeResource = new CoverTypeResource(coverTypeService);
        this.restCoverTypeMockMvc = MockMvcBuilders.standaloneSetup(coverTypeResource)
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
    public static CoverType createEntity(EntityManager em) {
        CoverType coverType = new CoverType()
            .name(DEFAULT_NAME)
            .brokerCommission(DEFAULT_BROKER_COMMISSION)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        RiskClass riskClass;
        if (TestUtil.findAll(em, RiskClass.class).isEmpty()) {
            riskClass = RiskClassResourceIT.createEntity(em);
            em.persist(riskClass);
            em.flush();
        } else {
            riskClass = TestUtil.findAll(em, RiskClass.class).get(0);
        }
        coverType.setRiskClass(riskClass);
        return coverType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoverType createUpdatedEntity(EntityManager em) {
        CoverType coverType = new CoverType()
            .name(UPDATED_NAME)
            .brokerCommission(UPDATED_BROKER_COMMISSION)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        RiskClass riskClass;
        if (TestUtil.findAll(em, RiskClass.class).isEmpty()) {
            riskClass = RiskClassResourceIT.createUpdatedEntity(em);
            em.persist(riskClass);
            em.flush();
        } else {
            riskClass = TestUtil.findAll(em, RiskClass.class).get(0);
        }
        coverType.setRiskClass(riskClass);
        return coverType;
    }

    @BeforeEach
    public void initTest() {
        coverType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoverType() throws Exception {
        int databaseSizeBeforeCreate = coverTypeRepository.findAll().size();

        // Create the CoverType
        CoverTypeDTO coverTypeDTO = coverTypeMapper.toDto(coverType);
        restCoverTypeMockMvc.perform(post("/api/cover-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coverTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CoverType in the database
        List<CoverType> coverTypeList = coverTypeRepository.findAll();
        assertThat(coverTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CoverType testCoverType = coverTypeList.get(coverTypeList.size() - 1);
        assertThat(testCoverType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCoverType.getBrokerCommission()).isEqualTo(DEFAULT_BROKER_COMMISSION);
        assertThat(testCoverType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCoverTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coverTypeRepository.findAll().size();

        // Create the CoverType with an existing ID
        coverType.setId(1L);
        CoverTypeDTO coverTypeDTO = coverTypeMapper.toDto(coverType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoverTypeMockMvc.perform(post("/api/cover-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coverTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoverType in the database
        List<CoverType> coverTypeList = coverTypeRepository.findAll();
        assertThat(coverTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = coverTypeRepository.findAll().size();
        // set the field null
        coverType.setName(null);

        // Create the CoverType, which fails.
        CoverTypeDTO coverTypeDTO = coverTypeMapper.toDto(coverType);

        restCoverTypeMockMvc.perform(post("/api/cover-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coverTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CoverType> coverTypeList = coverTypeRepository.findAll();
        assertThat(coverTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrokerCommissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = coverTypeRepository.findAll().size();
        // set the field null
        coverType.setBrokerCommission(null);

        // Create the CoverType, which fails.
        CoverTypeDTO coverTypeDTO = coverTypeMapper.toDto(coverType);

        restCoverTypeMockMvc.perform(post("/api/cover-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coverTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CoverType> coverTypeList = coverTypeRepository.findAll();
        assertThat(coverTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoverTypes() throws Exception {
        // Initialize the database
        coverTypeRepository.saveAndFlush(coverType);

        // Get all the coverTypeList
        restCoverTypeMockMvc.perform(get("/api/cover-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coverType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].brokerCommission").value(hasItem(DEFAULT_BROKER_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getCoverType() throws Exception {
        // Initialize the database
        coverTypeRepository.saveAndFlush(coverType);

        // Get the coverType
        restCoverTypeMockMvc.perform(get("/api/cover-types/{id}", coverType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coverType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.brokerCommission").value(DEFAULT_BROKER_COMMISSION.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCoverType() throws Exception {
        // Get the coverType
        restCoverTypeMockMvc.perform(get("/api/cover-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoverType() throws Exception {
        // Initialize the database
        coverTypeRepository.saveAndFlush(coverType);

        int databaseSizeBeforeUpdate = coverTypeRepository.findAll().size();

        // Update the coverType
        CoverType updatedCoverType = coverTypeRepository.findById(coverType.getId()).get();
        // Disconnect from session so that the updates on updatedCoverType are not directly saved in db
        em.detach(updatedCoverType);
        updatedCoverType
            .name(UPDATED_NAME)
            .brokerCommission(UPDATED_BROKER_COMMISSION)
            .description(UPDATED_DESCRIPTION);
        CoverTypeDTO coverTypeDTO = coverTypeMapper.toDto(updatedCoverType);

        restCoverTypeMockMvc.perform(put("/api/cover-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coverTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CoverType in the database
        List<CoverType> coverTypeList = coverTypeRepository.findAll();
        assertThat(coverTypeList).hasSize(databaseSizeBeforeUpdate);
        CoverType testCoverType = coverTypeList.get(coverTypeList.size() - 1);
        assertThat(testCoverType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCoverType.getBrokerCommission()).isEqualTo(UPDATED_BROKER_COMMISSION);
        assertThat(testCoverType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCoverType() throws Exception {
        int databaseSizeBeforeUpdate = coverTypeRepository.findAll().size();

        // Create the CoverType
        CoverTypeDTO coverTypeDTO = coverTypeMapper.toDto(coverType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoverTypeMockMvc.perform(put("/api/cover-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coverTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoverType in the database
        List<CoverType> coverTypeList = coverTypeRepository.findAll();
        assertThat(coverTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoverType() throws Exception {
        // Initialize the database
        coverTypeRepository.saveAndFlush(coverType);

        int databaseSizeBeforeDelete = coverTypeRepository.findAll().size();

        // Delete the coverType
        restCoverTypeMockMvc.perform(delete("/api/cover-types/{id}", coverType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CoverType> coverTypeList = coverTypeRepository.findAll();
        assertThat(coverTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoverType.class);
        CoverType coverType1 = new CoverType();
        coverType1.setId(1L);
        CoverType coverType2 = new CoverType();
        coverType2.setId(coverType1.getId());
        assertThat(coverType1).isEqualTo(coverType2);
        coverType2.setId(2L);
        assertThat(coverType1).isNotEqualTo(coverType2);
        coverType1.setId(null);
        assertThat(coverType1).isNotEqualTo(coverType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoverTypeDTO.class);
        CoverTypeDTO coverTypeDTO1 = new CoverTypeDTO();
        coverTypeDTO1.setId(1L);
        CoverTypeDTO coverTypeDTO2 = new CoverTypeDTO();
        assertThat(coverTypeDTO1).isNotEqualTo(coverTypeDTO2);
        coverTypeDTO2.setId(coverTypeDTO1.getId());
        assertThat(coverTypeDTO1).isEqualTo(coverTypeDTO2);
        coverTypeDTO2.setId(2L);
        assertThat(coverTypeDTO1).isNotEqualTo(coverTypeDTO2);
        coverTypeDTO1.setId(null);
        assertThat(coverTypeDTO1).isNotEqualTo(coverTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coverTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coverTypeMapper.fromId(null)).isNull();
    }
}
