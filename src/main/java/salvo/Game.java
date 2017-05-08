package salvo;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * Created by VdM on 28/04/2017.
 */
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    private Date date = new Date();


    public Game(){};

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JsonIgnore
    public Set<GamePlayer> getGamePlayers(){
        return gamePlayers;
    }

    @Override
    public String toString() {
        return "Game{" +
                "Id=" + Id +
                ", date=" + date +
                '}';
    }
}
