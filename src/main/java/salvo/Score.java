package salvo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by VdM on 19/05/2017.
 */
@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player")
    private Player player;

    @OneToMany(mappedBy = "score",fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayer;

    public Set<GamePlayer> getGamePlayer() {
        return gamePlayer;
    }


    public float score;

    private Date date = new Date();

    public Score(){};

    public Score(GamePlayer gamePlayer, float score){
        this.game = gamePlayer.getGame();
        this.player = gamePlayer.getPlayer();
        this.score = score;
    };

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
