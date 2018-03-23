package room.manager;

import room.model.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RoomManager {

    private ConcurrentHashMap<String, Room> rooms;

    private static RoomManager ourInstance = new RoomManager();

    public static RoomManager getInstance() {
        return ourInstance;
    }

    private RoomManager() {
        this.rooms = new ConcurrentHashMap<>();

        Room room = new Room();
        room.setName("Room of Zabbix Hell");
        room.setCreatorId("");
        this.rooms.put(room.getName(), room);

        room = new Room();
        room.setName("Room of Morgana");
        room.setCreatorId("");
        this.rooms.put(room.getName(), room);
    }

    public boolean roomExists(String name) {
        return rooms.containsKey(name);
    }

    public void addRoom(Room room) {
        this.rooms.put(room.getName(), room);
    }

    public Room getRoom(String name) {
        return this.rooms.get(name);
    }

    public List<Room> getRooms() {
        return new ArrayList<>(this.rooms.values());
    }

}
