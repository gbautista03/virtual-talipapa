package ph.virtual.talipapa.service;

import ph.virtual.talipapa.domain.DiscountType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DiscountType}.
 */
public interface DiscountTypeService {

    /**
     * Save a discountType.
     *
     * @param discountType the entity to save.
     * @return the persisted entity.
     */
    DiscountType save(DiscountType discountType);

    /**
     * Get all the discountTypes.
     *
     * @return the list of entities.
     */
    List<DiscountType> findAll();

    /**
     * Get the "id" discountType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiscountType> findOne(Long id);

    /**
     * Delete the "id" discountType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
