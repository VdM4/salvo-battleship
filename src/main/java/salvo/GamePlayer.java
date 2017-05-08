package salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import salvo.Game;
import salvo.Player;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by VdM on 28/04/2017.
 */

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private Date joinDate = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game")
    private Game game;

    @OneToMany(mappedBy = "ships",fetch = FetchType.EAGER)
    Set<Ship> ships;

    public  GamePlayer(){};

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GamePlayer(Player player, Game game){
        this.player = player;
        this.game = game;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }


}
