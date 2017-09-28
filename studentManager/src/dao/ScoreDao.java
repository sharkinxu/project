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
public class ScoreDao extends BaseDao {
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
			getStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				Score s = new Score();
				s.setId(rs.getInt("sc.id"));

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
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	// getStatement();
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
	public int motify(int upStuId, int upSubId, int score) {

		int result = 0;
		String sql = "update score set score =" + score + " where stu_id= "
				+ upStuId + " and sub_id = " + upSubId;

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
	public List<Score> search(String sql) {
		scores = new ArrayList<Score>();
		System.out.println(sql);
		try {
			getStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Score s = new Score();
				s.setId(rs.getInt("sc.id"));
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
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return scores;
		}
	}

	@SuppressWarnings("finally")
	public int motifyone(int upStuId, int upSubId, int score) {

		int result = 0;

		String sql = "insert into score (stu_id,sub_id,score) VALUES("
				+ upStuId + "," + upSubId + "," + score + ")";
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
	public List<Score> searchByCondition(Score condition, int number, int num) {
		scores = new ArrayList<Score>();

		String stuName = condition.getStu().getName();
		int bjId = condition.getStu().getBjId();
		int subId = condition.getSub().getId();
		String where = "where 1=1 ";
		if (!"".equals(stuName)) {
			where += " and stu.name = '" + stuName + "'";
		}
		if (bjId != 0) {
			where += " and stu.bj_id = " + bjId;
		}
		if (subId != 0) {
			where += " and sub.id = " + subId;
		}
		String sql = "select stu.*,bj.*,sub.* ,sc.* " + "from student as stu "
				+ "left JOIN banji as bj " + "on  bj.id = stu.bj_id "
				+ "inner JOIN m_bj_sub " + "on m_bj_sub.bj_id = bj.id "
				+ "inner JOIN `subject` as sub "
				+ "on sub.id = m_bj_sub.sub_id " + "left JOIN score as sc "
				+ "on sc.sub_id =sub.id " + "and stu.id = sc.stu_id " + where
				+ " limit " + number + "," + num;
		System.out.println(sql);
		try {
			getStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Score s = new Score();
				s.setId(rs.getInt("sc.id"));

				s.setGrade(rs.getString("sc.grade"));
				if (null == rs.getString("sc.score")) {
					s.setScore(-1);
				} else {
					s.setScore(rs.getInt("sc.score"));
				}
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
			try {
				closeAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return scores;
		}
	}

	public int searchCount(Score condition) {
		String stuName = condition.getStu().getName();
		int bjId = condition.getStu().getBjId();
		int subId = condition.getSub().getId();
		int count = 0;
		String where = " where 1=1 ";
		if (!"".equals(stuName)) {
			where += " and stu.name = '" + stuName + "'";
		}
		if (bjId != 0) {
			where += " and stu.bj_id = " + bjId;
		}
		if (subId != 0) {
			where += " and sub.id = " + subId;
		}
		String sql = "select count(*) as c " + "from student as stu "
				+ "left JOIN banji as bj " + "on  bj.id = stu.bj_id "
				+ "inner JOIN m_bj_sub " + "on m_bj_sub.bj_id = bj.id "
				+ "inner JOIN `subject` as sub "
				+ "on sub.id = m_bj_sub.sub_id " + "left JOIN score as sc "
				+ "on sc.sub_id =sub.id " + "and stu.id = sc.stu_id " + where;

		try {
			getStatement();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			System.out.println(sql);
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				count = rs.getInt("c");
			}
			closeAll();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}
}
