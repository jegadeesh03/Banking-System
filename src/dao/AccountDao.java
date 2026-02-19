package dao;
import java.sql.Connection;

import model.Account;

public interface AccountDao {
	 void openAccount(Account a) throws Exception;

	    void updateBalance(int accId, double newBalance, Connection con) throws Exception;

	    double getBalance(int accId, Connection con) throws Exception;

	    double getDailyLimit(int accId, Connection con) throws Exception;

	    double getMinBalance(int accId, Connection con) throws Exception;
	
}
