package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Banji;
import entity.Score;
import entity.Student;
import entity.Subject;

/**
 * (1)本类是从ConnectionSql类继承下来的子类 (2)Connection用来连接数据库域关闭 (3)本类主要是实现数据库中的增删改查
 * 
 * @author lenovo
 * 
 */
public class ScoreDao extends ConnectionSql {
	List<Score> scores;

	/**
	 * (1)本方法用来实现数据库的查询
	 * 
	 * @return
	 */
	@SuppressWarnings("finally")
	public List<Score> searchAll() {
		System.out.println("aaaa");
		scores = new ArrayList<Score>();
		String sql = "select stu.*,bj.*,sub.* ,sc.* " + "from student as stu "
				+ "left JOIN banji as bj " + "on  bj.id = stu.bj_id "
				+ "inner JOIN m_bj_sub " + "on m_bj_sub.bj_id = bj.id "
				+ "inner JOIN `subject` as sub "
				+ "on sub.id = m_bj_sub.sub_id " + "left JOIN score as sc "
				+ "on sc.sub_id =sub.id " + "and stu.id = sc.stu_id";
		System.out.println(sql);
		try {
			Statement stat = getStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Score s = new Score();
				s.setId(rs.getInt("sc.id"));
				s.setStu_id(rs.getInt("sc.stu_id"));
				s.setSub_id(rs.getInt("sc.sub_id"));

				s.setGrade(rs.getString("sc.grade"));
				s.setScore(rs.getInt("sc.score"));
				Student stu = new Student();

				stu.setId(rs.getInt("stu.id"));
				stu.setName(rs.getString("stu.name"));
				stu.setSex(rs.getString("stu.sex"));
				stu.setAge(rs.getInt("stu.age"));
				Banji bj = new Banji();
				bj.setId(rs.getInt("bj.id"));
				bj.setName(rs.getString("bj.name"));
				bj.setStuNums(rs.getInt("bj.stuNums"));
				stu.setBj(bj);
				s.setStu(stu);
				Subject sub = new Subject();
				sub.setId(rs.getInt("sub.id"));
				sub.setName(rs.getString("sub.name"));
				s.setSub(sub);
				scores.add(s);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, stat, null, rs);
			return scores;
		}
	}

	// @SuppressWarnings("finally")
	// public int add(Score stu) {
	// // int stuNums = stu.getStuNums();
	// int result = 0;
	// String sql = "insert into Score(name) values('" + name + "')";
	//
	// try {
	// Statement stat = getStatement();
	// result = stat.executeUpdate(sql);
	// System.out.println(result);
	// stat.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// } finally {
	// closeAll(con, stat, null, rs);
	// return result;
	// }
	//
	// }

	@SuppressWarnings("finally")
	public int motify(Score sc, int score) {

		int result = 0;
		String sql = "update score set score =" + score + " where id ="
				+ sc.getId();

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
	public List<Score> search(String sql) {
		scores = new ArrayList<Score>();
		System.out.println(sql);
		try {
			stat = getStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Score s = new Score();
				s.setId(rs.getInt("sc.id"));
				s.setStu_id(rs.getInt("sc.stu_id"));
				s.setSub_id(rs.getInt("sc.sub_id"));

				s.setGrade(rs.getString("sc.grade"));
				s.setScore(rs.getInt("sc.score"));
				Student stu = new Student();

				stu.setId(rs.getInt("stu.id"));
				stu.setName(rs.getString("stu.name"));
				stu.setSex(rs.getString("stu.sex"));
				stu.setAge(rs.getInt("stu.age"));
				Banji bj = new Banji();
				bj.setId(rs.getInt("bj.id"));
				bj.setName(rs.getString("bj.name"));
				bj.setStuNums(rs.getInt("bj.stuNums"));
				stu.setBj(bj);
				s.setStu(stu);
				Subject sub = new Subject();
				sub.setId(rs.getInt("sub.id"));
				sub.setName(rs.getString("sub.name"));
				s.setSub(sub);
				scores.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(con, stat, null, rs);
			return scores;
		}
	}

	@SuppressWarnings("finally")
	public int motifyone(Score sc, int score) {

		int result = 0;
		
String sql= "insert into score (stu_id,sub_id,score) VALUES("+sc.getStu().getId()+","+sc.getSub().getId()+","+score+")";
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
}
