package service.impl;

import dao.CustomerDao;
import dao.AuditDao;
import dao.impl.CustomerDaoImpl;
import dao.impl.AuditDaoImpl;
import model.Customer;
import service.CustomerService;
import util.AuditLogger;

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao dao = new CustomerDaoImpl();
    private AuditDao auditDao = new AuditDaoImpl(); // ✅ ADD THIS

    @Override
    public void createCustomer(Customer customer, String createdBy) throws Exception {

        try {
            // 1️⃣ Save customer (DB)
            dao.save(customer);

            // 2️⃣ FILE audit
            AuditLogger.log(createdBy, "CREATE_CUSTOMER", "SUCCESS");

            // 3️⃣ DB audit
            auditDao.saveAudit(createdBy, "CREATE_CUSTOMER", "SUCCESS");

        } catch (Exception e) {

            // FAILURE audit
            AuditLogger.log(createdBy, "CREATE_CUSTOMER", "FAILED");

            // Optional: DB failure audit
            auditDao.saveAudit(createdBy, "CREATE_CUSTOMER", "FAILED");

            throw e; // rethrow so caller knows
        }
    }
}
