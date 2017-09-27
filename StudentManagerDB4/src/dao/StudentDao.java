package dao;

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
public class StudentDao extends ConnectionSql {
	List<Student> stus;
	List<Banji> banjis;

	/**
	 * (1)��������Ҫ��ʵ�����ݿ�ı�������ʾ����Ҫ����Ϣ���� (2)sql����е�select�����Ǹ�����Ҫ����Ϣ���л�ȡ,�����������sql��������
	 * (3)����ResultSetʵ��,�������ܲ�ѯ������Ϣ (4)����ѯ����Ϣ���뵽һ��student,������whileѭ����student���뼯��
	 * (5)��Ϊ��student�е�һ��������banji����,��������Ҫ����һ��banji��ʵ����������ֵ�����뵽student������student
	 * (6)���֮�󷵻ؼ���,��������������ı���.
	 * 
	 * @return
	 */
	@SuppressWarnings("finally")
	public List<Student> searchAll() {
		stus = new ArrayList<Student>();
		String sql = "select s.*,b.name,b.stuNums  FROM student as s left join banji as b on b.id=s.bj_id";
		try {
			Statement stat = getStatement();
			ResultSet rs = stat.executeQuery(sql);
			// System.out.println( "1111111");
			while (rs.next()) {
				Student s = new Student();
				s.setId(rs.getInt("s.id"));
				s.setName(rs.getString("s.name"));
				s.setSex(rs.getString("s.sex"));
				s.setAge(rs.getInt("s.age"));
				s.setBj_id(rs.getInt("s.bj_id"));
				Banji bj = new Banji();
				bj.setId(rs.getInt("bj_id"));
				bj.setName(rs.getString("b.name"));
				bj.setStuNums(rs.getInt("b.stuNums"));
				s.setBj(bj);
				// s.setClassName(rs.getString("b.name"));
				stus.add(s);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, stat, null, rs);
			return stus;
		}
	}
	/**
	 * (1)�������Ƕ����ݵ�������õ���sql����е�insert
	 * (2)�����������һ��studnet,���student����Ϣ�����ı�������ʹ��ı�����������
	 * (3)�����ĸ��������ڽ������õĴ����student�е���Ϣ
	 * (4)�������ĸ�������ֵƴ�����ѧ����sql���
	 * (5)����һ��int���͵�ֵ,���������������ӳɹ�������
	 * (6)�����յ�int���͵�ֵ��Ϊ����ֵ
	 * @param stu
	 * @return
	 */
	@SuppressWarnings("finally")
	public int add(Student stu) {
		String name = stu.getName();
		String sex = stu.getSex();
		int age = stu.getAge();
		int bj_id = stu.getBj().getId();
		int result = 0;
		String sql = "insert into student(name,sex,age,bj_id) values('" + name
				+ "','" + sex + "'," + age + "," + bj_id + ")";

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
	/**
	 * (1)�������Ƕ����ݵ��޸����õ���sql����е�update
	 * (2)�����������һ��studnet,���student����Ϣ�����ı�������ʹ��ı�����������
	 * (3)�����ĸ��������ڽ������õĴ����student�е���Ϣ
	 * (4)�������ĸ�������ֵƴ���޸�ѧ����sql���
	 * (5)����һ��int���͵�ֵ,��������������޸ĳɹ�������
	 * (6)�����յ�int���͵�ֵ��Ϊ����ֵ 
	 * @param stu
	 * @return
	 */
	@SuppressWarnings("finally")
	public int motify(Student stu) {
		int id = stu.getId();
		String name = stu.getName();
		String sex = stu.getSex();
		int age = stu.getAge();
		int bj_id = stu.getBj().getId();
		int result = 0;
		String sql = "update student set name = '" + name + "',sex='" + sex
				+ "' ,age = " + age + ",bj_id=" + bj_id + "  where id =" + id;
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
	/**
	 * (1)����������ɾ����Ϣ,����ɾ����Ϣֻ��Ҫһ��Ψһ��������ֻ����һ��id��Ϊɾ������
	 * ����ɾ��sql���ؼ�����dalete
	 * (5)����һ��int���͵�ֵ,�������������ɾ���ɹ�������
	 * (6)�����յ�int���͵�ֵ��Ϊ����ֵ
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
			Statement stat = getStatement();
			result = stat.executeUpdate(sql);
			System.out.println(result);
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, stat, null, rs);
			return result;
		}

	}
	/**
	 * (1)��������Ҫ��ʵ�����ݿ�Ĳ��Ҳ�����sql(String)���
	 * (3)����ResultSetʵ��,�������ܲ�ѯ������Ϣ
	 * (4)����ѯ����Ϣ���뵽һ��student,������whileѭ����student���뼯��
	 * (5)��Ϊ��student�е�һ��������banji����,��������Ҫ����һ��banji��ʵ����������ֵ�����뵽student������student
	 * (6)���֮�󷵻ؼ���,��������������ı���.
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("finally")
	public List<Student> search(String sql) {
		stus = new ArrayList<Student>();
		try {
			stat = getStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Student s = new Student();
				s.setId(rs.getInt("s.id"));
				s.setName(rs.getString("s.name"));
				s.setSex(rs.getString("s.sex"));
				s.setAge(rs.getInt("s.age"));
				// s.setClassName(rs.getString("b.name"));
				Banji bj = new Banji();
				bj.setId(rs.getInt("bj_id"));
				bj.setName(rs.getString("b.name"));
				bj.setStuNums(rs.getInt("b.stuNums"));
				s.setBj(bj);
				stus.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, stat, null, rs);
			return stus;
		}
	}

}
