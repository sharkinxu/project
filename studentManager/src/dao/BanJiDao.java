package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Banji;
import entity.Subject;

/**
 * (1)本类是从ConnectionSql类继承下来的子类 (2)Connection用来连接数据库域关闭 (3)本类主要是实现数据库中的增删改查
 * 
 * @author lenovo
 * 
 */
public class BanJiDao extends BaseDao {
	List<Banji> banjis;
	List<Subject> subjects;

	/**
	 * (1)本方法用来实现数据库的查询
	 * 
	 * @return
	 */
	@SuppressWarnings("finally")
	public List<Banji> searchAll() {
		banjis = new ArrayList<Banji>();
		String sql = "select * from Banji";
		try {
			getStatement();
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
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return banjis;
		}
	}

	@SuppressWarnings("finally")
	public int add(Banji stu) {
		String name = stu.getName();
		// int stuNums = stu.getStuNums();
		int result = 0;
		String sql = "insert into Banji(name,stuNums) values('" + name + "',0)";
		System.out.println(sql);
		try {
			getStatement();
			result = stat.executeUpdate(sql);
			System.out.println(result);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			getStatement();
			result = stat.executeUpdate(sql);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			Connection conn=	getConnection();
			getStatement();
			result = stat.executeUpdate(sql);
			conn.setAutoCommit(false);

			result1 = stat.executeUpdate(sql1);
			conn.commit();
			System.out.println(result);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

	}

	@SuppressWarnings("finally")
	public List<Banji> search(String sql) {
		banjis = new ArrayList<Banji>();
		try {
			getStatement();
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
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return banjis;
		}
	}

	@SuppressWarnings("finally")
	public List<Subject> searchSubjectAll(int selectId) {
		subjects = new ArrayList<Subject>();
		String where = " where 1=1 ";
		if(selectId!=0){
			where +=" and banji.id="+selectId;
		}
		String sql = "SELECT DISTINCT  subject.id, subject.name "
				+ " from banji "
				+ "INNER JOIN m_bj_sub "
				+ "ON banji.id = m_bj_sub.bj_id "
				+ "INNER JOIN `subject` "
				+ "on m_bj_sub.sub_id = `subject`.id " + where;
		 System.out.println(sql);
		try {
			getStatement();
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
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return subjects;
		}
	}

	@SuppressWarnings("finally")
	public int addSubject(int bj_id, int sub_id) {

		int result = 0;
		String sql = "insert into m_bj_sub(bj_id,sub_id) " + "values("
				+ bj_id + "," + sub_id+ ")";

		try {
			getStatement();
			result = stat.executeUpdate(sql);
			System.out.println(result);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

	}

	@SuppressWarnings("finally")
	public int daleteSubject(int bj_id, int sub_id) {
		int result = 0;
		int result1 = 0;
		String sql = "delete from m_bj_sub " + "where bj_id=" + bj_id
				+ " and sub_id=" + sub_id;
		System.out.println(sql);
		try {
			getStatement();
			result = stat.executeUpdate(sql);
			System.out.println(result);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

	}

	@SuppressWarnings("finally")
	public List<Subject> searchSubject(int selectId) {
		subjects = new ArrayList<Subject>();
		String sql = "SELECT * from `subject` "
				+ "where id<> ALL(SELECT DISTINCT "
				+ "subject.id from banji "
				+ "INNER  JOIN m_bj_sub"
				+ " ON banji.id = m_bj_sub.bj_id "
				+ "inner JOIN `subject` "
				+ "on m_bj_sub.sub_id = `subject`.id "
				+ "where banji.id="+selectId+")";
		System.out.println(sql);
		try {
			getStatement();
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
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return subjects;
		}
	}

	public int searchCount(Banji banji) {

		int count = 0;
		try {
			getStatement();
			String sql = "";

			String where = " where 1=1 ";
			String name = banji.getName();
			int stuNum = banji.getStuNums();
			if (!name.equals("")) {
				where += "and name like '%" + name + "%'";
			}
			if (stuNum != 0) {
				where += "and sex ='" + stuNum + "'";
			}

			sql = "select count(*) as c from banji " + where;
			ResultSet rs = stat.executeQuery(sql);
			System.out.println(sql);

			while (rs.next()) {
				count = rs.getInt("c");
			}
			closeAll();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public Banji searchByid(int id) {
		Banji banji = null;
		String sql = "select * from banji where id=" + id;
		System.out.println(sql);
		try {
			getStatement();
			ResultSet rs = stat.executeQuery(sql);
			// System.out.println( "1111111");
			while (rs.next()) {
				banji = new Banji();
				banji.setId(rs.getInt("id"));
				banji.setName(rs.getString("name"));
				banji.setStuNums(rs.getInt("stuNums"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return banji;
		}
	}

	public List<Banji> searchPageOne(int number, int num, Banji banji) {
		List<Banji> list = new ArrayList<Banji>();
		try {
			getStatement();
			// SELECT * FROM table LIMIT 5;
			String sql = "";

			String where = " where 1=1 ";
			String name = banji.getName();
			int stuNums = banji.getStuNums();
			if (!name.equals("")) {
				where += "and name like '%" + name + "%'";
			}

			if (stuNums != 0) {
				where += "and stuNums =" + stuNums;
			}

			sql = "select * from banji " + where + " limit " + number + ","
					+ num;
			System.out.println(sql);
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				Banji banji1 = new Banji();
				banji1.setId(rs.getInt("id"));
				banji1.setName(rs.getString("name"));
				banji1.setStuNums(rs.getInt("stuNums"));
				list.add(banji1);
			}
			closeAll();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
