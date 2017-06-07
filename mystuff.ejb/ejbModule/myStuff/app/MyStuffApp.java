package myStuff.app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.naming.Context;

import myStuff.DaoBean.ejb.ProType;
import myStuff.Pojo.Jpa.Customer;
import myStuff.Pojo.Jpa.Order;
import myStuff.Pojo.Jpa.Product;
import myStuff.dao.ejb.AdminDao;
import myStuff.dao.ejb.CartDao;
import myStuff.dao.ejb.CustomerDao;
import myStuff.dao.ejb.OrderDao;
import myStuff.dao.ejb.WishlistDao;

@Singleton
@Startup
public class MyStuffApp {
	@EJB
	private WishlistDao wishlist;
	@EJB
	private OrderDao orderStub;
	@EJB
	private CartDao cartStub;
	@EJB
	private CustomerDao custStub;	
	@EJB
	private AdminDao adminStub;

	@PostConstruct
	public void init() {
		try {
		// -- products
		Product lenovo = new Product("Lenovo Edge 2 I7", 200, ProType.TECHNOLOGY,
				"Levono - Edge 2 15.6\" 2-in-1 Touch-Screen Laptop - Intel Core i7 - 8GB Memory - 1TB Hard Drive",
				"images/computer.jpg", 10,0);
		Product hp = new Product("HP Color Laser Jet 1025NW w/2", 22, ProType.TECHNOLOGY,
				"Geared with HP ePrint feature, the LaserJet Pro CP1025nw printer gives an easy printing experience from any place. .",
				"images/printer.jpg", 10,0);
		Product jacket = new Product("VTG 70's leather motorcycle", 66, ProType.CLOTHING,
				"Vintage motorcycle leather jacket 60's Clothing", "images/jacket.jpg", 20,0);

		Product ref = new Product("New Portable Refrigerator", 200, ProType.APPLIANCES,
				"New Portable Refrigerator 4 Liter Mini Cooler & Warmer, Cosmetic Fridge Pink 4 Liter Cooler & Warmer [Hot & Cold] for Cosmetic, Anywhere , for Automobile",
				"images/fridge.jpg", 20,0);
		Product bull = new Product("REAL HAND CARVED COW SKULL & HORNS", 66, ProType.DECORATION,
				"This authentic cow skull with the intricate carving was hand crafted in Bali, the \"Island of the Gods\", Indonesia. Balinese Master Artim",
				"images/bull.jpg", 10,0);
		Product table = new Product("Whole wood coffee table from switz wood", 77, ProType.FURNITURE,
				"A beautifully hand made mosaic  IKEA wood table / stool / plant standStacking (if you have more than 1)Japanese styleColours: Red White Black and Mirror glass tilesSquare round star designRed iridesce",
				"images/table.jpg", 222,0);
		Product sofa = new Product("New Hot 9 Solid Pure Colour Lounge Couch", 666, ProType.FURNITURE,
				"Hot Sofa Cover Stretch Slipcover Sofa Cover Removable Sofa Protector FREE Pillowcase",
				"images/sofa.jpg", 8,0);
		Product tread = new Product("Goplus 500W Folding Electric Treadmill", 500, ProType.SPORT,
				"Good condition but belt does make a noise as it moves around - may be fixable but I've not really used it much despite buying for £400 a few years back! ",
				"images/tread.jpg", 1,0);
		// -- customers
		Customer oren = new Customer("Oren", "Hoffman", "oren", "oren@gmail.com");
		Customer lena = new Customer("lena", "Broid", "lena", "lena@gmail.com");
		Customer nela = new Customer("Nela", "Dogmeat", "nela", "lena@gmail.com");
		adminStub.createCustomer(nela);
		adminStub.createCustomer(oren);
		adminStub.createCustomer(lena);
		adminStub.createProduct(tread);
		adminStub.createProduct(sofa);
		adminStub.createProduct(table);
		adminStub.createProduct(bull);
		adminStub.createProduct(ref);
		adminStub.createProduct(jacket);
		adminStub.createProduct(hp);
		adminStub.createProduct(lenovo);

	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
