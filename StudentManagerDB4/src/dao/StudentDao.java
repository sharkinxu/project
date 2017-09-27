package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Banji;
import entity.Student;

/**
 * (1)本类是从ConnectionSql类继承下来的子类 (2)Connection用来连接数据库域关闭 (3)本类主要是实现数据库中的增删改查
 * 
 * @author lenovo
 * 
 */
public class StudentDao extends ConnectionSql {
	List<Student> stus;
	List<Banji> banjis;

	/**
	 * (1)本方法主要是实现数据库的遍历和显示出想要的信息给表 (2)sql语句中的select部分是根据想要的信息进行获取,整个语句用了sql的左连接
	 * (3)创建ResultSet实例,用来接受查询出的信息 (4)将查询的信息放入到一个student,并利用while循环将student放入集合
	 * (5)因为在student中的一个属性是banji的类,所以我们要创建一个banji的实例化给它赋值并加入到student中完善student
	 * (6)完成之后返回集合,用来填入主界面的表中.
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
	 * (1)本方法是对数据的添加利用的是sql语句中的insert
	 * (2)方法传入的是一个studnet,这个student的信息是由文本框输入和从文本框输入后换算的
	 * (3)定义四个变量用于接收有用的传入的student中的信息
	 * (4)根据这四个变量的值拼接添加学生的sql语句
	 * (5)接收一个int类型的值,这个是用来返回添加成功的行数
	 * (6)将接收的int类型的值作为返回值
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
	 * (1)本方法是对数据的修改利用的是sql语句中的update
	 * (2)方法传入的是一个studnet,这个student的信息是由文本框输入和从文本框输入后换算的
	 * (3)定义四个变量用于接收有用的传入的student中的信息
	 * (4)根据这四个变量的值拼接修改学生的sql语句
	 * (5)接收一个int类型的值,这个是用来返回修改成功的行数
	 * (6)将接收的int类型的值作为返回值 
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
	 * (1)本方法用于删除信息,由于删除信息只需要一个唯一索引所以只传入一个id作为删除依据
	 * 定义删除sql语句关键字是dalete
	 * (5)接收一个int类型的值,这个是用来返回删除成功的行数
	 * (6)将接收的int类型的值作为返回值
	 * @param id
	 * @return
	 */
	@SuppressWarnings("finally")
	public int dalete(int id) {
		int result = 0;
		String sql = "delete from student where id=" + id;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 建立链接
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
	 * (1)本方法主要是实现数据库的查找参数是sql(String)语句
	 * (3)创建ResultSet实例,用来接受查询出的信息
	 * (4)将查询的信息放入到一个student,并利用while循环将student放入集合
	 * (5)因为在student中的一个属性是banji的类,所以我们要创建一个banji的实例化给它赋值并加入到student中完善student
	 * (6)完成之后返回集合,用来填入主界面的表中.
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
