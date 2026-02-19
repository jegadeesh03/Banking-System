package session;

public class SessionWatcherThread extends Thread {

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(5000); // check every 5 sec
                SessionManager.checkExpiredSessions();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
