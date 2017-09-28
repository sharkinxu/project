package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import util.Pagination;
import dao.BanJiDao;
import entity.Banji;
import entity.Subject;

@SuppressWarnings("serial")
public class BanjiServlet extends HttpServlet {
	BanJiDao bd = new BanJiDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {

			// 获取type(用来判断进入那个方法的参数)的值
			String type = request.getParameter("type");

			// Method[] method = this.getClass().getMethods();
			// for (int i = 0; i < method.length; i++) {
			// if (method[i].getName().equals(type)) {
			// try {
			// method[i].invoke(this, request, response);
			// } catch (IllegalAccessException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (IllegalArgumentException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (InvocationTargetException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// }

			// 对type进行判断,判断进行哪个方法
			if (type.equals("addBanji")) {
				addBanji(request, response);
			} else if (type.equals("selectBanji")) {
				selectBanji(request, response);
			} else if (type.equals("alterBanji")) {
				alterBanji(request, response);
			} else if (type.equals("deleteBanji")) {
				deleteBanji(request, response);
			} else if (type.equals("addView")) {
				addView(request, response);
			} else if (type.equals("alterView")) {
				alterView(request, response);
			} else if (type.equals("manageSubject")) {
				manageSubject(request, response);
			} else if (type.equals("showInSubject")) {
				showInSubject(request, response);
			} else if (type.equals("showOutSubject")) {
				showOutSubject(request, response);
			} else if (type.equals("addInSubject")) {
				addInSubject(request, response);
			} else if (type.equals("outInSubject")) {
				outInSubject(request, response);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	private void outInSubject(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 定义flag用于判断查询数据库是否成功
		int flag = 0;
		// 接收从前端传过来的数据
		int bj_id = Integer.parseInt(request.getParameter("bj_id"));
		// 接收从数据
		String[] idType = request.getParameter("selectId").split(",");
		for (int i = 0; i < idType.length; i++) {
			int sub_id = 0;
			if (idType[i] == null || idType[i].equals("")) {

			} else {
				sub_id = Integer.parseInt(idType[i]);
			}
			flag += bd.daleteSubject(bj_id, sub_id);
		}
		if (flag == idType.length) {
			System.out.println("成功");
		} else {
			System.out.println("失败");
		}
		PrintWriter out = response.getWriter();
		// 用已经选择的课程做成字符串
		List<Subject> inSubs = bd.searchSubjectAll(bj_id);
		JSONArray array = JSONArray.fromObject(inSubs);
		String str = array.toString();
		System.out.println(str);
		// 用未经选择的课程做成字符串
		List<Subject> noSubs = bd.searchSubject(bj_id);
		JSONArray array1 = JSONArray.fromObject(noSubs);
		String str1 = array1.toString();
		System.out.println(str1);
		// 将两个字符串返回

		out.print(str + "^" + str1);
	}

	private void addInSubject(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int flag = 0;
		int bj_id = Integer.parseInt(request.getParameter("bj_id"));

		String[] idType = request.getParameter("selectId").split(",");
		for (int i = 0; i < idType.length; i++) {
			int sub_id = 0;
			if (idType[i] == null || idType[i].equals("")) {

			} else {
				sub_id = Integer.parseInt(idType[i]);
			}
			flag += bd.addSubject(bj_id, sub_id);
		}
		if (flag == idType.length) {
			System.out.println("成功");
		} else {
			System.out.println("失败");
		}
		PrintWriter out = response.getWriter();
		// 用已经选择的课程做成字符串
		List<Subject> inSubs = bd.searchSubjectAll(bj_id);
		JSONArray array = JSONArray.fromObject(inSubs);
		String str = array.toString();
		System.out.println(str);
		// 用未经选择的课程做成字符串
		List<Subject> noSubs = bd.searchSubject(bj_id);
		JSONArray array1 = JSONArray.fromObject(noSubs);
		String str1 = array1.toString();
		System.out.println(str1);
		// 将两个字符串返回

		out.print(str + "^" + str1);

	}

	public void alterView(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Banji banji = bd.searchByid(id);
		System.out.println(banji.getId());
		request.setAttribute("banji", banji);
		request.getRequestDispatcher("WEB-INF/banji/alterBanjiView.jsp")
				.forward(request, response);
	}

	public void addBanji(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");

		Banji banji = new Banji();
		banji.setName(name);
		int flag = bd.add(banji);
		if (flag > 0) {
			System.out.println("添加成功");
			selectBanji(request, response);
		} else {
			System.out.println("添加失败");
		}
	}

	public void selectBanji(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Banji condition = new Banji();
		int ye = 1;// 初始化ye(分页的页码)变量,
		int yeNum = 5; // 并定义yeNum(分页中每页的数量)
		int yeMa = 5;
		String name = "";
		if (null != request.getParameter("ye")) {
			ye = Integer.parseInt(request.getParameter("ye"));
		}

		if (request.getParameter("name") != null) {
			name = request.getParameter("name");
			condition.setName(name);

		}

		int stuNums = 0;
		if (request.getParameter("stuNums") == null
				|| request.getParameter("stuNums").equals("")) {
			stuNums = 0;
		} else {
			stuNums = Integer.parseInt(request.getParameter("stuNums"));
			condition.setStuNums(stuNums);
			
		}
		session.setAttribute("condition", condition);
		Banji banji = new Banji();
		banji.setName(name);
		banji.setStuNums(stuNums);
		int count = bd.searchCount(banji);// 获得数据总数

		Pagination p = new Pagination(ye, count, yeNum, yeMa);
		List<Banji> list = bd.searchPageOne(p.getNumber(), p.getYeNum(), banji);
		request.setAttribute("p", p);
		request.setAttribute("list", list);
		request.getRequestDispatcher("WEB-INF/banji/banjiView.jsp").forward(
				request, response);

	}

	public void addView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/banji/addBanjiView.jsp").forward(
				request, response);
	}

	public void alterBanji(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String idType = request.getParameter("id");
		int id = 0;
		if (!idType.equals("")) {
			id = Integer.parseInt(idType);
		}

		String name = request.getParameter("name");

		Banji banji = new Banji();
		banji.setId(id);
		banji.setName(name);
		int flag = bd.motify(banji);
		if (flag > 0) {
			response.sendRedirect("showBanji?type=selectBanji");
		}
	}

	public void deleteBanji(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int flag = 0;
		String[] idType = request.getParameter("selectId").split(",");
		for (int i = 0; i < idType.length; i++) {
			int id = 0;
			if (idType[i] == null || idType[i].equals("")) {

			} else {
				id = Integer.parseInt(idType[i]);
			}
			System.out.println("id" + i + "   " + id);
			flag += bd.dalete(id);
		}
		if (flag == idType.length) {
			System.out.println("成功");
		} else {
			System.out.println("失败");
		}
		response.sendRedirect("showBanji?type=selectBanji");
	}

	public void manageSubject(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("bj_id", id);

		request.getRequestDispatcher("WEB-INF/banji/banjiSubjectView.jsp")
				.forward(request, response);

	}

	public void showInSubject(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		List<Subject> inSubs = bd.searchSubjectAll(id);
		JSONArray array = JSONArray.fromObject(inSubs);
		String str1 = array.toString();
		PrintWriter out = response.getWriter();
		out.print(str1);

	}

	public void showOutSubject(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("id" + id);
		List<Subject> noSubs = bd.searchSubject(id);
		JSONArray array1 = JSONArray.fromObject(noSubs);
		String str1 = array1.toString();
		PrintWriter out = response.getWriter();
		System.out.println("未选择" + str1);
		out.print(str1);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
