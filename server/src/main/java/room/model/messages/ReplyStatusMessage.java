package room.model.messages;

public class ReplyStatusMessage {

    private Boolean success = false;
    private String message = "";

    public ReplyStatusMessage() {

    }

    public ReplyStatusMessage(String message, Boolean success) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
