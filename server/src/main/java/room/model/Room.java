package room.model;

import room.game.Characters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
    private static final int MAX_PLAYERS = 10;

    private String name = "";
    private String creatorId = "";
    private ConcurrentHashMap<String, PlayerData> players = new ConcurrentHashMap<>();

    public boolean assignCharacters() {
        if (players.size() < 5) {
            return false;
        }

        List<Characters> availableCharacters = new ArrayList<>();

        switch (players.size()) {
            case 5:
                availableCharacters.add(Characters.MERLIN);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);

                availableCharacters.add(Characters.ASSASSIN);
                availableCharacters.add(Characters.REGULAR_MORDRED_MINION);
                break;

            case 6:
                availableCharacters.add(Characters.MERLIN);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);

                availableCharacters.add(Characters.MORGANA);
                availableCharacters.add(Characters.ASSASSIN);
                break;

            case 7:
                availableCharacters.add(Characters.MERLIN);
                availableCharacters.add(Characters.PERCIVAL);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);

                availableCharacters.add(Characters.MORGANA);
                availableCharacters.add(Characters.ASSASSIN);
                availableCharacters.add(Characters.REGULAR_MORDRED_MINION);
                break;

            case 8:
                availableCharacters.add(Characters.MERLIN);
                availableCharacters.add(Characters.PERCIVAL);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);

                availableCharacters.add(Characters.MORGANA);
                availableCharacters.add(Characters.ASSASSIN);
                availableCharacters.add(Characters.REGULAR_MORDRED_MINION);
                break;

            case 9:
                availableCharacters.add(Characters.MERLIN);
                availableCharacters.add(Characters.PERCIVAL);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);

                availableCharacters.add(Characters.MORGANA);
                availableCharacters.add(Characters.ASSASSIN);
                availableCharacters.add(Characters.REGULAR_MORDRED_MINION);
                break;

            case 10:
                availableCharacters.add(Characters.MERLIN);
                availableCharacters.add(Characters.PERCIVAL);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);
                availableCharacters.add(Characters.REGULAR_MERLIN_MINION);

                availableCharacters.add(Characters.MORGANA);
                availableCharacters.add(Characters.ASSASSIN);
                availableCharacters.add(Characters.REGULAR_MORDRED_MINION);
                availableCharacters.add(Characters.REGULAR_MORDRED_MINION);
                break;
        }

        // shuffle the order:
        Collections.shuffle(availableCharacters);

        // assign the characters:
        int count = 0;
        for(PlayerData playerData : players.values()) {
            playerData.setCharacterId(availableCharacters.get(count++).getValue());
        }

        return true;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerData getPlayerData(String sid) {
        return this.players.get(sid);
    }

    public boolean addPlayer(SessionData sessionData) {
        if (this.players.size() >= MAX_PLAYERS) {
            return false;
        }

        PlayerData playerData = new PlayerData();
        playerData.setName(sessionData.getFirstName() + " " + sessionData.getLastName());

        this.players.put(sessionData.getSessionId(), playerData);

        /*// DUMMY:
        playerData = new PlayerData();
        playerData.setName("André Silva");
        this.players.put(sessionData.getSessionId() + "1", playerData);

        // DUMMY:
        playerData = new PlayerData();
        playerData.setName("Rita Martins");
        this.players.put(sessionData.getSessionId() + "2", playerData);

        // DUMMY:
        playerData = new PlayerData();
        playerData.setName("Pedro Martins");
        this.players.put(sessionData.getSessionId() + "3", playerData);

        // DUMMY:
        playerData = new PlayerData();
        playerData.setName("Ricardo Loureiro");
        this.players.put(sessionData.getSessionId() + "4", playerData);

        // DUMMY:
        playerData = new PlayerData();
        playerData.setName("Filipe Pinheiro");
        this.players.put(sessionData.getSessionId() + "5", playerData);
         // DUMMY:
        playerData = new PlayerData();
        playerData.setName("Jorge Oliveira");
        this.players.put(sessionData.getSessionId() + "6", playerData);
         // DUMMY:
        playerData = new PlayerData();
        playerData.setName("Nuno Faria");
        this.players.put(sessionData.getSessionId() + "7", playerData);
        // DUMMY:
        playerData = new PlayerData();
        playerData.setName("Bruno Leite");
        this.players.put(sessionData.getSessionId() + "8", playerData);
        // DUMMY:
        playerData = new PlayerData();
        playerData.setName("André Oliveira");
        this.players.put(sessionData.getSessionId() + "9", playerData);*/

        return true;
    }

    public boolean removePlayer(String sessionId) {
        return players.remove(sessionId) != null;
    }

    public List<PlayerData> getPlayers() {
        return new ArrayList<>(players.values());
    }

    public boolean hasPlayer(String sessionId) {
        return this.players.containsKey(sessionId);
    }


}
