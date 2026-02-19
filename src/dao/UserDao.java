package dao;
import model.User;

public interface UserDao {
	 User findByUsername(String username) throws Exception;
}
