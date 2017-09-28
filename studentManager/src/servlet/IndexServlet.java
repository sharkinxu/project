package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type");
		// try {
		// Method method = this.getClass().getDeclaredMethod(type,
		// HttpServletRequest.class,HttpServletResponse.class);
		// method.invoke(type, request,response);
		// } catch (NoSuchMethodException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SecurityException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
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
		try {
			if (type == null) {

				request.getRequestDispatcher("WEB-INF/index/index.jsp")
						.forward(request, response);
			}
			if ("index1".equals(type)) {
				request.getRequestDispatcher("WEB-INF/index/index1.jsp")
						.forward(request, response);
			}
			if ("index2".equals(type)) {
				request.getRequestDispatcher("WEB-INF/index/index2.jsp")
						.forward(request, response);
			}
			if ("index3".equals(type)) {
				request.getRequestDispatcher("WEB-INF/index/index3.jsp")
						.forward(request, response);
			}
			if ("index4".equals(type)) {
				request.getRequestDispatcher("WEB-INF/index/index4.jsp")
						.forward(request, response);
			}
			if ("index5".equals(type)) {
				request.getRequestDispatcher("WEB-INF/index/index5.jsp")
						.forward(request, response);
			}

		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
