package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.Benefit;
import com.flag42.ibs.repository.BenefitRepository;
import com.flag42.ibs.service.BenefitService;
import com.flag42.ibs.service.dto.BenefitDTO;
import com.flag42.ibs.service.mapper.BenefitMapper;
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

import com.flag42.ibs.domain.enumeration.BenefitRate;
/**
 * Integration tests for the {@Link BenefitResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class BenefitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BenefitRate DEFAULT_BENEFIT_RATE = BenefitRate.FIXED;
    private static final BenefitRate UPDATED_BENEFIT_RATE = BenefitRate.PERCENTAGE;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private BenefitRepository benefitRepository;

    @Autowired
    private BenefitMapper benefitMapper;

    @Autowired
    private BenefitService benefitService;

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

    private MockMvc restBenefitMockMvc;

    private Benefit benefit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BenefitResource benefitResource = new BenefitResource(benefitService);
        this.restBenefitMockMvc = MockMvcBuilders.standaloneSetup(benefitResource)
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
    public static Benefit createEntity(EntityManager em) {
        Benefit benefit = new Benefit()
            .name(DEFAULT_NAME)
            .benefitRate(DEFAULT_BENEFIT_RATE)
            .description(DEFAULT_DESCRIPTION);
        return benefit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Benefit createUpdatedEntity(EntityManager em) {
        Benefit benefit = new Benefit()
            .name(UPDATED_NAME)
            .benefitRate(UPDATED_BENEFIT_RATE)
            .description(UPDATED_DESCRIPTION);
        return benefit;
    }

    @BeforeEach
    public void initTest() {
        benefit = createEntity(em);
    }

    @Test
    @Transactional
    public void createBenefit() throws Exception {
        int databaseSizeBeforeCreate = benefitRepository.findAll().size();

        // Create the Benefit
        BenefitDTO benefitDTO = benefitMapper.toDto(benefit);
        restBenefitMockMvc.perform(post("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitDTO)))
            .andExpect(status().isCreated());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeCreate + 1);
        Benefit testBenefit = benefitList.get(benefitList.size() - 1);
        assertThat(testBenefit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBenefit.getBenefitRate()).isEqualTo(DEFAULT_BENEFIT_RATE);
        assertThat(testBenefit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createBenefitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = benefitRepository.findAll().size();

        // Create the Benefit with an existing ID
        benefit.setId(1L);
        BenefitDTO benefitDTO = benefitMapper.toDto(benefit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBenefitMockMvc.perform(post("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = benefitRepository.findAll().size();
        // set the field null
        benefit.setName(null);

        // Create the Benefit, which fails.
        BenefitDTO benefitDTO = benefitMapper.toDto(benefit);

        restBenefitMockMvc.perform(post("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitDTO)))
            .andExpect(status().isBadRequest());

        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBenefits() throws Exception {
        // Initialize the database
        benefitRepository.saveAndFlush(benefit);

        // Get all the benefitList
        restBenefitMockMvc.perform(get("/api/benefits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(benefit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].benefitRate").value(hasItem(DEFAULT_BENEFIT_RATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getBenefit() throws Exception {
        // Initialize the database
        benefitRepository.saveAndFlush(benefit);

        // Get the benefit
        restBenefitMockMvc.perform(get("/api/benefits/{id}", benefit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(benefit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.benefitRate").value(DEFAULT_BENEFIT_RATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBenefit() throws Exception {
        // Get the benefit
        restBenefitMockMvc.perform(get("/api/benefits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBenefit() throws Exception {
        // Initialize the database
        benefitRepository.saveAndFlush(benefit);

        int databaseSizeBeforeUpdate = benefitRepository.findAll().size();

        // Update the benefit
        Benefit updatedBenefit = benefitRepository.findById(benefit.getId()).get();
        // Disconnect from session so that the updates on updatedBenefit are not directly saved in db
        em.detach(updatedBenefit);
        updatedBenefit
            .name(UPDATED_NAME)
            .benefitRate(UPDATED_BENEFIT_RATE)
            .description(UPDATED_DESCRIPTION);
        BenefitDTO benefitDTO = benefitMapper.toDto(updatedBenefit);

        restBenefitMockMvc.perform(put("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitDTO)))
            .andExpect(status().isOk());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeUpdate);
        Benefit testBenefit = benefitList.get(benefitList.size() - 1);
        assertThat(testBenefit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBenefit.getBenefitRate()).isEqualTo(UPDATED_BENEFIT_RATE);
        assertThat(testBenefit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingBenefit() throws Exception {
        int databaseSizeBeforeUpdate = benefitRepository.findAll().size();

        // Create the Benefit
        BenefitDTO benefitDTO = benefitMapper.toDto(benefit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBenefitMockMvc.perform(put("/api/benefits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(benefitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Benefit in the database
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBenefit() throws Exception {
        // Initialize the database
        benefitRepository.saveAndFlush(benefit);

        int databaseSizeBeforeDelete = benefitRepository.findAll().size();

        // Delete the benefit
        restBenefitMockMvc.perform(delete("/api/benefits/{id}", benefit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Benefit> benefitList = benefitRepository.findAll();
        assertThat(benefitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Benefit.class);
        Benefit benefit1 = new Benefit();
        benefit1.setId(1L);
        Benefit benefit2 = new Benefit();
        benefit2.setId(benefit1.getId());
        assertThat(benefit1).isEqualTo(benefit2);
        benefit2.setId(2L);
        assertThat(benefit1).isNotEqualTo(benefit2);
        benefit1.setId(null);
        assertThat(benefit1).isNotEqualTo(benefit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BenefitDTO.class);
        BenefitDTO benefitDTO1 = new BenefitDTO();
        benefitDTO1.setId(1L);
        BenefitDTO benefitDTO2 = new BenefitDTO();
        assertThat(benefitDTO1).isNotEqualTo(benefitDTO2);
        benefitDTO2.setId(benefitDTO1.getId());
        assertThat(benefitDTO1).isEqualTo(benefitDTO2);
        benefitDTO2.setId(2L);
        assertThat(benefitDTO1).isNotEqualTo(benefitDTO2);
        benefitDTO1.setId(null);
        assertThat(benefitDTO1).isNotEqualTo(benefitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(benefitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(benefitMapper.fromId(null)).isNull();
    }
}
