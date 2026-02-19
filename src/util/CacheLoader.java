package util;
import cache.AccountCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CacheLoader {
	public static void loadAccounts() throws Exception {

        AccountCache.clear();

        Connection con = DBUtil.getConnection();
        String sql = "SELECT id, balance FROM accounts";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int accId = rs.getInt("id");
            double bal = rs.getDouble("balance");

            AccountCache.put(accId, bal);
        }

        System.out.println("✅ Account cache loaded from DB");
    }
}
