package salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;


/**
 * Created by VdM on 08/05/2017.
 */

@Entity
public class Ship {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    Set<GamePlayer> gamePlayer;

    private String type;


    public Ship(){};

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Ship(String type){
        this.type = type;

    }

    @JsonIgnore
    public Set<GamePlayer> getGamePlayer(){
        return gamePlayer;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "Id=" + Id +
                ", type='" + type + '\'' +
                '}';
    }
}
