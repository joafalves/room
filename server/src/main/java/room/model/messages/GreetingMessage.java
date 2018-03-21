package room.model.messages;

public class GreetingMessage {

    private Boolean success;

    public GreetingMessage() {

    }

    public GreetingMessage(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }
}
