package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import util.Pagination;
import dao.BanJiDao;
import dao.ScoreDao;
import dao.StudentDao;
import dao.SubjectDao;
import entity.Banji;
import entity.Score;
import entity.Student;
import entity.Subject;

public class ScoreServlet extends HttpServlet {
	StudentDao sd = new StudentDao();
	BanJiDao bd = new BanJiDao();
	ScoreDao scd = new ScoreDao();
	SubjectDao subd = new SubjectDao();

	/**
	 * doGet方法对servlet进行分支利用type进行判断
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {
		
			// 获取type(用来判断进入那个方法的参数)的值
			String type = request.getParameter("type");
			// 对type进行判断,判断进行哪个方法
			if (type.equals("searchByCondition")) {
				searchByCondition(request, response);
			} else if (type.equals("subjectShow")) {
				subjectShow(request, response);
			} else if (type.equals("alterScore")) {
				alterScore(request, response);
			}

		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void alterScore(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		int upSubId = Integer.parseInt(request.getParameter("upSubId"));
		int upStuId = Integer.parseInt(request.getParameter("upStuId"));
		String oldScore = request.getParameter("oldScore");
		int newScore = 0;
		if (request.getParameter("newScore") == null
				|| "".equals(request.getParameter("newScore"))) {
		} else {
			newScore = Integer.parseInt(request.getParameter("newScore"));
		}
		int result = 0;
		if ("".equals(oldScore)) {
			result = scd.motifyone(upStuId, upSubId, newScore);
		} else {
			result = scd.motify(upStuId, upSubId, newScore);
		}
		if (result == 1) {
			out.print(newScore);
		}
		else{
			out.print("失败");
		}

	}

	private void subjectShow(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int bjId = 0;
		if (request.getParameter("bjId") == null
				|| "".equals(request.getParameter("bjId"))) {
		} else {
			bjId = Integer.parseInt(request.getParameter("bjId"));
		}
		List<Subject> subjects = bd.searchSubjectAll(bjId);
		JSONArray array = JSONArray.fromObject(subjects);
		String str = array.toString();
		PrintWriter out = response.getWriter();
		out.print(str);
	}

	private void searchByCondition(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Score condition = new Score();
		int ye = 1;// 初始化ye(分页的页码)变量,
		if (null != request.getParameter("ye")) {
			ye = Integer.parseInt(request.getParameter("ye"));
		}

		int yeNum = 4; // 并定义num(分页中每页的数量)
		int yeMa = 5;

		String stuName = request.getParameter("stuName");

		if (stuName == null) {
			stuName = "";
		}
		session.setAttribute("studentName", stuName);
		int bjId = 0;
		if (request.getParameter("bjId") == null
				|| "".equals(request.getParameter("bjId"))) {
		} else {
			bjId = Integer.parseInt(request.getParameter("bjId"));
		}

		int subId = 0;
		if (request.getParameter("subId") == null
				|| "".equals(request.getParameter("subId"))) {

		} else {
			subId = Integer.parseInt(request.getParameter("subId"));
			session.setAttribute("studentSub", subId);
		}
		
		Student stu = new Student();
		stu.setName(stuName);
		stu.setBjId(bjId);
		condition.setStu(stu);
		Subject sub = new Subject();
		sub.setId(subId);
		condition.setSub(sub);
		int count = scd.searchCount(condition);
		session.setAttribute("condition", condition);
		Pagination p = new Pagination(ye, count, yeNum, yeMa);
		List<Score> list = scd.searchByCondition(condition, p.getNumber(),
				p.getYeNum());

		List<Banji> banjis = bd.searchAll();
		List<Subject> subjects = subd.searchAll();
		request.setAttribute("banjis", banjis);
		request.setAttribute("subjects", subjects);
		request.setAttribute("p", p);
		request.setAttribute("list", list);
		request.getRequestDispatcher("WEB-INF/score/scoreView.jsp").forward(
				request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
