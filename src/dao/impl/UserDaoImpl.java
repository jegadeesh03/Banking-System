package dao.impl;
import dao.UserDao;
import model.User;
import util.DBUtil;

import java.sql.*;
public class UserDaoImpl implements UserDao {

    @Override
    public User findByUsername(String username) throws Exception {
        String sql = "SELECT u.id, u.username, u.password_hash, r.name " +
                     "FROM users u JOIN roles r ON u.role_id=r.id WHERE username=?";
        Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        

        if (rs.next()) {
            return new User(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4)
            );
        }
        return null;
    }
}