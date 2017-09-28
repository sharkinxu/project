package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import util.Pagination;
import dao.BanJiDao;
import dao.StudentDao;
import entity.Banji;
import entity.Student;

public class StudentServlet extends HttpServlet {
	StudentDao sd = new StudentDao();
	BanJiDao bd = new BanJiDao();

	/**
	 * doGet方法对servlet进行分支利用type进行判断
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {

			// 获取type(用来判断进入那个方法的参数)的值
			String type = new String(request.getParameter("type").getBytes(
					"ISO-8859-1"), "utf-8");
			// 对type进行判断,判断进行哪个方法
			if (type.equals("addStudent")) {
				addStudent(request, response);
			} else if (type.equals("selectStudent")) {
				selectStudent(request, response);
			} else if (type.equals("alterStudent")) {
				alterStudent(request, response);
			} else if (type.equals("deleteStudent")) {
				deleteStudent(request, response);
			} else if (type.equals("addView")) {
				addView(request, response);
			} else if (type.equals("alterView")) {
				alterView(request, response);
			} else if (type.equals("upload")) {
				upload(request, response);
			} else if (type.equals("add2")) {
				add2(request, response);
			}
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void add2(HttpServletRequest request, HttpServletResponse response) {
		try {
			String name = "";
			String sex = "";
			int age = 0;
			int bj_id = 0;
			if (request.getParameter("name") != null) {
				name = new String(request.getParameter("name").getBytes(
						"ISO-8859-1"), "utf-8");
			}

			if (request.getParameter("sex") != null) {
				sex = new String(request.getParameter("sex").getBytes(
						"ISO-8859-1"), "utf-8");
			}

			if (request.getParameter("age") == null
					|| "".equals(request.getParameter("age"))) {
				age = 0;
			} else {
				age = Integer.parseInt(new String(request.getParameter("age")
						.getBytes("ISO-8859-1"), "utf-8"));
			}
			if (request.getParameter("bj_id") == null
					|| request.getParameter("bj_id").equals("")) {
				bj_id = 0;
			} else {
				bj_id = Integer.parseInt(new String(request.getParameter(
						"bj_id").getBytes("ISO-8859-1"), "utf-8"));
			}
			String photos = new String(request.getParameter("photos").getBytes(
					"ISO-8859-1"), "utf-8");
			String photo = photos.split(":")[0];
			Student stu = new Student();
			stu.setName(name);
			stu.setSex(sex);
			stu.setAge(age);
			stu.setPhoto(photo);
			Banji bj = new Banji();
			bj.setId(bj_id);
			stu.setBj(bj);

			StudentDao studao = new StudentDao();
			studao.addOne(stu);

			try {

				response.sendRedirect("showTable?type=selectStudent");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void upload(HttpServletRequest request, HttpServletResponse response) {
		// String name = request.getParameter("name");
		// String sex = request.getParameter("sex");
		// int age = (Integer.parseInt(request.getParameter("age")));
		// int bjId = (Integer.parseInt(request.getParameter("bj")));

		String newFileName = "";
		try {

			String url = request.getServletContext().getRealPath("/");
			url = url + "/tu";
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);

			for (int i = 0; i < items.size(); i++) {

				if ("photo".equals(items.get(i).getFieldName())) {

					UUID uuid = UUID.randomUUID();
					String oldFileName = items.get(i).getName();
					String houzhui = oldFileName.substring(oldFileName
							.lastIndexOf("."));
					newFileName = uuid + houzhui;
					File file = new File(url + "/" + newFileName);
					items.get(i).write(file);
				}
			}
			try {
				PrintWriter out = response.getWriter();
				out.print(newFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void alterView(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		List<Banji> banjis = bd.searchAll();

		request.setAttribute("banjis", banjis);

		Student stu = sd.searchByid(id);
		System.out.println(stu.getBj().getId());
		request.setAttribute("stu", stu);
		request.getRequestDispatcher("WEB-INF/student/alterStudentView.jsp")
				.forward(request, response);
	}

	// 定义showTable方法实现功能&throws抛出异常
	// public void showTable(HttpServletRequest request,
	// HttpServletResponse response) throws ServletException, IOException {
	//
	// int ye = 1;// 初始化ye(分页的页码)变量,
	// int yeNum = 5; // 并定义num(分页中每页的数量)
	// if (null != request.getParameter("ye")) {
	// ye = Integer.parseInt(request.getParameter("ye"));
	// }
	// int count = sd.searchVCount();// 获得数据总数
	//
	// int yeMa = 5;
	//
	// Pagination p = new Pagination(ye, count, yeNum, yeMa);
	//
	// request.setAttribute("p", p);
	// List<Student> list = sd.searchPage(p.getNumber(), yeNum);
	// request.setAttribute("list", list);
	// request.getRequestDispatcher("studentView.jsp").forward(request,
	// response);
	// }

	public void addStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = null;
		String sex = null;
		int age = 0;
		int bj_id = 0;
		String photo = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upLoad = new ServletFileUpload(factory);
			List<FileItem> items = upLoad.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getFieldName().equals("myFile")) {
					// 得到服务器路径
					String url = request.getServletContext().getRealPath("/");
					url = url + "/tu";
					String fileName = items.get(i).getName();
					int index = fileName.indexOf(".");
					String houzhui = fileName.substring(index);
					System.out.println(houzhui);

					UUID uuid = UUID.randomUUID();
					String nameUp = uuid.toString();
					photo = nameUp + houzhui;
					File file = new File(url + "/" + nameUp + houzhui);

					items.get(i).write(file);
				}
				if ("name".equals(items.get(i).getFieldName())) {
					name = new String(items.get(i).getString()
							.getBytes("ISO-8859-1"), "utf-8");
				} else if ("sex".equals(items.get(i).getFieldName())) {
					sex = new String(items.get(i).getString()
							.getBytes("ISO-8859-1"), "utf-8");
				} else if ("age".equals(items.get(i).getFieldName())) {

					age = Integer.parseInt(items.get(i).getString());

				} else if ("bj_id".equals(items.get(i).getFieldName())) {
					if ("".equals(items.get(i).getString())
							|| null == items.get(i).getString()) {
					} else {
						bj_id = Integer.parseInt(items.get(i).getString());
					}
				}
			}

		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Student stu = new Student();
		stu.setName(name);
		stu.setSex(sex);
		stu.setAge(age);
		stu.setBjId(1);
		stu.setBjId(bj_id);
		stu.setPhoto(photo);
		int flag = sd.addOne(stu);
		if (flag > 0) {
			System.out.println("添加成功");
			selectStudent(request, response);
		} else {
			System.out.println("添加失败");
		}
	}

	public void selectStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		int ye = 1;// 初始化ye(分页的页码)变量,
		int yeNum = 5; // 并定义num(分页中每页的数量)
		int yeMa = 5;
		String name = "";
		String sex = "";
		int age = 0;
		int bjId = 0;
		Student condition = new Student();
		if (null != request.getParameter("ye")) {
			ye = Integer.parseInt(request.getParameter("ye"));
		}

		if (request.getParameter("name") != null) {
			name = request.getParameter("name");
			condition.setName(name);
		}

		if (request.getParameter("sex") != null) {
			sex = request.getParameter("sex");
			condition.setSex(sex);
		}

		if (request.getParameter("age") == null
				|| request.getParameter("age").equals("")) {

			age = 0;
		} else {
			age = Integer.parseInt(request.getParameter("age"));
			condition.setAge(age);
		}
		if (request.getParameter("bj") == null
				|| request.getParameter("bj").equals("")) {
			bjId = 0;
		} else {
			bjId = Integer.parseInt(request.getParameter("bj"));
			condition.setBjId(bjId);
		}

		Student stu = new Student();
		stu.setName(name);
		stu.setSex(sex);
		stu.setAge(age);
		stu.setBjId(bjId);

		int count = sd.searchCount(stu);// 获得数据总数

		Pagination p = new Pagination(ye, count, yeNum, yeMa);
		List<Student> list = sd.searchPageOne(p.getNumber(), p.getYeNum(), stu);
		List<Banji> banjis = bd.searchAll();
		session.setAttribute("condition", condition);
		request.setAttribute("banjis", banjis);
		request.setAttribute("p", p);
		request.setAttribute("list", list);
		;
		request.getRequestDispatcher("WEB-INF/student/studentView.jsp")
				.forward(request, response);

	}

	public void addView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Banji> banjis = bd.searchAll();

		request.setAttribute("banjis", banjis);
		request.getRequestDispatcher("WEB-INF/student/addStudentView.jsp")
				.forward(request, response);

	}

	public void alterStudent(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String name = null;
		String sex = null;
		int age = 0;
		int bj_id = 0;
		int id = 0;
		String photo = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upLoad = new ServletFileUpload(factory);
			List<FileItem> items = upLoad.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).getFieldName().equals("myFile")) {
					// 得到服务器路径
					String url = request.getServletContext().getRealPath("/");
					url = url + "/tu";
					String fileName = items.get(i).getName();
					int index = fileName.indexOf(".");
					String houzhui = fileName.substring(index);
					System.out.println(houzhui);

					UUID uuid = UUID.randomUUID();
					String nameUp = uuid.toString();
					photo = nameUp + houzhui;
					File file = new File(url + "/" + nameUp + houzhui);

					items.get(i).write(file);
				}
				if ("name".equals(items.get(i).getFieldName())) {
					name = new String(items.get(i).getString()
							.getBytes("ISO-8859-1"), "utf-8");
				} else if ("sex".equals(items.get(i).getFieldName())) {
					sex = new String(items.get(i).getString()
							.getBytes("ISO-8859-1"), "utf-8");
				} else if ("age".equals(items.get(i).getFieldName())) {

					age = Integer.parseInt(items.get(i).getString());

				} else if ("bj_id".equals(items.get(i).getFieldName())) {
					if ("".equals(items.get(i).getString())
							|| null == items.get(i).getString()) {
					} else {
						bj_id = Integer.parseInt(items.get(i).getString());
					}
				} else if ("id".equals(items.get(i).getFieldName())) {

					id = Integer.parseInt(items.get(i).getString());

				}
			}

		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null == photo) {
			Student temp = sd.searchByid(id);
			photo = temp.getPhoto();
		}
		Student stu = new Student();
		stu.setId(id);
		stu.setName(name);
		stu.setSex(sex);
		stu.setAge(age);
		stu.setPhoto(photo);
		stu.setBjId(bj_id);
		int flag = sd.motify(stu);
		if (flag > 0) {
			response.sendRedirect("showTable?type=selectStudent");
		} else {

		}
	}

	public void deleteStudent(HttpServletRequest request,
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
			flag += sd.dalete(id);
		}
		if (flag == idType.length) {
			System.out.println("成功");
		} else {
			System.out.println("失败");
		}
		response.sendRedirect("showTable?type=selectStudent");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
