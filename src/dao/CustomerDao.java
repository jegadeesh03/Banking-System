package dao;
import model.Customer;

public interface CustomerDao {
	void save(Customer customer) throws Exception;
}
