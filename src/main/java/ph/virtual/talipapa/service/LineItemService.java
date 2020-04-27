package ph.virtual.talipapa.service;

import ph.virtual.talipapa.domain.LineItem;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LineItem}.
 */
public interface LineItemService {

    /**
     * Save a lineItem.
     *
     * @param lineItem the entity to save.
     * @return the persisted entity.
     */
    LineItem save(LineItem lineItem);

    /**
     * Get all the lineItems.
     *
     * @return the list of entities.
     */
    List<LineItem> findAll();

    /**
     * Get the "id" lineItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LineItem> findOne(Long id);

    /**
     * Delete the "id" lineItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
