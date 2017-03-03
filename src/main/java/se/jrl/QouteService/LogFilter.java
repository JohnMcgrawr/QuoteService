package se.jrl.QouteService;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.concurrent.ForkJoinPool;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "log-filter")
public class LogFilter implements Filter {


	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		StringBuilder stringBuilder = new StringBuilder();
		Timestamp timestamp = new Timestamp(new java.util.Date().getTime());

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
 		         
		chain.doFilter(httpServletRequest, httpServletResponse);
		
		         stringBuilder.append("Requested URL : " + httpServletRequest.getRequestURL()).append("\n")
					.append("Method used in request : " + httpServletRequest.getMethod()).append("\n")
					.append("Status code : " + httpServletResponse.getStatus()).append("\n")
					.append("Execution time : " + timestamp).append("\n");
		         
		         System.out.println(stringBuilder.toString());
	}

	@Override
	public void destroy() {

	}

}

// URL för requesten
// - HTTP method (GET, PUT, POST, etc)
// - Tidpunkt
// - Response status code
