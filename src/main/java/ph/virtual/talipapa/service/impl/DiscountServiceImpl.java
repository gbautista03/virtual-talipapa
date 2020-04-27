package ph.virtual.talipapa.service.impl;

import ph.virtual.talipapa.service.DiscountService;
import ph.virtual.talipapa.domain.Discount;
import ph.virtual.talipapa.repository.DiscountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Discount}.
 */
@Service
@Transactional
public class DiscountServiceImpl implements DiscountService {

    private final Logger log = LoggerFactory.getLogger(DiscountServiceImpl.class);

    private final DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    /**
     * Save a discount.
     *
     * @param discount the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Discount save(Discount discount) {
        log.debug("Request to save Discount : {}", discount);
        return discountRepository.save(discount);
    }

    /**
     * Get all the discounts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Discount> findAll() {
        log.debug("Request to get all Discounts");
        return discountRepository.findAll();
    }

    /**
     * Get one discount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Discount> findOne(Long id) {
        log.debug("Request to get Discount : {}", id);
        return discountRepository.findById(id);
    }

    /**
     * Delete the discount by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Discount : {}", id);
        discountRepository.deleteById(id);
    }
}
