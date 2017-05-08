package salvo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by VdM on 08/05/2017.
 */

@RepositoryRestResource
public interface ShipRepository extends JpaRepository <Ship, Long> {
}
