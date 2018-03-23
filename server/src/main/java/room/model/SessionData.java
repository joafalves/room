package room.model;

public class SessionData {
    private String sessionId;
    private String firstName;
    private String lastName;

    public SessionData() {

    }

    public SessionData(String sid, String firstName, String lastName) {
        this.sessionId = sid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SessionData) {
            return ((SessionData) other).sessionId.equals(this.sessionId);
        }

        return other.equals(this);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
