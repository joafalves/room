package room.model;

import java.util.ArrayList;
import java.util.List;

public class RoomPublicData {
    private String name;
    private Boolean owner;
    private Boolean joined;
    private List<PlayerData> players = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public List<PlayerData> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerData> players) {
        this.players = players;
    }

    public void addPlayer(PlayerData playerData) {
        this.players.add(playerData);
    }

    public static RoomPublicData fromRoom(Room room) {
        RoomPublicData roomPublicData = new RoomPublicData();
        roomPublicData.setName(room.getName());
        roomPublicData.setPlayers(room.getPlayers());

        return roomPublicData;
    }

    public Boolean getJoined() {
        return joined;
    }

    public void setJoined(Boolean joined) {
        this.joined = joined;
    }
}
