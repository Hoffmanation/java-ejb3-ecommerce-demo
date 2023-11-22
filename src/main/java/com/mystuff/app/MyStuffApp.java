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
	private ProductDaoImpl productDaoStub;

	@PostConstruct
	public void init() {
		logg.info("MyStuff Init applicaiton...");
		Utilities.initializeDB(productDaoStub);
		logg.info("MyStuff Init applicaiton finished");
	}

}
