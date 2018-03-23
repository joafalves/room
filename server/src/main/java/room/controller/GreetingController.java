package room.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import room.manager.SessionManager;
import room.model.messages.ReplyStatusMessage;
import room.model.messages.HelloMessage;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendToUser
    public ReplyStatusMessage greeting(HelloMessage message, SimpMessageHeaderAccessor headerAccessor) {
        String sid = headerAccessor.getSessionAttributes().get("sid").toString();
        SessionManager.getInstance().addOrUpdateSession(sid, message.getFirstName(), message.getLastName());
        // TODO: handle max users...
        return new ReplyStatusMessage("", true);
    }
}
