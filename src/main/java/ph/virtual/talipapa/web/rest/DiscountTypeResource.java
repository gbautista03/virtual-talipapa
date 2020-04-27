package ph.virtual.talipapa.web.rest;

import ph.virtual.talipapa.domain.DiscountType;
import ph.virtual.talipapa.service.DiscountTypeService;
import ph.virtual.talipapa.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ph.virtual.talipapa.domain.DiscountType}.
 */
@RestController
@RequestMapping("/api")
public class DiscountTypeResource {

    private final Logger log = LoggerFactory.getLogger(DiscountTypeResource.class);

    private static final String ENTITY_NAME = "discountType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiscountTypeService discountTypeService;

    public DiscountTypeResource(DiscountTypeService discountTypeService) {
        this.discountTypeService = discountTypeService;
    }

    /**
     * {@code POST  /discount-types} : Create a new discountType.
     *
     * @param discountType the discountType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new discountType, or with status {@code 400 (Bad Request)} if the discountType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/discount-types")
    public ResponseEntity<DiscountType> createDiscountType(@Valid @RequestBody DiscountType discountType) throws URISyntaxException {
        log.debug("REST request to save DiscountType : {}", discountType);
        if (discountType.getId() != null) {
            throw new BadRequestAlertException("A new discountType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiscountType result = discountTypeService.save(discountType);
        return ResponseEntity.created(new URI("/api/discount-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /discount-types} : Updates an existing discountType.
     *
     * @param discountType the discountType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discountType,
     * or with status {@code 400 (Bad Request)} if the discountType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discountType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/discount-types")
    public ResponseEntity<DiscountType> updateDiscountType(@Valid @RequestBody DiscountType discountType) throws URISyntaxException {
        log.debug("REST request to update DiscountType : {}", discountType);
        if (discountType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiscountType result = discountTypeService.save(discountType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, discountType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /discount-types} : get all the discountTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discountTypes in body.
     */
    @GetMapping("/discount-types")
    public List<DiscountType> getAllDiscountTypes() {
        log.debug("REST request to get all DiscountTypes");
        return discountTypeService.findAll();
    }

    /**
     * {@code GET  /discount-types/:id} : get the "id" discountType.
     *
     * @param id the id of the discountType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the discountType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/discount-types/{id}")
    public ResponseEntity<DiscountType> getDiscountType(@PathVariable Long id) {
        log.debug("REST request to get DiscountType : {}", id);
        Optional<DiscountType> discountType = discountTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discountType);
    }

    /**
     * {@code DELETE  /discount-types/:id} : delete the "id" discountType.
     *
     * @param id the id of the discountType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/discount-types/{id}")
    public ResponseEntity<Void> deleteDiscountType(@PathVariable Long id) {
        log.debug("REST request to delete DiscountType : {}", id);
        discountTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
