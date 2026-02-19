package dao;

public interface AuditDao {
	void saveAudit(String user, String action, String status) throws Exception;
}
