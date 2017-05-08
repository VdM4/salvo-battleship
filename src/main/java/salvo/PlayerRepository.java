package salvo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by VdM on 27/04/2017.
 */

@RepositoryRestResource
public interface PlayerRepository extends JpaRepository<Player, Long> {
    }

