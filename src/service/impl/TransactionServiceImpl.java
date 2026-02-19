package service.impl;

import dao.TransactionDao;
import dao.impl.TransactionDaoImpl;
import service.TransactionService;
import util.AuditLogger;
import util.DBUtil;
import util.WALLogger;

import java.sql.Connection;

public class TransactionServiceImpl implements TransactionService {

    private TransactionDao dao = new TransactionDaoImpl();

    @Override
    public void transfer(int from, int to, double amt, String user) throws Exception {

        if (from == to) {
            throw new Exception("From and To account cannot be the same");
        }

        // 🔥 Get DB connection
        try (Connection con = DBUtil.getConnection()) {
            
            con.setAutoCommit(false);   // START TRANSACTION

            try {
                // 1️⃣ Validate accounts exist & get balances
                double fromBal = dao.getBalance(from, con);
                double toBal   = dao.getBalance(to, con);
                double minBal  = dao.getMinBalance(from, con);

                if (fromBal - amt < minBal) {
                    throw new Exception("Insufficient balance!");
                }

                // 2️⃣ DAILY LIMIT CHECK
                double todayTotal = dao.getTodayTransferTotal(from, con);
                double dailyLimit = dao.getDailyLimit(from, con);
                if (todayTotal + amt > dailyLimit) {
                    throw new Exception("Daily transfer limit exceeded!");
                }

              
                WALLogger.write(from, to, amt, "STARTED");

                // 3️⃣ Debit
                dao.updateBalance(from, fromBal - amt, con);
//                if (true) {
//              throw new RuntimeException("Simulated crash after debit!");
//          }

                // 4️⃣ Credit
                dao.updateBalance(to, toBal + amt, con);
                

                // 5️⃣ Save transaction
                dao.saveTransaction(from, to, amt, con);

                con.commit();  // ✅ SUCCESS
                AuditLogger.log(user, "TRANSFER", "SUCCESS");

             // 🔐 WAL COMMIT
                WALLogger.write(from, to, amt, "COMMITTED");
            } catch (Exception e) {
                con.rollback();  // ❌ ROLLBACK
                AuditLogger.log(user, "TRANSFER", "FAILED: " + e.getMessage());

             // 🔐 WAL FAILED
                WALLogger.write(from, to, amt, "FAILED");

                throw e;
            } finally {
                con.setAutoCommit(true);
            }

        } // try-with-resources closes connection automatically
    }
}
