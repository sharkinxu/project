package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import entity.User;

public class UserDao extends BaseDao {

	public User searchByUsernameAndPassword(User user) {
		User u = null;
		try {
			getStatement();
			String sql = "select * from users where username='"
					+ user.getUsername() + "' and password='"
					+ user.getPassword() + "'";
			rs = stat.executeQuery(sql);
			while (rs.next()) {

				u = new User();
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return u;
	}

	public User add(User user) {
		User u = null;
		try {
			String sql = "insert into users(username,time)values(?,?)";
			Connection conn = getConnection();
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			// pstat.setString(2, user.getPassword());
			pstat.setTimestamp(2, user.getTime());

			int result = pstat.executeUpdate();
			if (result > 0) {
				sql = "select last_insert_id()";
				ResultSet rs = pstat.executeQuery(sql);

				while (rs.next()) {
					u = new User();
					u.setId(rs.getInt(1));
					u.setUsername(user.getUsername());

				}

			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return u;
	}

	public User update(User user) {
		User u = null;
		try {
			String sql = "update users set password=? where id=?";
			Connection conn = getConnection();
			PreparedStatement pstat = conn.prepareStatement(sql);
			pstat.setString(1, user.getPassword());
			// pstat.setString(2, user.getPassword());
			pstat.setInt(2, user.getId());

			int result = pstat.executeUpdate();

			if (result > 0) {
				u = user;

			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return u;
	}

	public User searchByName(String name) {
		User u = null;
		try {
			getStatement();
			String sql = "select * from users where username='" + name + "'";
			rs = stat.executeQuery(sql);
			while (rs.next()) {

				u = new User();
				u.setId(rs.getInt("id"));
				u.setTime(rs.getTimestamp("time"));
				u.setUsername(rs.getString("username"));

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return u;
	}
}
