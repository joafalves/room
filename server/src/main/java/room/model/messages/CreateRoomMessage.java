package room.model.messages;

/**
 * Created by João Filipe Alves <joao.f.alves@wit-software.com> on 14-06-2017.
 */
public class CreateRoomMessage {

    private String roomName;

    public CreateRoomMessage() {

    }

    public CreateRoomMessage(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
