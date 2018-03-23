package room.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import room.game.Characters;
import room.manager.RoomManager;
import room.manager.SessionManager;
import room.model.PlayerData;
import room.model.RoleInfo;
import room.model.Room;
import room.model.RoomPublicData;
import room.model.messages.CreateRoomMessage;
import room.model.messages.JoinRoomMessage;
import room.model.messages.ReplyStatusMessage;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/room/create")
    @SendToUser
    public ReplyStatusMessage createRoom(CreateRoomMessage message, SimpMessageHeaderAccessor headerAccessor) {
        if (RoomManager.getInstance().roomExists(message.getRoomName())) {
            return new ReplyStatusMessage("Room already exists", false);
        }

        String sid = headerAccessor.getSessionAttributes().get("sid").toString();
        Room room = new Room();
        room.setCreatorId(sid);
        room.setName(message.getRoomName());

        RoomManager.getInstance().addRoom(room);

        return new ReplyStatusMessage("", true);
    }

    @MessageMapping("/room/list")
    @SendToUser
    public List<RoomPublicData> listRooms(SimpMessageHeaderAccessor headerAccessor) {
        String sid = headerAccessor.getSessionAttributes().get("sid").toString();
        List<RoomPublicData> out = new ArrayList<>();

        for(Room room : RoomManager.getInstance().getRooms()) {
            RoomPublicData roomPublicData = RoomPublicData.fromRoom(room);
            roomPublicData.setOwner(sid.equals(room.getCreatorId()) || room.getCreatorId().equals(""));
            roomPublicData.setJoined(room.hasPlayer(sid));
            out.add(roomPublicData);
        }

        return out;
    }

    @MessageMapping("/room/join")
    @SendToUser
    public ReplyStatusMessage joinRoom(JoinRoomMessage roomMessage, SimpMessageHeaderAccessor headerAccessor) {
        String sid = headerAccessor.getSessionAttributes().get("sid").toString();
        Room room = RoomManager.getInstance().getRoom(roomMessage.getRoomName());

        if (room == null) {
            return new ReplyStatusMessage("Room does not exist", false);
        }

        room.addPlayer(SessionManager.getInstance().getSession(sid));

        this.template.convertAndSend("/topic/room/updated/" + room.getName(), room.getPlayers());

        return new ReplyStatusMessage("", true);
    }

    @MessageMapping("/room/leave")
    @SendToUser
    public ReplyStatusMessage leaveRoom(JoinRoomMessage roomMessage, SimpMessageHeaderAccessor headerAccessor) {
        String sid = headerAccessor.getSessionAttributes().get("sid").toString();
        Room room = RoomManager.getInstance().getRoom(roomMessage.getRoomName());

        if (room == null) {
            return new ReplyStatusMessage("Room does not exist", false);
        }

        room.removePlayer(sid);

        this.template.convertAndSend("/topic/room/updated/" + room.getName(), room.getPlayers());

        return new ReplyStatusMessage("", true);
    }

    @MessageMapping("/room/info")
    @SendToUser
    public RoomPublicData getRoomInfo(JoinRoomMessage roomMessage, SimpMessageHeaderAccessor headerAccessor) {
        String sid = headerAccessor.getSessionAttributes().get("sid").toString();
        Room room = RoomManager.getInstance().getRoom(roomMessage.getRoomName());

        if (room == null) {
            return null;
        }

        RoomPublicData roomPublicData = RoomPublicData.fromRoom(room);
        roomPublicData.setOwner(sid.equals(room.getCreatorId()) || room.getCreatorId().equals(""));
        roomPublicData.setJoined(room.hasPlayer(sid));

        return roomPublicData;
    }

    @MessageMapping("/room/restart")
    @SendToUser
    public ReplyStatusMessage restartRoom(JoinRoomMessage roomMessage, SimpMessageHeaderAccessor headerAccessor) {
        String sid = headerAccessor.getSessionAttributes().get("sid").toString();
        Room room = RoomManager.getInstance().getRoom(roomMessage.getRoomName());

        if (room == null) {
            return new ReplyStatusMessage("Room does not exist", false);
        }

        if (!room.assignCharacters()) {
            return new ReplyStatusMessage("Could not assign characters, few players?", false);
        }

        RoomPublicData roomPublicData = RoomPublicData.fromRoom(room);
        roomPublicData.setOwner(sid.equals(room.getCreatorId()) || room.getCreatorId().equals(""));
        roomPublicData.setJoined(room.hasPlayer(sid));

        this.template.convertAndSend("/topic/room/restarted/" + room.getName(), roomPublicData);

        return new ReplyStatusMessage("", true);
    }

    @MessageMapping("/room/role-info")
    @SendToUser
    public RoleInfo getCharacterRoleInfo(JoinRoomMessage roomMessage, SimpMessageHeaderAccessor headerAccessor) {
        String sid = headerAccessor.getSessionAttributes().get("sid").toString();
        Room room = RoomManager.getInstance().getRoom(roomMessage.getRoomName());

        if (room == null) {
            return null;
        }

        PlayerData playerData = room.getPlayerData(sid);

        if (playerData == null || playerData.getCharacterId() == null) {
            return null;
        }

        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleName(Characters.getRoleName(playerData.getCharacterId()));
        roleInfo.setRoleInfo(generateRoleDescription(room.getPlayers(), playerData));

        return roleInfo;
    }

    private String generateRoleDescription(List<PlayerData> playersData, PlayerData player) {
        StringBuilder out = new StringBuilder();
        switch (player.getCharacterId()) {
            case 0:
                 out.append("Yup, you got this lame character, nothing much to know.. sorry..");
                 break;

            case 1:
            case 3:
            case 5:
                out.append("Your team is: ");
                for(PlayerData playerData : playersData) {
                    if (playerData.getCharacterId() == 1 || playerData.getCharacterId() == 3 || playerData.getCharacterId() == 5) {
                        out.append(playerData.getName() + "; ");
                    }
                }
                out.append("Do your worst!");

                break;

            case 4:
                out.append("You know who's evil: ");
                for(PlayerData playerData : playersData) {
                    if (playerData.getCharacterId() == 1 || playerData.getCharacterId() == 3 || playerData.getCharacterId() == 5) {
                        out.append(playerData.getName() + "; ");
                    }
                }
                out.append("Try to not get caught...");
                break;

            case 2:
                out.append("You are wondering, who shall you pick? ");
                for(PlayerData playerData : playersData) {
                    if (playerData.getCharacterId() == 4 || playerData.getCharacterId() == 3) {
                        out.append(playerData.getName() + "; ");
                    }
                }
                break;
        }

        return out.toString();
    }


}
