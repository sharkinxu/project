package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.RandomNumber;
import util.ValidateCode;
import dao.UserDao;
import entity.User;
import util.CreateMD5;

public class UserServlet extends HttpServlet {
	UserDao userDao = new UserDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//����type�����жϽ���ķ���
		String type = request.getParameter("type");
		try {
			if ("adminView".equals(type)) {
				request.getRequestDispatcher("WEB-INF/index/adminView.jsp")
						.forward(request, response);
			}
			if ("loginView".equals(type)) {
				request.getRequestDispatcher("WEB-INF/index/loginView.jsp")
						.forward(request, response);
			}
			if ("admin".equals(type)) {
				admin(request, response);
			}
			if ("login".equals(type)) {
				login(request, response);
			}
			if ("randomImage".equals(type)) {
				randomImage(request, response);
			}
			if ("contrast".equals(type)) {
				contrast(request, response);
			}

		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 * �����ж���֤���Ƿ���ȷ
 * @param request
 * @param response
 * @throws IOException
 */
	private void contrast(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//��ȡ���������֤��
		String validateC = (String) request.getSession().getAttribute("rand");
		//��ȡ�ı�д�����֤��
		String veryCode = request.getParameter("varyCode");
		
		PrintWriter out = response.getWriter();
		//��������֤����жԱ�
		if (veryCode == null || "".equals(veryCode)) {
			out.print("fail");
		} else {
			if (validateC.equals(veryCode)) {
				out.print("success");
			} else {
				out.print("fail");
			}
		}
		out.flush();
		out.close();

	}
	/**
	 * ִ��login��������ʵ�ֵ�¼
	 * �����������md5���м���Ȼ������ݿ���жԱ�
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			//����ı��ı������û���������
			String name = request.getParameter("username");
			String password = request.getParameter("password");
			//��װuser,����ִ�����ݿ��ѯ
			User user = new User();
			user.setUsername(name);
			User u = userDao.searchByName(name);
			user.setPassword(CreateMD5.getMd5(password + u.getTime()));
			user = userDao.searchByUsernameAndPassword(user);
			//�ж��Ƿ�������û���,�������ת����,���û��ֱ��
			if (user != null) {
				request.getSession().setAttribute("user", user);
				response.sendRedirect("index?type=index1");
			} else {

				response.sendRedirect("user?type=loginView");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void admin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("username");
		String password = request.getParameter("password");
		// password MD5����
		// password = CreateMD5.getMd5(password);

		User user = new User();
		user.setUsername(name);
		// user.setPassword(password);
		Date date = new Date();

		java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
		user.setTime(time);
		User u = userDao.add(user);
		u = userDao.searchByName(u.getUsername());
		password = CreateMD5.getMd5(password, u.getTime().toString());
		u.setPassword(password);

		u = userDao.update(u);
		System.out.println("=>" + u.getId() + " " + u.getUsername());
		request.getSession().setAttribute("user", user);

		response.sendRedirect("user?type=loginView");

		// String username = request.getParameter("username");
		// String password = request.getParameter("password");
		// int time = Integer.parseInt(request.getParameter("time"));
		// User user = null;
		// User searchAll = new User();
		// searchAll.setUsername(username);
		// searchAll.setPassword(password);
		// UserDao ud = new UserDao();
		// user = ud.searchUsernameAndPassword(searchAll);
		// if (user != null) {
		// HttpSession session = request.getSession();
		// session.setAttribute("user", user);
		//
		// Cookie cookie = new Cookie("userName", user.getUsername());
		//
		// if (time == 1) {
		// cookie.setMaxAge(0);
		// } else if (time == 2) {
		// cookie.setMaxAge(30);
		//
		// } else if (time == 3) {
		// cookie.setMaxAge(60);
		// }
		// response.addCookie(cookie);
		// request.getRequestDispatcher("WEB-INF/index/Success.jsp").forward(
		// request, response);
		// } else {
		// request.getRequestDispatcher("WEB-INF/index/Fail.jsp").forward(
		// request, response);
		// }
	}

	public void randomImage(HttpServletRequest request,
			HttpServletResponse response) {

		RandomNumber rn = new RandomNumber();
		try {
			// ����ҳ�治����
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			ValidateCode vc = rn.generateImage();
			ServletOutputStream outStream = response.getOutputStream();
			// ���ͼ��ҳ��
			ImageIO.write(vc.getImage(), "JPEG", outStream);
			outStream.close();
			request.getSession().setAttribute("rand", vc.getRand());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		doGet(request, response);

	}

}
