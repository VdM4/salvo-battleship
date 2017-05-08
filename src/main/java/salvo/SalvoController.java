package salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by VdM on 02/05/2017.
 */
@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private GamePlayerRepository gamePlayerRepo;

    @Autowired
    private PlayerRepository playerRepo;

    @RequestMapping("/games")
    private Map<String,Object> makeGameDTO() {

        Map<String, Object> gameObjectDTO = new LinkedHashMap<>();
        gameObjectDTO.put("Games", gameRepo.findAll()
                .stream()
                .map(game -> getGamesDTO(game))
                .collect(Collectors.toList()));
        return gameObjectDTO;
    }


    private Map<String, Object> getGamesDTO (Game game) {

        Map<String, Object> makeGameDTO = new LinkedHashMap<>();
        makeGameDTO.put("id", game.getId());
        makeGameDTO.put("creationDate", game.getDate());
        makeGameDTO.put("participation", game.getGamePlayers().stream()
                .map(gamePlayer -> getGamePlayerDTO(gamePlayer))
                .collect(Collectors.toList()));


        return makeGameDTO;
    }

/*
    private List<Object> getParticipationList(){
        return gamePlayerRepo.findAll()
                .stream()
                .map(gamePlayer -> getGamePlayerDTO(gamePlayer))
                .collect(Collectors.toList());
    }

*/
    private Map<String, Object> getGamePlayerDTO (GamePlayer gamePlayer){

        Map<String, Object> getGamePlayerObjectDTO = new LinkedHashMap<>();
        getGamePlayerObjectDTO.put("id", gamePlayer.getId());

        getGamePlayerObjectDTO.put("player", getPlayersDTO(gamePlayer.getPlayer()));


        return getGamePlayerObjectDTO;

    }

    private Map<String, Object> getPlayersDTO (Player player){

        Map<String,Object> playerObjectDTO = new LinkedHashMap<>();
        playerObjectDTO.put("id", player.getId());
        playerObjectDTO.put("username", player.getUserName());

        return playerObjectDTO;
    }

}
