package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.BenefitType;
import com.flag42.ibs.repository.BenefitTypeRepository;
import com.flag42.ibs.service.BenefitTypeService;
import com.flag42.ibs.service.dto.BenefitTypeDTO;
import com.flag42.ibs.service.mapper.BenefitTypeMapper;
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
 * Integration tests for the {@Link BenefitTypeResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class BenefitTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private BenefitTypeRepository benefitTypeRepository;

    @Autowired
    private BenefitTypeMapper benefitTypeMapper;

    @Autowired
    private BenefitTypeService benefitTypeService;

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

    private MockMvc restBenefitTypeMockMvc;

    private BenefitType benefitType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BenefitTypeResource benefitTypeResource = new BenefitTypeResource(benefitTypeService);
        this.restBenefitTypeMockMvc = MockMvcBuilders.standaloneSetup(benefitTypeResource)
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
    public static BenefitType createEntity(EntityManager em) {
        BenefitType benefitType = new BenefitType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return benefitType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BenefitType createUpdatedEntity(EntityManager em) {
        BenefitType benefitType = new BenefitType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return benefitType;
    }

    @BeforeEach
    public void initTest() {
        benefitType = createEntity(em);
    }

    @Test
    @Transactional
    public void createBenefitType() throws Exception {
        int databaseSizeBeforeCreate = benefitTypeRepository.findAll().size();

        // Create the BenefitType
        BenefitTypeDTO benefitTypeDTO = benefitTypeMapper.toDto(benefitType);
        restBenefitTypeMockMvc.perform(post("/api/benefit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BenefitType testBenefitType = benefitTypeList.get(benefitTypeList.size() - 1);
        assertThat(testBenefitType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBenefitType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createBenefitTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = benefitTypeRepository.findAll().size();

        // Create the BenefitType with an existing ID
        benefitType.setId(1L);
        BenefitTypeDTO benefitTypeDTO = benefitTypeMapper.toDto(benefitType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenefitTypeMockMvc.perform(post("/api/benefit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitTypeRepository.findAll().size();
        // set the field null
        benefitType.setName(null);

        // Create the BenefitType, which fails.
        BenefitTypeDTO benefitTypeDTO = benefitTypeMapper.toDto(benefitType);

        restBenefitTypeMockMvc.perform(post("/api/benefit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitTypeDTO)))
            .andExpect(status().isBadRequest());

        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBenefitTypes() throws Exception {
        // Initialize the database
        benefitTypeRepository.saveAndFlush(benefitType);

        // Get all the benefitTypeList
        restBenefitTypeMockMvc.perform(get("/api/benefit-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benefitType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getBenefitType() throws Exception {
        // Initialize the database
        benefitTypeRepository.saveAndFlush(benefitType);

        // Get the benefitType
        restBenefitTypeMockMvc.perform(get("/api/benefit-types/{id}", benefitType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(benefitType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBenefitType() throws Exception {
        // Get the benefitType
        restBenefitTypeMockMvc.perform(get("/api/benefit-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBenefitType() throws Exception {
        // Initialize the database
        benefitTypeRepository.saveAndFlush(benefitType);

        int databaseSizeBeforeUpdate = benefitTypeRepository.findAll().size();

        // Update the benefitType
        BenefitType updatedBenefitType = benefitTypeRepository.findById(benefitType.getId()).get();
        // Disconnect from session so that the updates on updatedBenefitType are not directly saved in db
        em.detach(updatedBenefitType);
        updatedBenefitType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        BenefitTypeDTO benefitTypeDTO = benefitTypeMapper.toDto(updatedBenefitType);

        restBenefitTypeMockMvc.perform(put("/api/benefit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitTypeDTO)))
            .andExpect(status().isOk());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeUpdate);
        BenefitType testBenefitType = benefitTypeList.get(benefitTypeList.size() - 1);
        assertThat(testBenefitType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenefitType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingBenefitType() throws Exception {
        int databaseSizeBeforeUpdate = benefitTypeRepository.findAll().size();

        // Create the BenefitType
        BenefitTypeDTO benefitTypeDTO = benefitTypeMapper.toDto(benefitType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitTypeMockMvc.perform(put("/api/benefit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BenefitType in the database
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBenefitType() throws Exception {
        // Initialize the database
        benefitTypeRepository.saveAndFlush(benefitType);

        int databaseSizeBeforeDelete = benefitTypeRepository.findAll().size();

        // Delete the benefitType
        restBenefitTypeMockMvc.perform(delete("/api/benefit-types/{id}", benefitType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BenefitType> benefitTypeList = benefitTypeRepository.findAll();
        assertThat(benefitTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BenefitType.class);
        BenefitType benefitType1 = new BenefitType();
        benefitType1.setId(1L);
        BenefitType benefitType2 = new BenefitType();
        benefitType2.setId(benefitType1.getId());
        assertThat(benefitType1).isEqualTo(benefitType2);
        benefitType2.setId(2L);
        assertThat(benefitType1).isNotEqualTo(benefitType2);
        benefitType1.setId(null);
        assertThat(benefitType1).isNotEqualTo(benefitType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BenefitTypeDTO.class);
        BenefitTypeDTO benefitTypeDTO1 = new BenefitTypeDTO();
        benefitTypeDTO1.setId(1L);
        BenefitTypeDTO benefitTypeDTO2 = new BenefitTypeDTO();
        assertThat(benefitTypeDTO1).isNotEqualTo(benefitTypeDTO2);
        benefitTypeDTO2.setId(benefitTypeDTO1.getId());
        assertThat(benefitTypeDTO1).isEqualTo(benefitTypeDTO2);
        benefitTypeDTO2.setId(2L);
        assertThat(benefitTypeDTO1).isNotEqualTo(benefitTypeDTO2);
        benefitTypeDTO1.setId(null);
        assertThat(benefitTypeDTO1).isNotEqualTo(benefitTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(benefitTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(benefitTypeMapper.fromId(null)).isNull();
    }
}
