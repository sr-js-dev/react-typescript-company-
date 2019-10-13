package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.RiskCategory;
import com.flag42.ibs.domain.ProductType;
import com.flag42.ibs.repository.RiskCategoryRepository;
import com.flag42.ibs.service.RiskCategoryService;
import com.flag42.ibs.service.dto.RiskCategoryDTO;
import com.flag42.ibs.service.mapper.RiskCategoryMapper;
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
 * Integration tests for the {@Link RiskCategoryResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class RiskCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RiskCategoryRepository riskCategoryRepository;

    @Autowired
    private RiskCategoryMapper riskCategoryMapper;

    @Autowired
    private RiskCategoryService riskCategoryService;

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

    private MockMvc restRiskCategoryMockMvc;

    private RiskCategory riskCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RiskCategoryResource riskCategoryResource = new RiskCategoryResource(riskCategoryService);
        this.restRiskCategoryMockMvc = MockMvcBuilders.standaloneSetup(riskCategoryResource)
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
    public static RiskCategory createEntity(EntityManager em) {
        RiskCategory riskCategory = new RiskCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        ProductType productType;
        if (TestUtil.findAll(em, ProductType.class).isEmpty()) {
            productType = ProductTypeResourceIT.createEntity(em);
            em.persist(productType);
            em.flush();
        } else {
            productType = TestUtil.findAll(em, ProductType.class).get(0);
        }
        riskCategory.setProductType(productType);
        return riskCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RiskCategory createUpdatedEntity(EntityManager em) {
        RiskCategory riskCategory = new RiskCategory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        ProductType productType;
        if (TestUtil.findAll(em, ProductType.class).isEmpty()) {
            productType = ProductTypeResourceIT.createUpdatedEntity(em);
            em.persist(productType);
            em.flush();
        } else {
            productType = TestUtil.findAll(em, ProductType.class).get(0);
        }
        riskCategory.setProductType(productType);
        return riskCategory;
    }

    @BeforeEach
    public void initTest() {
        riskCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createRiskCategory() throws Exception {
        int databaseSizeBeforeCreate = riskCategoryRepository.findAll().size();

        // Create the RiskCategory
        RiskCategoryDTO riskCategoryDTO = riskCategoryMapper.toDto(riskCategory);
        restRiskCategoryMockMvc.perform(post("/api/risk-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the RiskCategory in the database
        List<RiskCategory> riskCategoryList = riskCategoryRepository.findAll();
        assertThat(riskCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        RiskCategory testRiskCategory = riskCategoryList.get(riskCategoryList.size() - 1);
        assertThat(testRiskCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRiskCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRiskCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = riskCategoryRepository.findAll().size();

        // Create the RiskCategory with an existing ID
        riskCategory.setId(1L);
        RiskCategoryDTO riskCategoryDTO = riskCategoryMapper.toDto(riskCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiskCategoryMockMvc.perform(post("/api/risk-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RiskCategory in the database
        List<RiskCategory> riskCategoryList = riskCategoryRepository.findAll();
        assertThat(riskCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = riskCategoryRepository.findAll().size();
        // set the field null
        riskCategory.setName(null);

        // Create the RiskCategory, which fails.
        RiskCategoryDTO riskCategoryDTO = riskCategoryMapper.toDto(riskCategory);

        restRiskCategoryMockMvc.perform(post("/api/risk-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<RiskCategory> riskCategoryList = riskCategoryRepository.findAll();
        assertThat(riskCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRiskCategories() throws Exception {
        // Initialize the database
        riskCategoryRepository.saveAndFlush(riskCategory);

        // Get all the riskCategoryList
        restRiskCategoryMockMvc.perform(get("/api/risk-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(riskCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getRiskCategory() throws Exception {
        // Initialize the database
        riskCategoryRepository.saveAndFlush(riskCategory);

        // Get the riskCategory
        restRiskCategoryMockMvc.perform(get("/api/risk-categories/{id}", riskCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(riskCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRiskCategory() throws Exception {
        // Get the riskCategory
        restRiskCategoryMockMvc.perform(get("/api/risk-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRiskCategory() throws Exception {
        // Initialize the database
        riskCategoryRepository.saveAndFlush(riskCategory);

        int databaseSizeBeforeUpdate = riskCategoryRepository.findAll().size();

        // Update the riskCategory
        RiskCategory updatedRiskCategory = riskCategoryRepository.findById(riskCategory.getId()).get();
        // Disconnect from session so that the updates on updatedRiskCategory are not directly saved in db
        em.detach(updatedRiskCategory);
        updatedRiskCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        RiskCategoryDTO riskCategoryDTO = riskCategoryMapper.toDto(updatedRiskCategory);

        restRiskCategoryMockMvc.perform(put("/api/risk-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the RiskCategory in the database
        List<RiskCategory> riskCategoryList = riskCategoryRepository.findAll();
        assertThat(riskCategoryList).hasSize(databaseSizeBeforeUpdate);
        RiskCategory testRiskCategory = riskCategoryList.get(riskCategoryList.size() - 1);
        assertThat(testRiskCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRiskCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRiskCategory() throws Exception {
        int databaseSizeBeforeUpdate = riskCategoryRepository.findAll().size();

        // Create the RiskCategory
        RiskCategoryDTO riskCategoryDTO = riskCategoryMapper.toDto(riskCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiskCategoryMockMvc.perform(put("/api/risk-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riskCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RiskCategory in the database
        List<RiskCategory> riskCategoryList = riskCategoryRepository.findAll();
        assertThat(riskCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRiskCategory() throws Exception {
        // Initialize the database
        riskCategoryRepository.saveAndFlush(riskCategory);

        int databaseSizeBeforeDelete = riskCategoryRepository.findAll().size();

        // Delete the riskCategory
        restRiskCategoryMockMvc.perform(delete("/api/risk-categories/{id}", riskCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RiskCategory> riskCategoryList = riskCategoryRepository.findAll();
        assertThat(riskCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskCategory.class);
        RiskCategory riskCategory1 = new RiskCategory();
        riskCategory1.setId(1L);
        RiskCategory riskCategory2 = new RiskCategory();
        riskCategory2.setId(riskCategory1.getId());
        assertThat(riskCategory1).isEqualTo(riskCategory2);
        riskCategory2.setId(2L);
        assertThat(riskCategory1).isNotEqualTo(riskCategory2);
        riskCategory1.setId(null);
        assertThat(riskCategory1).isNotEqualTo(riskCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiskCategoryDTO.class);
        RiskCategoryDTO riskCategoryDTO1 = new RiskCategoryDTO();
        riskCategoryDTO1.setId(1L);
        RiskCategoryDTO riskCategoryDTO2 = new RiskCategoryDTO();
        assertThat(riskCategoryDTO1).isNotEqualTo(riskCategoryDTO2);
        riskCategoryDTO2.setId(riskCategoryDTO1.getId());
        assertThat(riskCategoryDTO1).isEqualTo(riskCategoryDTO2);
        riskCategoryDTO2.setId(2L);
        assertThat(riskCategoryDTO1).isNotEqualTo(riskCategoryDTO2);
        riskCategoryDTO1.setId(null);
        assertThat(riskCategoryDTO1).isNotEqualTo(riskCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(riskCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(riskCategoryMapper.fromId(null)).isNull();
    }
}
