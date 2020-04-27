package ph.virtual.talipapa.service.impl;

import ph.virtual.talipapa.service.LineItemService;
import ph.virtual.talipapa.domain.LineItem;
import ph.virtual.talipapa.repository.LineItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link LineItem}.
 */
@Service
@Transactional
public class LineItemServiceImpl implements LineItemService {

    private final Logger log = LoggerFactory.getLogger(LineItemServiceImpl.class);

    private final LineItemRepository lineItemRepository;

    public LineItemServiceImpl(LineItemRepository lineItemRepository) {
        this.lineItemRepository = lineItemRepository;
    }

    /**
     * Save a lineItem.
     *
     * @param lineItem the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LineItem save(LineItem lineItem) {
        log.debug("Request to save LineItem : {}", lineItem);
        return lineItemRepository.save(lineItem);
    }

    /**
     * Get all the lineItems.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LineItem> findAll() {
        log.debug("Request to get all LineItems");
        return lineItemRepository.findAll();
    }

    /**
     * Get one lineItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LineItem> findOne(Long id) {
        log.debug("Request to get LineItem : {}", id);
        return lineItemRepository.findById(id);
    }

    /**
     * Delete the lineItem by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LineItem : {}", id);
        lineItemRepository.deleteById(id);
    }
}
