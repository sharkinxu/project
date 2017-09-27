package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Banji;
import entity.Subject;

/**
 * (1)本类是从ConnectionSql类继承下来的子类
 *  (2)Connection用来连接数据库域关闭 
 *  (3)本类主要是实现数据库中的增删改查
 * 
 * @author lenovo
 * 
 */
public class BanJiDao extends ConnectionSql {
	List<Banji> banjis;
	List<Subject> subjects;

	/**
	 * (1)本方法用来实现数据库的查询
	 * 
	 * @return
	 */
	@SuppressWarnings("finally")
	public List<Banji> searchAll() {
		System.out.println("aaaa");
		banjis = new ArrayList<Banji>();
		String sql = "select * from Banji";
		try {
			Statement stat = getStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Banji s = new Banji();
				s.setId(rs.getInt("id"));
				s.setName(rs.getString("name"));
				s.setStuNums(rs.getInt("stuNums"));
				banjis.add(s);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, stat, null, rs);
			return banjis;
		}
	}

	@SuppressWarnings("finally")
	public int add(Banji stu) {
		String name = stu.getName();
		// int stuNums = stu.getStuNums();
		int result = 0;
		String sql = "insert into Banji(name) values('" + name + "')";
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
	public int motify(Banji stu) {
		int id = stu.getId();
		String name = stu.getName();

		int result = 0;
		String sql = "update Banji set name = '" + name + "'  where id =" + id;
		try {
			Statement stat = getStatement();
			result = stat.executeUpdate(sql);
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
		int result1 = 0;
		String sql = "delete from Banji where id=" + id + ";";
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
	public List<Banji> search(String sql) {
		banjis = new ArrayList<Banji>();
		try {
			stat = getStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Banji s = new Banji();
				s.setId(rs.getInt("id"));
				s.setName(rs.getString("name"));
				s.setStuNums(rs.getInt("stuNums"));
				banjis.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, stat, null, rs);
			return banjis;
		}
	}

	public List<Subject> searchSubjectAll(Banji bj) {
		subjects = new ArrayList<Subject>();
		System.out.println(bj.getId());
		String sql = "SELECT subject.id, subject.name "
				+ " from banji "
				+ "INNER JOIN m_bj_sub "
				+ "ON banji.id = m_bj_sub.bj_id "
				+ "INNER JOIN `subject` "
				+ "on m_bj_sub.sub_id = `subject`.id "
				+ "where banji.id= "
				+ bj.getId();
		 System.out.println(sql);
		try {
			Statement stat = getStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject s = new Subject();
				s.setId(rs.getInt("subject.id"));
				s.setName(rs.getString("subject.name"));

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
	public int addSubject(Banji bj,Subject sub) {
		String name = bj.getName();
		int result = 0;
		String sql = "insert into m_bj_sub(bj_id,sub_id) "
				+ "values("+bj.getId()
				+","+sub.getId()+")";

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
	public int daleteSubject(Banji bj,Subject sub) {
		int result = 0;
		int result1 = 0;
String sql = "delete from m_bj_sub "
		+ "where bj_id="+bj.getId()
		+" and sub_id="+sub.getId();
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
	public List<Subject> searchSubject(Banji bj) {
		subjects = new ArrayList<Subject>();
		String sql = "SELECT * from `subject` "
				+ "where id<> ALL(SELECT DISTINCT "
				+ "subject.id from banji "
				+ "INNER  JOIN m_bj_sub"
				+ " ON banji.id = m_bj_sub.bj_id "
				+ "inner JOIN `subject` "
				+ "on m_bj_sub.sub_id = `subject`.id "
				+ "where banji.id="+ bj.getId()+")";
		System.out.println(sql);
		try {
			Statement stat = getStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject s = new Subject();
				s.setId(rs.getInt("subject.id"));
				s.setName(rs.getString("subject.name"));
				
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
