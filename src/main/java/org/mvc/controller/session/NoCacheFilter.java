package org.mvc.controller.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class NoCacheFilter implements Filter {

	private int counter;

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filter)
			throws IOException, ServletException {
		HttpServletResponse hsr = (HttpServletResponse) res;
		System.out.println("In Flter :: isLogin >> " + req.getAttribute("IsLogin"));
		hsr.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP // 1.1.
		hsr.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		hsr.setDateHeader("Expires", 0); // Proxies.
		counter = 0;

		System.out.println(">>in doFilter >> IsLogin>>"+req.getAttribute("IsLogin")+">>counter>>"+counter);
		if ("true".equals(req.getAttribute("IsLogin")) && req.getAttribute("IsLogin") != null || counter == 0) {
			filter.doFilter(req, res);
			counter ++;
		} else if (req.getAttribute("IsLogin") == null) {
			try {
				throw new Exception("Invalid user");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

}