package room.model.messages;

/**
 * Created by João Filipe Alves <joao.f.alves@wit-software.com> on 14-06-2017.
 */
public class CreateRoomMessage {

    private String name;

    public CreateRoomMessage() {

    }

    public CreateRoomMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
