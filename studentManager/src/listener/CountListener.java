package listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import util.CountMessageUtil;

public class CountListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		ServletContext application = se.getSession().getServletContext();
		int num = 0;
		if(application.getAttribute("num")!=null){
			num = (Integer)application.getAttribute("num");
			
		}
		if(se.getSession().isNew()){
			num++;
		}
		CountMessageUtil.sendCountMessage(num);
		 application.setAttribute("num", num);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
