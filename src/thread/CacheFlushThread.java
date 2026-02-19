package thread;

import cache.AccountCache;
import dao.AccountDao;
import dao.impl.AccountDaoImpl;
import util.DBUtil;

import java.sql.Connection;
import java.util.Map;

public class CacheFlushThread extends Thread {

    private AccountDao dao = new AccountDaoImpl(); // DB operations

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000); // ⏱ 60 seconds

                System.out.println("\n[CacheFlushThread] Flushing cache to DB...");

                try (Connection con = DBUtil.getConnection()) {
                    con.setAutoCommit(false);

                    for (Map.Entry<Integer, Double> entry : AccountCache.getSnapshot().entrySet()) {

                        int accId = entry.getKey();
                        double balance = entry.getValue();

                        dao.updateBalance(accId, balance, con);
                    }

                    con.commit();
                }

                System.out.println("[CacheFlushThread] Cache flushed successfully.");

            } catch (Exception e) {
                System.out.println("[CacheFlushThread] Error: " + e.getMessage());
            }
        }
    }
}
