package util;
import java.sql.*;

public class DBUtil {
	
	    private static Connection conn;

	    private static final String URL = "jdbc:mysql://localhost:3306/enterprise_bank";
	    private static final String USER = "root";
	    private static final String PASS = "jega@123";

	    public static Connection getConnection() throws Exception {
	        if (conn == null || conn.isClosed()) {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            conn = DriverManager.getConnection(URL, USER, PASS);
	            conn.setAutoCommit(true);
	        }
	        return conn;
	    }
	}


