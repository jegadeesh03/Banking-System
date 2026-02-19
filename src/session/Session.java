package session;

public class Session {

    private String username;
    private long lastAccessTime;

    public Session(String username) {
        this.username = username;
        this.lastAccessTime = System.currentTimeMillis();
    }

    public String getUsername() {
        return username;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void updateLastAccess() {
        this.lastAccessTime = System.currentTimeMillis();
    }
}
