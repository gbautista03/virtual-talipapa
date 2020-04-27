package ph.virtual.talipapa.service.impl;

import ph.virtual.talipapa.service.ItemTypeService;
import ph.virtual.talipapa.domain.ItemType;
import ph.virtual.talipapa.repository.ItemTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ItemType}.
 */
@Service
@Transactional
public class ItemTypeServiceImpl implements ItemTypeService {

    private final Logger log = LoggerFactory.getLogger(ItemTypeServiceImpl.class);

    private final ItemTypeRepository itemTypeRepository;

    public ItemTypeServiceImpl(ItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    /**
     * Save a itemType.
     *
     * @param itemType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ItemType save(ItemType itemType) {
        log.debug("Request to save ItemType : {}", itemType);
        return itemTypeRepository.save(itemType);
    }

    /**
     * Get all the itemTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ItemType> findAll() {
        log.debug("Request to get all ItemTypes");
        return itemTypeRepository.findAll();
    }

    /**
     * Get one itemType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemType> findOne(Long id) {
        log.debug("Request to get ItemType : {}", id);
        return itemTypeRepository.findById(id);
    }

    /**
     * Delete the itemType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ItemType : {}", id);
        itemTypeRepository.deleteById(id);
    }
}
