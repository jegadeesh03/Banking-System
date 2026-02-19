package dao.impl;

import dao.AccountDao;
import cache.AccountCache;
import model.Account;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDaoImpl implements AccountDao {

    @Override
    public void openAccount(Account a) throws Exception {
        String sql = "INSERT INTO accounts(customer_id, balance, daily_limit, min_balance) VALUES(?,?,?,?)";
        Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, a.getCustomerId());
        ps.setDouble(2, a.getBalance());
        ps.setDouble(3, a.getDailyLimit());
        ps.setDouble(4, a.getMinBalance());
        ps.executeUpdate();

        // cache update
        // Get last inserted id
        PreparedStatement ps2 = con.prepareStatement("SELECT LAST_INSERT_ID()");
        ResultSet rs = ps2.executeQuery();
        if (rs.next()) {
            int accId = rs.getInt(1);
            AccountCache.put(accId, a.getBalance());
        }
    }

    @Override
    public void updateBalance(int accId, double newBalance, Connection con) throws Exception {
        String sql = "UPDATE accounts SET balance=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1, newBalance);
        ps.setInt(2, accId);
        ps.executeUpdate();

        // update cache
        AccountCache.put(accId, newBalance);
    }

    @Override
    public double getBalance(int accId, Connection con) throws Exception {
        Double cached = AccountCache.get(accId);
        if (cached != null) return cached;

        String sql = "SELECT balance FROM accounts WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, accId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new Exception("Account not found: " + accId);
        double balance = rs.getDouble("balance");

        AccountCache.put(accId, balance);
        return balance;
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
    public double getMinBalance(int accId, Connection con) throws Exception {
        String sql = "SELECT min_balance FROM accounts WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, accId);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) throw new Exception("Account not found: " + accId);
        return rs.getDouble("min_balance");
    }
}
