package ph.virtual.talipapa.service.impl;

import ph.virtual.talipapa.service.UnitOfMeasureService;
import ph.virtual.talipapa.domain.UnitOfMeasure;
import ph.virtual.talipapa.repository.UnitOfMeasureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UnitOfMeasure}.
 */
@Service
@Transactional
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final Logger log = LoggerFactory.getLogger(UnitOfMeasureServiceImpl.class);

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    /**
     * Save a unitOfMeasure.
     *
     * @param unitOfMeasure the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UnitOfMeasure save(UnitOfMeasure unitOfMeasure) {
        log.debug("Request to save UnitOfMeasure : {}", unitOfMeasure);
        return unitOfMeasureRepository.save(unitOfMeasure);
    }

    /**
     * Get all the unitOfMeasures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UnitOfMeasure> findAll() {
        log.debug("Request to get all UnitOfMeasures");
        return unitOfMeasureRepository.findAll();
    }

    /**
     * Get one unitOfMeasure by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UnitOfMeasure> findOne(Long id) {
        log.debug("Request to get UnitOfMeasure : {}", id);
        return unitOfMeasureRepository.findById(id);
    }

    /**
     * Delete the unitOfMeasure by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UnitOfMeasure : {}", id);
        unitOfMeasureRepository.deleteById(id);
    }
}
