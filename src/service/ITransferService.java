package service;

import model.Account;

public interface ITransferService {
    /**
     * Transfer money from one account to another
     * Implements ACID principles (savepoint, rollback)
     *
     * @param from   Sender account
     * @param to     Receiver account
     * @param amount Transfer amount
     */
    void transfer(Account from, Account to, double amount);
}
