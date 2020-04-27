package ph.virtual.talipapa.repository;

import ph.virtual.talipapa.domain.LineItem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LineItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {
}
