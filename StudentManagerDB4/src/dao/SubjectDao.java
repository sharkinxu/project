package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Subject;

/**
 * (1)本类是从ConnectionSql类继承下来的子类 (2)Connection用来连接数据库域关闭 (3)本类主要是实现数据库中的增删改查
 * 
 * @author lenovo
 * 
 */
public class SubjectDao extends ConnectionSql {
	List<Subject> subjects;

	/**
	 * (1)本方法用来实现数据库的查询
	 * 
	 * @return
	 */
	@SuppressWarnings("finally")
	public List<Subject> searchAll() {
		System.out.println("aaaa");
		subjects = new ArrayList<Subject>();
		String sql = "select * from Subject";
		try {
			Statement stat = getStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject s = new Subject();
				s.setId(rs.getInt("id"));
				s.setName(rs.getString("name"));
				subjects.add(s);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, stat, null, rs);
			return subjects;
		}
	}

	@SuppressWarnings("finally")
	public int add(Subject sub) {
		String name = sub.getName();
		// int stuNums = stu.getStuNums();
		int result = 0;
		String sql = "insert into Subject(name) values('" + name + "')";

		try {
			Statement stat = getStatement();
			result = stat.executeUpdate(sql);
			System.out.println(result);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, stat, null, rs);
			return result;
		}

	}

	@SuppressWarnings("finally")
	public int motify(Subject stu) {
		int id = stu.getId();
		String name = stu.getName();

		int result = 0;
		String sql = "update Subject set name = '" + name + "'  where id =" + id;
		System.out.println(sql);
		try {
			Statement stat = getStatement();
			result = stat.executeUpdate(sql);
			System.out.println(result);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, stat, null, rs);
			return result;
		}
	}

	@SuppressWarnings("finally")
	public int dalete(int id) {
		int result = 0;
		int result1=0;
		String sql = "delete from Subject where id=" + id + ";";
		String sql1 = "update student set bj_id=0 where bj_id=" + id;
		try {
			Statement stat = getStatement();
			result = stat.executeUpdate(sql);
			
			result1 = stat.executeUpdate(sql1);
			System.out.println(result);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, stat, null, rs);
			return result;
		}

	}

	@SuppressWarnings("finally")
	public List<Subject> search(String sql) {
		subjects = new ArrayList<Subject>();
		try {
			stat = getStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject s = new Subject();
				s.setId(rs.getInt("id"));
				s.setName(rs.getString("name"));

				subjects.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, stat, null, rs);
			return subjects;
		}
	}

}
