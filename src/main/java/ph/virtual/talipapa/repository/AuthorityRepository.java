package ph.virtual.talipapa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ph.virtual.talipapa.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
