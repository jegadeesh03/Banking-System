package dao;

import java.sql.Connection;

public interface TransactionDao {

    double getBalance(int accId, Connection con) throws Exception;

    double getMinBalance(int accId, Connection con) throws Exception;

    void updateBalance(int accId, double newBalance, Connection con) throws Exception;

    void saveTransaction(int from, int to, double amt, Connection con) throws Exception;
    
    double getTodayTransferTotal(int accId, Connection con) throws Exception;

    double getDailyLimit(int accId, Connection con) throws Exception;

}