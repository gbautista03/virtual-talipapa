package ph.virtual.talipapa.service.impl;

import ph.virtual.talipapa.service.DiscountTypeService;
import ph.virtual.talipapa.domain.DiscountType;
import ph.virtual.talipapa.repository.DiscountTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DiscountType}.
 */
@Service
@Transactional
public class DiscountTypeServiceImpl implements DiscountTypeService {

    private final Logger log = LoggerFactory.getLogger(DiscountTypeServiceImpl.class);

    private final DiscountTypeRepository discountTypeRepository;

    public DiscountTypeServiceImpl(DiscountTypeRepository discountTypeRepository) {
        this.discountTypeRepository = discountTypeRepository;
    }

    /**
     * Save a discountType.
     *
     * @param discountType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DiscountType save(DiscountType discountType) {
        log.debug("Request to save DiscountType : {}", discountType);
        return discountTypeRepository.save(discountType);
    }

    /**
     * Get all the discountTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DiscountType> findAll() {
        log.debug("Request to get all DiscountTypes");
        return discountTypeRepository.findAll();
    }

    /**
     * Get one discountType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiscountType> findOne(Long id) {
        log.debug("Request to get DiscountType : {}", id);
        return discountTypeRepository.findById(id);
    }

    /**
     * Delete the discountType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DiscountType : {}", id);
        discountTypeRepository.deleteById(id);
    }
}
