package salvo;

/**
 * Created by VdM on 28/04/2017.
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface GamePlayerRepository extends JpaRepository <GamePlayer, Long>{

}
