package myStuff.service.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

import myStuff.Pojo.Jpa.Customer;
import myStuff.dao.ejb.UtilDao;

@Stateless
public class Utilities implements UtilDao, Serializable {
	private static final long serialVersionUID = 5808932481091875959L;

	@PersistenceContext(unitName = "mystuff")
	private EntityManager manager;

	@Override
	public boolean IsValidEmail(String email) {
		if (email.contains("@") && email.contains(".")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean IsUniqueUser(String email, String password) {
			List<Customer> customers =  manager.createNamedQuery("login", Customer.class).setParameter("password", password).setParameter("email", email).getResultList();
			if (customers.isEmpty()){
				return true ;
				}
			return false ;
			}

	


}
