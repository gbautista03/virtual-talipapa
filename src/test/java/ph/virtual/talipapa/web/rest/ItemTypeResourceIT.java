package ph.virtual.talipapa.web.rest;

import ph.virtual.talipapa.VirtualTalipapaApp;
import ph.virtual.talipapa.domain.ItemType;
import ph.virtual.talipapa.repository.ItemTypeRepository;
import ph.virtual.talipapa.service.ItemTypeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ItemTypeResource} REST controller.
 */
@SpringBootTest(classes = VirtualTalipapaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ItemTypeResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    @Autowired
    private ItemTypeService itemTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemTypeMockMvc;

    private ItemType itemType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemType createEntity(EntityManager em) {
        ItemType itemType = new ItemType()
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return itemType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemType createUpdatedEntity(EntityManager em) {
        ItemType itemType = new ItemType()
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return itemType;
    }

    @BeforeEach
    public void initTest() {
        itemType = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemType() throws Exception {
        int databaseSizeBeforeCreate = itemTypeRepository.findAll().size();

        // Create the ItemType
        restItemTypeMockMvc.perform(post("/api/item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemType)))
            .andExpect(status().isCreated());

        // Validate the ItemType in the database
        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ItemType testItemType = itemTypeList.get(itemTypeList.size() - 1);
        assertThat(testItemType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testItemType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testItemType.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testItemType.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createItemTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemTypeRepository.findAll().size();

        // Create the ItemType with an existing ID
        itemType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemTypeMockMvc.perform(post("/api/item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemType)))
            .andExpect(status().isBadRequest());

        // Validate the ItemType in the database
        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemTypeRepository.findAll().size();
        // set the field null
        itemType.setType(null);

        // Create the ItemType, which fails.

        restItemTypeMockMvc.perform(post("/api/item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemType)))
            .andExpect(status().isBadRequest());

        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemTypes() throws Exception {
        // Initialize the database
        itemTypeRepository.saveAndFlush(itemType);

        // Get all the itemTypeList
        restItemTypeMockMvc.perform(get("/api/item-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getItemType() throws Exception {
        // Initialize the database
        itemTypeRepository.saveAndFlush(itemType);

        // Get the itemType
        restItemTypeMockMvc.perform(get("/api/item-types/{id}", itemType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingItemType() throws Exception {
        // Get the itemType
        restItemTypeMockMvc.perform(get("/api/item-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemType() throws Exception {
        // Initialize the database
        itemTypeService.save(itemType);

        int databaseSizeBeforeUpdate = itemTypeRepository.findAll().size();

        // Update the itemType
        ItemType updatedItemType = itemTypeRepository.findById(itemType.getId()).get();
        // Disconnect from session so that the updates on updatedItemType are not directly saved in db
        em.detach(updatedItemType);
        updatedItemType
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restItemTypeMockMvc.perform(put("/api/item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemType)))
            .andExpect(status().isOk());

        // Validate the ItemType in the database
        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeUpdate);
        ItemType testItemType = itemTypeList.get(itemTypeList.size() - 1);
        assertThat(testItemType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testItemType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testItemType.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testItemType.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingItemType() throws Exception {
        int databaseSizeBeforeUpdate = itemTypeRepository.findAll().size();

        // Create the ItemType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemTypeMockMvc.perform(put("/api/item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemType)))
            .andExpect(status().isBadRequest());

        // Validate the ItemType in the database
        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemType() throws Exception {
        // Initialize the database
        itemTypeService.save(itemType);

        int databaseSizeBeforeDelete = itemTypeRepository.findAll().size();

        // Delete the itemType
        restItemTypeMockMvc.perform(delete("/api/item-types/{id}", itemType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemType> itemTypeList = itemTypeRepository.findAll();
        assertThat(itemTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
