package ph.virtual.talipapa.service;

import ph.virtual.talipapa.domain.ItemType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ItemType}.
 */
public interface ItemTypeService {

    /**
     * Save a itemType.
     *
     * @param itemType the entity to save.
     * @return the persisted entity.
     */
    ItemType save(ItemType itemType);

    /**
     * Get all the itemTypes.
     *
     * @return the list of entities.
     */
    List<ItemType> findAll();

    /**
     * Get the "id" itemType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemType> findOne(Long id);

    /**
     * Delete the "id" itemType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
