package service;

public interface AccountService {
	void openAccount(int customerId, double initialDeposit, String createdBy) throws Exception;
}
