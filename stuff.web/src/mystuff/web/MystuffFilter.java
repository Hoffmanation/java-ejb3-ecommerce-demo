//package mystuff.web;
//
//
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.ws.rs.core.MediaType;
//
////please read first the 'readMe' file in the webContent folder
//
//
//@WebFilter(urlPatterns = "/rest/*")
//public class MystuffFilter implements Filter {
//
//	public MystuffFilter() {
//	}
//
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//
//		String url = ((HttpServletRequest) request).getRequestURI();
//
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//
//		System.out.println("url before: " + url);
//		url = url.substring("/couponet.web/rest".length());
//		System.out.println("url after" + url);
//
//		if (checkValidSession(req, url)) {
//
//			System.out.println("sessionvalidation");
//			System.out.println(";jsessionid=" + req.getSession().getId());
//			chain.doFilter(request, response);
//
//		} else {
//			
//			res.setStatus(500);
//			res.getWriter().println("{\"error\":\"please log in first ! \"}");
//			res.setContentType(MediaType.APPLICATION_JSON);
//
//		}
//
//	}
//
//	private boolean checkValidSession(HttpServletRequest req , String url){
//		
//		HttpSession session =req.getSession(false);
//		
//		if(url.startsWith("/admin")){
//			if(url.startsWith("/admin/login")) {
//				return true;
//			}else if (session == null || session.getAttribute("adminf")== null){
//				return false;
//			}else{
//				
//				return true;
//			}
//		}
//		
//
//		
//		if(url.startsWith("/customer")){
//			if(url.startsWith("/customer/login")) {
//				
//				return true;
//			}else if (session == null || session.getAttribute("custf")== null||session.getAttribute("cart")== null){
//				return false;
//			}else{
//				return true;
//			}
//		}
//		
//		return true;
//			
//		
//		
//	}
//
//	public void init(FilterConfig fConfig) throws ServletException {
//
//	}
//
//	public void destroy() {
//	}
//}
