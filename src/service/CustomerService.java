package service;
import model.Customer;

public interface CustomerService {
	void createCustomer(Customer customer, String createdBy) throws Exception;
}
