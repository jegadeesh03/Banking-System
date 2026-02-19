package dao.impl;

import dao.CustomerDao;
import model.Customer;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CustomerDaoImpl implements CustomerDao {

	    @Override
	    public void save(Customer c) throws Exception {
	        String sql = "INSERT INTO customers(name, city, mobile) VALUES(?,?,?)";
	        Connection con = DBUtil.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, c.getName());
	        ps.setString(2, c.getCity());
	        ps.setString(3, c.getMobile());
	        int rows = ps.executeUpdate();

	        System.out.println("Customer rows inserted: " + rows);
	    }
	}


