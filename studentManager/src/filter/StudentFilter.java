package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StudentFilter implements Filter {
	// 销毁
	@Override
	public void destroy() {

	}

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// HttpSession session=((HttpServletRequest)request).getSession();
		//
		// if(session.getAttribute("user")==null){
		// ((HttpServletResponse)response).sendRedirect("user");
		//
		// }else{
		// chain.doFilter(request, response);
		// }
		// 设置request后台获得数据编码为'utf-8'
		request.setCharacterEncoding("utf-8");
		// 设置response后台传输回前端的编码为utf-8
		response.setContentType("text/html;charset=UTF-8");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
