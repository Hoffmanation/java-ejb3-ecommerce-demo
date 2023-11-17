package com.mystuff.app;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jboss.logging.Logger;

import com.mystuff.dao.impl.CustomerDaoImpl;
import com.mystuff.dao.impl.ProductDaoImpl;
import com.mystuff.util.Utilities;

@Singleton
@Startup
public class MyStuffApp {
	
	private static final Logger logg = Logger.getLogger(MyStuffApp.class);
	
	@EJB
	private ProductDaoImpl  productDaoStub;
	@EJB
	private CustomerDaoImpl  customerDaoStub;

	@PostConstruct
	public void init() {
		try {
			logg.info("MyStuff Init applicaiton...");
			Utilities.initializeDB(productDaoStub);
			Utilities.initializeUserDB();
			logg.info("MyStuff Init applicaiton finished");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logg.info("MyStuff application started");
	}

}
