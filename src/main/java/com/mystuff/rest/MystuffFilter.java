//package com.mystuff.rest;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Enumeration;
//import java.util.HashSet;
//import java.util.Set;
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
//import com.mystuff.obj.dto.CustomerDTO;
//import com.mystuff.util.AppConstants;
//
//@WebFilter(urlPatterns = "/rest/*")
//public class MystuffFilter implements Filter {
//
//	Set<String> publicResources;
//
//	public MystuffFilter() {
//		publicResources = new HashSet<>(
//				Arrays.asList("mystuff/images", "mystuff/js", "/customer/login", "/customer/signUp" , "customer/getAllProducts"));
//
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
//		url = url.substring("/mystuff/rest".length());
//		System.out.println("url after" + url);
//
//		if (requestIsValid(req, url)) {
//
//			System.out.println("sessionvalidation");
//			System.out.println(";jsessionid=" + req.getSession().getId());
//			chain.doFilter(request, response);
//
//		} else {
//			res.setStatus(500);
//			res.getWriter().println("{\"error\":\"please log in first ! \"}");
//			res.setContentType(MediaType.APPLICATION_JSON);
//		}
//	}
//
//	private boolean requestIsValid(HttpServletRequest req, String url) {
//		// User Already logged in
//		HttpSession session = req.getSession(false);
//		if (session != null) {
//			Enumeration<String> attributes = session.getAttributeNames();
//			while (attributes.hasMoreElements()) {
//			    String attribute = (String) attributes.nextElement();
//			    System.out.println(attribute+" : "+req.getSession().getAttribute(attribute));
//			}
//			CustomerDTO logedinCustomer = (CustomerDTO) session.getAttribute(AppConstants.CUSTOMER_SESSION_ATTR);
//			if (logedinCustomer != null) {
//				return true;
//			}
//		}
//
//		// User isn't login but trying to access public resources
//		if (urlIsPublicResource(url)) {
//			return true;
//		}
//
//		return false;
//	}
//
//	private boolean urlIsPublicResource(String url) {
//		return publicResources.stream().anyMatch(resource -> url.contains(resource));
//	}
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void init(FilterConfig arg0) throws ServletException {
//		// TODO Auto-generated method stub
//	}
//}
