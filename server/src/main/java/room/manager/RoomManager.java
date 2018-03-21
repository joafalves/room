package room.manager;

public class RoomManager {
    private static RoomManager ourInstance = new RoomManager();

    public static RoomManager getInstance() {
        return ourInstance;
    }

    private RoomManager() {
    }
}
