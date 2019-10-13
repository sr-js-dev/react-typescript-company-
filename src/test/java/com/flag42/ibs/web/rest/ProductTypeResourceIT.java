package com.flag42.ibs.web.rest;

import com.flag42.ibs.IbsApp;
import com.flag42.ibs.domain.ProductType;
import com.flag42.ibs.repository.ProductTypeRepository;
import com.flag42.ibs.service.ProductTypeService;
import com.flag42.ibs.service.dto.ProductTypeDTO;
import com.flag42.ibs.service.mapper.ProductTypeMapper;
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
 * Integration tests for the {@Link ProductTypeResource} REST controller.
 */
@SpringBootTest(classes = IbsApp.class)
public class ProductTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private ProductTypeService productTypeService;

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

    private MockMvc restProductTypeMockMvc;

    private ProductType productType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductTypeResource productTypeResource = new ProductTypeResource(productTypeService);
        this.restProductTypeMockMvc = MockMvcBuilders.standaloneSetup(productTypeResource)
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
    public static ProductType createEntity(EntityManager em) {
        ProductType productType = new ProductType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return productType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductType createUpdatedEntity(EntityManager em) {
        ProductType productType = new ProductType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return productType;
    }

    @BeforeEach
    public void initTest() {
        productType = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductType() throws Exception {
        int databaseSizeBeforeCreate = productTypeRepository.findAll().size();

        // Create the ProductType
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);
        restProductTypeMockMvc.perform(post("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductType testProductType = productTypeList.get(productTypeList.size() - 1);
        assertThat(testProductType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProductTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productTypeRepository.findAll().size();

        // Create the ProductType with an existing ID
        productType.setId(1L);
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTypeMockMvc.perform(post("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productTypeRepository.findAll().size();
        // set the field null
        productType.setName(null);

        // Create the ProductType, which fails.
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);

        restProductTypeMockMvc.perform(post("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductTypes() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList
        restProductTypeMockMvc.perform(get("/api/product-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProductType() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get the productType
        restProductTypeMockMvc.perform(get("/api/product-types/{id}", productType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductType() throws Exception {
        // Get the productType
        restProductTypeMockMvc.perform(get("/api/product-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductType() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();

        // Update the productType
        ProductType updatedProductType = productTypeRepository.findById(productType.getId()).get();
        // Disconnect from session so that the updates on updatedProductType are not directly saved in db
        em.detach(updatedProductType);
        updatedProductType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(updatedProductType);

        restProductTypeMockMvc.perform(put("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);
        ProductType testProductType = productTypeList.get(productTypeList.size() - 1);
        assertThat(testProductType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProductType() throws Exception {
        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();

        // Create the ProductType
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTypeMockMvc.perform(put("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductType() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        int databaseSizeBeforeDelete = productTypeRepository.findAll().size();

        // Delete the productType
        restProductTypeMockMvc.perform(delete("/api/product-types/{id}", productType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductType.class);
        ProductType productType1 = new ProductType();
        productType1.setId(1L);
        ProductType productType2 = new ProductType();
        productType2.setId(productType1.getId());
        assertThat(productType1).isEqualTo(productType2);
        productType2.setId(2L);
        assertThat(productType1).isNotEqualTo(productType2);
        productType1.setId(null);
        assertThat(productType1).isNotEqualTo(productType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTypeDTO.class);
        ProductTypeDTO productTypeDTO1 = new ProductTypeDTO();
        productTypeDTO1.setId(1L);
        ProductTypeDTO productTypeDTO2 = new ProductTypeDTO();
        assertThat(productTypeDTO1).isNotEqualTo(productTypeDTO2);
        productTypeDTO2.setId(productTypeDTO1.getId());
        assertThat(productTypeDTO1).isEqualTo(productTypeDTO2);
        productTypeDTO2.setId(2L);
        assertThat(productTypeDTO1).isNotEqualTo(productTypeDTO2);
        productTypeDTO1.setId(null);
        assertThat(productTypeDTO1).isNotEqualTo(productTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productTypeMapper.fromId(null)).isNull();
    }
}
