package room.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import room.manager.SessionManager;
import room.model.messages.GreetingMessage;
import room.model.messages.HelloMessage;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GreetingMessage greeting(HelloMessage message, SimpMessageHeaderAccessor headerAccessor) {
        String sid = headerAccessor.getSessionAttributes().get("sid").toString();
        SessionManager.getInstance().addOrUpdateSession(sid, message.getFirstName(), message.getLastName());
        // TODO: handle max users...
        return new GreetingMessage(true);
    }

}
