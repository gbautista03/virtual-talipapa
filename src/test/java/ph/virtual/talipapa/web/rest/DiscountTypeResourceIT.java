package ph.virtual.talipapa.web.rest;

import ph.virtual.talipapa.VirtualTalipapaApp;
import ph.virtual.talipapa.domain.DiscountType;
import ph.virtual.talipapa.repository.DiscountTypeRepository;
import ph.virtual.talipapa.service.DiscountTypeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DiscountTypeResource} REST controller.
 */
@SpringBootTest(classes = VirtualTalipapaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DiscountTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIER = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIER = "BBBBBBBBBB";

    @Autowired
    private DiscountTypeRepository discountTypeRepository;

    @Autowired
    private DiscountTypeService discountTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiscountTypeMockMvc;

    private DiscountType discountType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiscountType createEntity(EntityManager em) {
        DiscountType discountType = new DiscountType()
            .name(DEFAULT_NAME)
            .modifier(DEFAULT_MODIFIER);
        return discountType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiscountType createUpdatedEntity(EntityManager em) {
        DiscountType discountType = new DiscountType()
            .name(UPDATED_NAME)
            .modifier(UPDATED_MODIFIER);
        return discountType;
    }

    @BeforeEach
    public void initTest() {
        discountType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscountType() throws Exception {
        int databaseSizeBeforeCreate = discountTypeRepository.findAll().size();

        // Create the DiscountType
        restDiscountTypeMockMvc.perform(post("/api/discount-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountType)))
            .andExpect(status().isCreated());

        // Validate the DiscountType in the database
        List<DiscountType> discountTypeList = discountTypeRepository.findAll();
        assertThat(discountTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DiscountType testDiscountType = discountTypeList.get(discountTypeList.size() - 1);
        assertThat(testDiscountType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDiscountType.getModifier()).isEqualTo(DEFAULT_MODIFIER);
    }

    @Test
    @Transactional
    public void createDiscountTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discountTypeRepository.findAll().size();

        // Create the DiscountType with an existing ID
        discountType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscountTypeMockMvc.perform(post("/api/discount-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountType)))
            .andExpect(status().isBadRequest());

        // Validate the DiscountType in the database
        List<DiscountType> discountTypeList = discountTypeRepository.findAll();
        assertThat(discountTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountTypeRepository.findAll().size();
        // set the field null
        discountType.setName(null);

        // Create the DiscountType, which fails.

        restDiscountTypeMockMvc.perform(post("/api/discount-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountType)))
            .andExpect(status().isBadRequest());

        List<DiscountType> discountTypeList = discountTypeRepository.findAll();
        assertThat(discountTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiscountTypes() throws Exception {
        // Initialize the database
        discountTypeRepository.saveAndFlush(discountType);

        // Get all the discountTypeList
        restDiscountTypeMockMvc.perform(get("/api/discount-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discountType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].modifier").value(hasItem(DEFAULT_MODIFIER)));
    }
    
    @Test
    @Transactional
    public void getDiscountType() throws Exception {
        // Initialize the database
        discountTypeRepository.saveAndFlush(discountType);

        // Get the discountType
        restDiscountTypeMockMvc.perform(get("/api/discount-types/{id}", discountType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(discountType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.modifier").value(DEFAULT_MODIFIER));
    }

    @Test
    @Transactional
    public void getNonExistingDiscountType() throws Exception {
        // Get the discountType
        restDiscountTypeMockMvc.perform(get("/api/discount-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscountType() throws Exception {
        // Initialize the database
        discountTypeService.save(discountType);

        int databaseSizeBeforeUpdate = discountTypeRepository.findAll().size();

        // Update the discountType
        DiscountType updatedDiscountType = discountTypeRepository.findById(discountType.getId()).get();
        // Disconnect from session so that the updates on updatedDiscountType are not directly saved in db
        em.detach(updatedDiscountType);
        updatedDiscountType
            .name(UPDATED_NAME)
            .modifier(UPDATED_MODIFIER);

        restDiscountTypeMockMvc.perform(put("/api/discount-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiscountType)))
            .andExpect(status().isOk());

        // Validate the DiscountType in the database
        List<DiscountType> discountTypeList = discountTypeRepository.findAll();
        assertThat(discountTypeList).hasSize(databaseSizeBeforeUpdate);
        DiscountType testDiscountType = discountTypeList.get(discountTypeList.size() - 1);
        assertThat(testDiscountType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDiscountType.getModifier()).isEqualTo(UPDATED_MODIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscountType() throws Exception {
        int databaseSizeBeforeUpdate = discountTypeRepository.findAll().size();

        // Create the DiscountType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountTypeMockMvc.perform(put("/api/discount-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountType)))
            .andExpect(status().isBadRequest());

        // Validate the DiscountType in the database
        List<DiscountType> discountTypeList = discountTypeRepository.findAll();
        assertThat(discountTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiscountType() throws Exception {
        // Initialize the database
        discountTypeService.save(discountType);

        int databaseSizeBeforeDelete = discountTypeRepository.findAll().size();

        // Delete the discountType
        restDiscountTypeMockMvc.perform(delete("/api/discount-types/{id}", discountType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiscountType> discountTypeList = discountTypeRepository.findAll();
        assertThat(discountTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
