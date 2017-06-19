package salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.*;
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

    @Autowired
    private ShipRepository shipRepo;


    @RequestMapping("/games")
    private Map<String,Object> makeGameDTO(Authentication auth) {

        Map<String, Object> gameObjectDTO = new LinkedHashMap<>();

        if(auth != null){
            gameObjectDTO.put("currentPlayer", getPlayerInfo(auth));
        }

        gameObjectDTO.put("games", gameRepo.findAll()
                .stream()
                .map(game -> getGamesDTO(game))
                .collect(Collectors.toList()));
        gameObjectDTO.put("leaderboard", playerRepo.findAll()
                .stream()
                .map(player -> getScoresDTO(player)).collect(Collectors.toList()));


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

    private Map<String, Object> getScoresDTO (Player player){

        Map<String,Object> getScoresDTO = new LinkedHashMap<>();

        if(player.getScores() != null) {

            getScoresDTO.put("username", player.getUserName());
            getScoresDTO.put("id", player.getId());
            getScoresDTO.put("totalScores", player.getScores().stream().mapToDouble(Score::getScore).sum());
            getScoresDTO.put("wins", player.getScores().stream().filter(score -> score.getScore() == 1 ).count());
            getScoresDTO.put("losses", player.getScores().stream().filter(score -> score.getScore() == 0).count());
            getScoresDTO.put("ties", player.getScores().stream().filter(score -> score.getScore() == 0.5).count());

        }

        return getScoresDTO;
    }

    private Player getPlayerAuth (Authentication auth) {

        return playerRepo.findByUserName(auth.getName());

    }

    private Map<String, Object> getPlayerInfo (Authentication auth){

        Map<String,Object> playerObjectDTO = new LinkedHashMap<>();
        playerObjectDTO.put("id", getPlayerAuth(auth).getId());
        playerObjectDTO.put("username", getPlayerAuth(auth).getUserName());
        playerObjectDTO.put("games", getPlayerAuth(auth).getGamePlayers()
                .stream()
                .map(gamePlayer -> getGPinfo(gamePlayer))
                .collect(Collectors.toList()));

        return playerObjectDTO;
    }

    private Map<String, Object> getGPinfo (GamePlayer gamePlayer){
        Map<String, Object> GpDTO = new LinkedHashMap<>();

        GpDTO.put("game", gamePlayer.getGame().getId());
        GpDTO.put("gpId", gamePlayer.getId());

        return GpDTO;
    }


    @RequestMapping("/game_view/{gameId}")
    public Map<String, Object> findGame (@PathVariable Long gameId){

        GamePlayer gamePlayer = gamePlayerRepo.findOne(gameId);

        Game game = gamePlayer.getGame();


        Map<String, Object> gameViewDTO = new LinkedHashMap<>();
        gameViewDTO.put("id", game.getId());
        gameViewDTO.put("date", game.getDate());
        gameViewDTO.put("participation", game.getGamePlayers().stream()
                .map(gamePlayer1 -> getGamePlayerDTO(gamePlayer1))
                .collect(Collectors.toList()));
        gameViewDTO.put("ships", gamePlayer.getShips().stream()
                .map(ship -> getShipsDTO(ship))
                .collect(Collectors.toList()));
        gameViewDTO.put("salvoes", game.getGamePlayers().stream()
                .map(gamePlayer2 -> getAllSalvoDTO(gamePlayer2))
                .collect(Collectors.toList()));



        return gameViewDTO;
    }

    private Map<String, Object> getShipsDTO (Ship ship){
        Map<String, Object> shipObjectDTO = new LinkedHashMap<>();
        shipObjectDTO.put("type", ship.getType());
        shipObjectDTO.put("locations", ship.getLocations());

        return shipObjectDTO;
    }

    private Map<String, Object> getSalvoesDTO (Salvo salvo){


        Map<String,Object> SalvoesDTO = new LinkedHashMap<>();
        SalvoesDTO.put("turn", salvo.getTurn());
        SalvoesDTO.put("locations", salvo.getLocations());

        return SalvoesDTO;
    }

    private Map<String, Object> getAllSalvoDTO (GamePlayer gamePlayer2){

        Map<String, Object> enemySalvoesDTO = new LinkedHashMap<>();
        enemySalvoesDTO.put("player", gamePlayer2.getId());
        enemySalvoesDTO.put("salvos", gamePlayer2.getSalvoes().stream()
                .map(salvo -> getSalvoesDTO(salvo))
                .collect(Collectors.toList()));

        return enemySalvoesDTO;



    }


    @RequestMapping(value = "/players", method = RequestMethod.POST)
    public ResponseEntity<String> createUser (@RequestParam String userName, @RequestParam String password) {

        if(userName.isEmpty()) {
            return new ResponseEntity<>("No username given", HttpStatus.FORBIDDEN);
        }

        Player player = playerRepo.findByUserName(userName);
        if(player != null){
            return new ResponseEntity<>("Name already in use", HttpStatus.CONFLICT);
        }

        playerRepo.save(new Player(userName, password));
        return new ResponseEntity<>("Player added", HttpStatus.CREATED);

    }

    @RequestMapping(path = "/game", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame (Authentication authentication){

        Game game = new Game();

        gameRepo.save(game);

        Player player = playerRepo.findByUserName(authentication.getName());

        GamePlayer gamePlayer = new GamePlayer(player, game);

        gamePlayerRepo.save(gamePlayer);

        if(authentication.getName().isEmpty()){
            return new ResponseEntity<>(makeMap("error","Unauthorized access"), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(makeMap("gpID", gamePlayer.getId()), HttpStatus.CREATED);


    }

    @RequestMapping(path = "/game/{gameId}", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> joinGame (Authentication authentication, @PathVariable Long gameId){

        Game game = gameRepo.findOne(gameId);

        Player player = playerRepo.findByUserName(authentication.getName());

        if(authentication.getName().isEmpty()){
            return new ResponseEntity<>(makeMap("error", "Unauthorized access"), HttpStatus.UNAUTHORIZED);
        }

        GamePlayer gamePlayer = new GamePlayer(player, game);

        gamePlayerRepo.save(gamePlayer);


        return new ResponseEntity<>(makeMap("gpId", gamePlayer.getId()), HttpStatus.CREATED);

    }

    @RequestMapping(path = "/games/players/{gamePlayerId}/ships", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createShips (Authentication authentication, @PathVariable Long gamePlayerId, @RequestBody Set<Ship> ships){

        Player currentPlayer = playerRepo.findByUserName(authentication.getName());

        GamePlayer currentGamePlayer = gamePlayerRepo.findOne(gamePlayerId);

        if(authentication.getName().isEmpty()){
            return new ResponseEntity<>(makeMap("error", "Unauthorized access"), HttpStatus.UNAUTHORIZED);
        }

        if(currentGamePlayer == null){
            return new ResponseEntity<>(makeMap("error", "Unauthorized access"), HttpStatus.UNAUTHORIZED);
        }

        if(currentGamePlayer.getPlayer().getId() != currentPlayer.getId()){
            return new ResponseEntity<>(makeMap("error", "Unauthorized access"), HttpStatus.UNAUTHORIZED);
        }

        if(!currentGamePlayer.getShips().isEmpty()){
            return new ResponseEntity<>(makeMap("error", "Ships already placed"), HttpStatus.FORBIDDEN);
        }

        for(Ship ship : ships){

            shipRepo.save(ship);

            currentGamePlayer.addShip(ship);
        }

        gamePlayerRepo.save(currentGamePlayer);

        return new ResponseEntity<>(makeMap("boom", "You have placed your ships"), HttpStatus.CREATED);
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

}
