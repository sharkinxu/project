package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Pagination;
import dao.SubjectDao;
import entity.Subject;

@SuppressWarnings("serial")
public class SubjectServlet extends HttpServlet {
	SubjectDao subd = new SubjectDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {
			

			// ��ȡtype(�����жϽ����Ǹ������Ĳ���)��ֵ
			String type = request.getParameter("type");
			
			Method[] method = this.getClass().getMethods();
			for (int i = 0; i < method.length; i++) {
				if(method[i].getName().equals(type)){
				
						method[i].invoke(this, request,response);
					
				}
			}
			
			
			// ��type�����ж�,�жϽ����ĸ�����
//			 if (type.equals("addSubject")) {
//				addSubject(request, response);
//			} else if (type.equals("selectSubject")) {
//				selectSubject(request, response);
//			} else if (type.equals("alterSubject")) {
//				alterSubject(request, response);
//			} else if (type.equals("deleteSubject")) {
//				deleteSubject(request, response);
//			} else if (type.equals("addView")) {
//				addView(request, response);
//			} else if (type.equals("alterView")) {
//				alterView(request, response);
//			}
		}  catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void alterView(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		Subject subject = subd.searchByid(id);
		System.out.println(subject.getId());
		request.setAttribute("subject", subject);
		request.getRequestDispatcher("WEB-INF/subject/alterSubjectView.jsp").forward(request,
				response);
	}

	// ����showSubject����ʵ�ֹ���&throws�׳��쳣
	// public void showSubject(HttpServletRequest request,
	// HttpServletResponse response) throws ServletException, IOException {
	//
	// int ye = 1;// ��ʼ��ye(��ҳ��ҳ��)����,
	// int yeNum = 5; // ������num(��ҳ��ÿҳ������)
	// if (null != request.getParameter("ye")) {
	// ye = Integer.parseInt(request.getParameter("ye"));
	// }
	// int count = sd.searchVCount();// �����������
	//
	// int yeMa = 5;
	//
	// Pagination p = new Pagination(ye, count, yeNum, yeMa);
	//
	// request.setAttribute("p", p);
	// List<Subject> list = sd.searchPage(p.getNumber(), yeNum);
	// request.setAttribute("list", list);
	// request.getRequestDispatcher("subjectView.jsp").forward(request,
	// response);
	// }

	public void addSubject(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("name");

		Subject subject = new Subject();
		subject.setName(name);
		int flag = subd.add(subject);
		if (flag > 0) {
			System.out.println("��ӳɹ�");
			response.sendRedirect("showSubject?type=selectSubject");
		} else {
			System.out.println("���ʧ��");
		}
	}

	public void selectSubject(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		int ye = 1;// ��ʼ��ye(��ҳ��ҳ��)����,
		int yeNum = 5; // ������yeNum(��ҳ��ÿҳ������)
		int yeMa = 5;
		String name = "";
		if (null != request.getParameter("ye")) {
			ye = Integer.parseInt(request.getParameter("ye"));
		}

		if (request.getParameter("name") != null) {
			name = request.getParameter("name");
			session.setAttribute("SubjectName", name);
		}
				

		Subject subject = new Subject();
		subject.setName(name);

		int count = subd.searchCount(subject);// �����������
		
		Pagination p = new Pagination(ye, count, yeNum, yeMa);
		
		List<Subject> list = subd.searchPageOne(p.getNumber(), p.getYeNum(), subject);
		request.setAttribute("p", p); 
		request.setAttribute("list", list);
//		System.out.println(list.get(1).getName());
		request.getRequestDispatcher("WEB-INF/subject/subjectView.jsp").forward(
				request, response);

	}

	public void addView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/subject/addSubjectView.jsp").forward(request,
				response);
	}

	public void alterSubject(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String idType = request.getParameter("id");
		int id = 0;
		if (!idType.equals("")) {
			id = Integer.parseInt(idType);
		}

		String name = request.getParameter("name");

		Subject subject = new Subject();
		subject.setId(id);
		subject.setName(name);
		int flag = subd.motify(subject);
		if (flag > 0) {
			response.sendRedirect("showSubject?type=selectSubject");
		} 
	}

	public void deleteSubject(HttpServletRequest request,
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
			flag += subd.dalete(id);
		}
		if (flag == idType.length) {
			System.out.println("�ɹ�");
		} else {
			System.out.println("ʧ��");
		}
		response.sendRedirect("showSubject?type=selectSubject");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}
}
