package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Banji;
import entity.Student;

/**
 * (1)�����Ǵ�ConnectionSql��̳����������� (2)Connection�����������ݿ���ر� (3)������Ҫ��ʵ�����ݿ��е���ɾ�Ĳ�
 * 
 * @author lenovo
 * 
 */
public class StudentDao extends BaseDao {
	List<Student> stus;
	List<Banji> banjis;


	@SuppressWarnings("finally")
	public int addOne(Student stu) {
		String name = stu.getName();
		String sex = stu.getSex();
		int age = stu.getAge();
		int bj_id = stu.getBjId();
		String photo = stu.getPhoto();
		int result = 0;
		String sql = "insert into student(name,sex,age,bj_id,photo) values('"
				+ name + "','" + sex + "'," + age + "," + bj_id + ",'" + photo
				+ "')";

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

	/**
	 * (1)�������Ƕ����ݵ��޸����õ���sql����е�update
	 * (2)�����������һ��studnet,���student����Ϣ�����ı�������ʹ��ı�����������
	 * (3)�����ĸ��������ڽ������õĴ����student�е���Ϣ (4)�������ĸ�������ֵƴ���޸�ѧ����sql���
	 * (5)����һ��int���͵�ֵ,��������������޸ĳɹ������� (6)�����յ�int���͵�ֵ��Ϊ����ֵ
	 * 
	 * @param stu
	 * @return
	 */
	@SuppressWarnings("finally")
	public int motify(Student stu) {
		int id = stu.getId();
		String name = stu.getName();
		String sex = stu.getSex();
		int age = stu.getAge();
		int bj_id = stu.getBjId();
		String photo = stu.getPhoto();

		int result = 0;
		String sql = "update student set name = '" + name + "',sex='" + sex
				+ "' ,age = " + age + ",bj_id=" + bj_id + ",photo='" + photo
				+ "'  where id =" + id;
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

	/**
	 * (1)����������ɾ����Ϣ,����ɾ����Ϣֻ��Ҫһ��Ψһ��������ֻ����һ��id��Ϊɾ������ ����ɾ��sql���ؼ�����dalete
	 * (5)����һ��int���͵�ֵ,�������������ɾ���ɹ������� (6)�����յ�int���͵�ֵ��Ϊ����ֵ
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("finally")
	public int dalete(int id) {
		int result = 0;
		String sql = "delete from student where id=" + id;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// ��������
			getStatement();
			result = stat.executeUpdate(sql);
			System.out.println(result);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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

	/**
	 * (1)��������Ҫ��ʵ�����ݿ�Ĳ��Ҳ�����sql(String)��� (3)����ResultSetʵ��,�������ܲ�ѯ������Ϣ
	 * (4)����ѯ����Ϣ���뵽һ��student,������whileѭ����student���뼯��
	 * (5)��Ϊ��student�е�һ��������banji����,��������Ҫ����һ��banji��ʵ����������ֵ�����뵽student������student
	 * (6)���֮�󷵻ؼ���,��������������ı���.
	 * 
	 * @param sql
	 * @return
	 */
	// @SuppressWarnings("finally")
	// public List<Student> search(String sql) {
	// stus = new ArrayList<Student>();
	// try {
	// stat = getStatement();
	// rs = stat.executeQuery(sql);
	// while (rs.next()) {
	// Student s = new Student();
	// s.setId(rs.getInt("s.id"));
	// s.setName(rs.getString("s.name"));
	// s.setSex(rs.getString("s.sex"));
	// s.setAge(rs.getInt("s.age"));
	// // s.setClassName(rs.getString("b.name"));
	// Banji bj = new Banji();
	// bj.setId(rs.getInt("bj_id"));
	// bj.setName(rs.getString("b.name"));
	// bj.setStuNums(rs.getInt("b.stuNums"));
	// s.setBj(bj);
	// stus.add(s);
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// } finally {
	// closeAll(con, stat, null, rs);
	// return stus;
	// }
	// }

	// public int searchVCount() {
	// List<Student> list = new ArrayList<Student>();
	// int count = 0;
	// try {
	// Class.forName("com.mysql.jdbc.Driver");
	//
	// Connection con = DriverManager
	// .getConnection(
	// "jdbc:mysql://127.0.0.1:3306/school?characterEncoding=utf-8",
	// "root", "123456");
	// Statement stat = con.createStatement();
	// ResultSet rs = stat
	// .executeQuery("select count(*) as c from student");
	// System.out.println("select count(*) from student");
	// while (rs.next()) {
	// count = rs.getInt("c");
	// }
	// rs.close();
	// stat.close();
	// con.close();
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return count;
	// }

	public int searchCount(Student stu) {
		List<Student> list = new ArrayList<Student>();
		int count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager
					.getConnection(
							"jdbc:mysql://127.0.0.1:3306/school?characterEncoding=utf-8",
							"root", "123456");
			Statement stat = con.createStatement();
			String sql = "";

			String where = " where 1=1 ";
			String name = stu.getName();
			String sex = stu.getSex();
			int age = stu.getAge();
			int bj_id = stu.getBjId();
			if (!name.equals("")) {
				where += "and name like '%" + name + "%'";
			}
			if (!sex.equals("")) {
				where += " and sex ='" + sex + "'";
			}
			if (age != 0) {
				where += " and age =" + age;
			}
			if (bj_id != 0) {
				where += " and bj_id=" + bj_id;
			}
			sql = "select count(*) as c from student " + where;
			System.out.println(sql);
			ResultSet rs = stat.executeQuery(sql);
			System.out.println(sql);
			System.out.println("select count(*) as c from student");
			while (rs.next()) {
				count = rs.getInt("c");
			}
			rs.close();
			stat.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	// public List<Student> searchAll() {
	// List<Student> list = new ArrayList<Student>();
	// try {
	// Class.forName("com.mysql.jdbc.Driver");
	//
	// Connection con = DriverManager
	// .getConnection(
	// "jdbc:mysql://127.0.0.1:3306/school?characterEncoding=utf-8",
	// "root", "123456");
	// Statement stat = con.createStatement();
	// ResultSet rs = stat.executeQuery("select * from student");
	// System.out.println("select * from student");
	// while (rs.next()) {
	// Student stu = new Student();
	// stu.setId(rs.getInt("id"));
	// stu.setName(rs.getString("name"));
	// stu.setSex(rs.getString("sex"));
	// stu.setAge(rs.getInt("age"));
	// list.add(stu);
	// }
	// rs.close();
	// stat.close();
	// con.close();
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return list;
	// }

	// public List<Student> searchPage(int number, int num) {
	// List<Student> list = new ArrayList<Student>();
	// try {
	// Class.forName("com.mysql.jdbc.Driver");
	//
	// Connection con = DriverManager
	// .getConnection(
	// "jdbc:mysql://127.0.0.1:3306/school?characterEncoding=utf-8",
	// "root", "123456");
	// Statement stat = con.createStatement();
	// // SELECT * FROM table LIMIT 5;
	// String sql = "select * from student limit "
	// + number + "," + num;
	// ResultSet rs = stat.executeQuery(sql);
	//
	// while (rs.next()) {
	// Student stu = new Student();
	// stu.setId(rs.getInt("id"));
	// stu.setName(rs.getString("name"));
	// stu.setSex(rs.getString("sex"));
	// stu.setAge(rs.getInt("age"));
	// list.add(stu);
	// }
	// rs.close();
	// stat.close();
	// con.close();
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return list;
	// }

	public List<Student> searchPageOne(int number, int num, Student stu) {
		List<Student> list = new ArrayList<Student>();
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager
					.getConnection(
							"jdbc:mysql://127.0.0.1:3306/school?characterEncoding=utf-8",
							"root", "123456");
			Statement stat = con.createStatement();
			// SELECT * FROM table LIMIT 5;
			String sql = "";

			String where = " where 1=1 ";
			String name = stu.getName();
			String sex = stu.getSex();
			int age = stu.getAge();
			int bj_id = stu.getBjId();
			if (!name.equals("")) {
				where += " and s.name like '%" + name + "%'";
			}
			if (!sex.equals("")) {
				where += " and s.sex ='" + sex + "'";
			}
			if (age != 0) {
				where += " and s.age =" + age;
			}
			/*
			 * ��Ӱ༶���ж�
			 */
			if (bj_id != 0) {
				where += " and bj_id=" + bj_id;
			}

			sql = "select s.*,b.name,b.stuNums ,b.id FROM student as s left join banji as b on b.id=s.bj_id "
					+ where + " limit " + number + "," + num;
			System.out.println(sql);
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				Student stu1 = new Student();
				stu1.setId(rs.getInt("s.id"));
				stu1.setName(rs.getString("s.name"));
				stu1.setSex(rs.getString("s.sex"));
				stu1.setAge(rs.getInt("s.age"));
				stu1.setPhoto(rs.getString("s.photo"));
				Banji banji = new Banji();
				banji.setId(rs.getInt("b.id"));
				banji.setName(rs.getString("b.name"));
				banji.setStuNums(rs.getInt("b.stuNums"));
				stu1.setBj(banji);
				list.add(stu1);
			}
			rs.close();
			stat.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings({ "finally", "null" })
	public Student searchByid(int id) {
		Student stu1 = new Student();
		String sql = "select s.*,b.id,b.name,b.stuNums FROM student as s left join banji as b on b.id=s.bj_id where s.id="
				+ id;
		System.out.println(sql);
		try {
			getStatement();
			ResultSet rs = stat.executeQuery(sql);
			// System.out.println( "1111111");
			while (rs.next()) {
				stu1.setId(rs.getInt("s.id"));
				stu1.setName(rs.getString("s.name"));
				stu1.setSex(rs.getString("s.sex"));
				stu1.setAge(rs.getInt("s.age"));
				stu1.setPhoto(rs.getString("s.photo"));

				Banji banji = new Banji();
				banji.setId(rs.getInt("b.id"));
				banji.setName(rs.getString("b.name"));
				banji.setStuNums(rs.getInt("b.stuNums"));
				stu1.setBj(banji);

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
			return stu1;
		}
	}
}
