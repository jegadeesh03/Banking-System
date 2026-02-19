package service.impl;

import dao.AccountDao;
import dao.impl.AccountDaoImpl;
import model.Account;
import service.AccountService;
import util.AuditLogger;
import util.FileLogger;  // ✅ import FileLogger

public class AccountServiceImpl implements AccountService {

    private AccountDao dao = new AccountDaoImpl();

    @Override
    public void openAccount(int customerId, double initialDeposit, String createdBy) throws Exception {

        try {
            if (initialDeposit < 2000) {
                throw new Exception("Minimum balance must be 2000");
            }

            Account acc = new Account(customerId, initialDeposit);
            dao.openAccount(acc);

            // ✅ Audit logs
            AuditLogger.log(createdBy, "OPEN_ACCOUNT", "SUCCESS");   // old
            FileLogger.log(createdBy, "OPEN_ACCOUNT", "SUCCESS");    // new file log

        } catch (Exception e) {
            // log failure
            AuditLogger.log(createdBy, "OPEN_ACCOUNT", "FAILED: " + e.getMessage());
            FileLogger.log(createdBy, "OPEN_ACCOUNT", "FAILED: " + e.getMessage());
            throw e;
        }
    }
}
