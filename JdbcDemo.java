package com.enjoylearning.mybatis;
//STEP 1. 导入sql相关的包
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.enjoylearning.mybatis.entity.TUser;

public class JdbcDemo {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/yucong?characterEncoding=utf-8";
	static final String USER = "root";
	static final String PASS = "root";	
	
	@Test
	public void QueryPreparedStatementDemo() {
		Connection conn = null;
		PreparedStatement stmt = null;
		List<TUser> users = new ArrayList<>();
		try {
			// STEP 2: 注册mysql的驱动
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: 获得一个连接
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: 创建一个查询
			System.out.println("Creating statement...");
			String sql;
			sql = "SELECT * FROM t_user where user_name= ? ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "lison");
			ResultSet rs = stmt.executeQuery();
			

			// STEP 5: 从resultSet中获取数据并转化成bean
			while (rs.next()) {
				System.out.println("------------------------------");
				// Retrieve by column name
				TUser user = new TUser();
//				user.setId(rs.getInt("id"));
//				user.setUserName(rs.getString("user_name"));
				user.setRealName(rs.getString("real_name"));
				user.setSex(rs.getByte("sex"));
				user.setMobile(rs.getString("mobile"));
				user.setEmail(rs.getString("email"));
				user.setNote(rs.getString("note"));

				System.out.println(user.toString());
				
				users.add(user);
			}
			// STEP 6: 关闭连接
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("-------------------------");
		System.out.println("there are "+users.size()+" users in the list!");
	}
}
