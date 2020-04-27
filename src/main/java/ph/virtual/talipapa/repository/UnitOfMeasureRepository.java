package ph.virtual.talipapa.repository;

import ph.virtual.talipapa.domain.UnitOfMeasure;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UnitOfMeasure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitOfMeasureRepository extends JpaRepository<UnitOfMeasure, Long> {
}
