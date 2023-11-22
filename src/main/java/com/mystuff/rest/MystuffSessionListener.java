package com.mystuff.rest;
import java.util.Enumeration;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MystuffSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		HttpSession session  =arg0.getSession()  ;
		System.out.println("session created:" + session.getId());
		Enumeration<String> attributes = session.getAttributeNames();
		while (attributes.hasMoreElements()) {
		    String attribute = (String) attributes.nextElement();
		    System.out.println(attribute+" : "+session.getAttribute(attribute));
		}
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session  =arg0.getSession()  ;
		Enumeration<String> attributes = session.getAttributeNames();
		while (attributes.hasMoreElements()) {
		    String attribute = (String) attributes.nextElement();
		    System.out.println(attribute+" : "+session.getAttribute(attribute));
		}
	}



}