package ph.virtual.talipapa.service;

import ph.virtual.talipapa.domain.UnitOfMeasure;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link UnitOfMeasure}.
 */
public interface UnitOfMeasureService {

    /**
     * Save a unitOfMeasure.
     *
     * @param unitOfMeasure the entity to save.
     * @return the persisted entity.
     */
    UnitOfMeasure save(UnitOfMeasure unitOfMeasure);

    /**
     * Get all the unitOfMeasures.
     *
     * @return the list of entities.
     */
    List<UnitOfMeasure> findAll();

    /**
     * Get the "id" unitOfMeasure.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UnitOfMeasure> findOne(Long id);

    /**
     * Delete the "id" unitOfMeasure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
