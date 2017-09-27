package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionSql {
	Connection con = null;
	Statement stat;
	ResultSet rs;

	public Connection getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// ½¨Á¢Á´½Ó
			con = DriverManager
					.getConnection(
							"jdbc:mysql://127.0.0.1:3306/school?characterEncoding=utf-8",
							"root", "123456");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public Statement getStatement() {
		con = getConnection();
		Statement stat = null;
		try {
			stat = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stat;
	}

	public void closeAll(Connection con, Statement stat,
			PreparedStatement pstat, ResultSet rs) {
		try {
			if (con != null) {
				con.close();
			}
			if (stat != null) {
				stat.close();
			}
			if (pstat != null) {
				pstat.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
