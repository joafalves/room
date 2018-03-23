package room.manager;

import room.model.SessionData;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static SessionManager ourInstance = new SessionManager();
    public static SessionManager getInstance() {
        return ourInstance;
    }

    private ConcurrentHashMap<String, SessionData> sessions;

    private SessionManager() {
        this.sessions = new ConcurrentHashMap<>();
    }

    public void addOrUpdateSession(String sid, String firstName, String lastName) {
        SessionData sessionData = new SessionData(sid, firstName, lastName);
        this.sessions.put(sid, sessionData);
    }

    public boolean removeSession(String sid) {
        return this.sessions.remove(sid) != null;
    }

    public SessionData getSession(String sid) {
        return this.sessions.get(sid);
    }
}
