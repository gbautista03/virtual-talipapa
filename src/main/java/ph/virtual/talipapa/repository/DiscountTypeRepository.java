package ph.virtual.talipapa.repository;

import ph.virtual.talipapa.domain.DiscountType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DiscountType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountTypeRepository extends JpaRepository<DiscountType, Long> {
}
