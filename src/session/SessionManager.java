package session;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final ConcurrentHashMap<String, Session> sessions =
            new ConcurrentHashMap<>();

    private static final long SESSION_TIMEOUT = 1 * 60 * 1000; // 5 minutes

    public static String createSession(String username) {
        String sessionId = java.util.UUID.randomUUID().toString();
        sessions.put(sessionId, new Session(username));
        return sessionId;
    }
    public static void checkExpiredSessions() {

        long now = System.currentTimeMillis();

        sessions.forEach((sessionId, session) -> {

            if (now - session.getLastAccessTime() > SESSION_TIMEOUT) {
                sessions.remove(sessionId);
                System.out.println("⚠ Session expired automatically for user: " + session.getUsername());
            }

        });
    }


    public static boolean isSessionValid(String sessionId) {
        Session session = sessions.get(sessionId);

        if (session == null) return false;

        long now = System.currentTimeMillis();

        if (now - session.getLastAccessTime() > SESSION_TIMEOUT) {
            sessions.remove(sessionId);
            return false;
        }

        // update last access (activity reset timeout)
        session.updateLastAccess();

        return true;
    }

    public static void logout(String sessionId) {
        sessions.remove(sessionId);
    }

    public static String getUsername(String sessionId) {
        Session session = sessions.get(sessionId);
        return session != null ? session.getUsername() : null;
    }
}
