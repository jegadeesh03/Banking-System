package service;

public interface TransactionService {
    void transfer(int fromAcc, int toAcc, double amount, String user) throws Exception;
}
