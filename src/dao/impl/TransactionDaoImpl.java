package dao.impl;

import dao.TransactionDao;
import cache.AccountCache;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransactionDaoImpl implements TransactionDao {

    @Override
    public double getBalance(int accId, Connection con) throws Exception {
        // 🔹 Cache-first read
        Double cached = AccountCache.get(accId);

        if (cached == null) {
            throw new Exception("Account not found: " + accId);
        }

        return cached;
    }

    @Override
    public double getMinBalance(int accId, Connection con) throws Exception {
        String sql = "SELECT min_balance FROM accounts WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, accId);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) throw new Exception("Account not found: " + accId);
        return rs.getDouble("min_balance");
    }

    @Override
    public double getDailyLimit(int accId, Connection con) throws Exception {
        String sql = "SELECT daily_limit FROM accounts WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, accId);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) throw new Exception("Account not found: " + accId);
        return rs.getDouble("daily_limit");
    }

    @Override
    public double getTodayTransferTotal(int accId, Connection con) throws Exception {
        String sql = "SELECT SUM(amount) AS total FROM transactions " +
                     "WHERE from_acc=? AND DATE(created_at)=CURDATE()";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, accId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getDouble("total");
        }
        return 0.0;
    }

    @Override
    public void updateBalance(int accId, double newBalance, Connection con) throws Exception {
        String sql = "UPDATE accounts SET balance=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1, newBalance);
        ps.setInt(2, accId);
        ps.executeUpdate();

        // 🔹 Update cache
        AccountCache.put(accId, newBalance);
    }

    @Override
    public void saveTransaction(int from, int to, double amt, Connection con) throws Exception {
        String sql = "INSERT INTO transactions(from_acc, to_acc, amount) VALUES(?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, from);
        ps.setInt(2, to);
        ps.setDouble(3, amt);
        ps.executeUpdate();
    }
}
