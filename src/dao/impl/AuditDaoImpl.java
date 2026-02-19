package dao.impl;

import dao.AuditDao;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AuditDaoImpl implements AuditDao {

    @Override
    public void saveAudit(String user, String action, String status) throws Exception {

        Connection con = DBUtil.getConnection(); // ✅ WORKS NOW

        String sql = "INSERT INTO audit_logs(username, action, status) VALUES(?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, user);
        ps.setString(2, action);
        ps.setString(3, status);

        ps.executeUpdate();
    }
}
