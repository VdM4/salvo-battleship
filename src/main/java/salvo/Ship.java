package salvo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by VdM on 08/05/2017.
 */

@Entity
public class Ship {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer")
    private GamePlayer gamePlayer;

    private String shipType;

    @ElementCollection
    @Column(name = "locations")
    private List<String> locations;

    public Ship() {
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getType() {
        return shipType;
    }

    public void setType(String type) {
        this.shipType = type;
    }

    public Ship(String shipType, ArrayList<String> locations) {
        this.shipType = shipType;
        this.locations = locations;

    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public GamePlayer getGamePlayer() {
        return this.gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {

        this.gamePlayer = gamePlayer;
    }

}