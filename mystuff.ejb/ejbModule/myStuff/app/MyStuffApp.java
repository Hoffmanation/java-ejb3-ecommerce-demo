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
					"Lenovo - Edge 2 15.6\" 2-in-1 Touch-Screen Laptop - Intel Core i7 - 8GB Memory - 1TB Hard Drive",
					"images/computer.jpg", 10, 0);

			Product hp = new Product("HP Color Laser Jet 1025NW w/2", 22, ProType.TECHNOLOGY,
					"Geared with HP ePrint feature, the LaserJet Pro CP1025nw printer gives an easy printing experience from any place. .",
					"images/printer.jpg", 10, 0);

			Product jacket = new Product("VTG 70's leather motorcycle", 66, ProType.CLOTHING,
					"Vintage motorcycle leather jacket 60's Clothing", "images/jacket.jpg", 20, 0);

			Product ref = new Product("New Portable Refrigerator", 200, ProType.APPLIANCES,
					"New Portable Refrigerator 4 Liter Mini Cooler & Warmer, Cosmetic Fridge Pink 4 Liter Cooler & Warmer [Hot & Cold] for Cosmetic, Anywhere , for Automobile",
					"images/fridge.jpg", 20, 0);

			Product bull = new Product("Real Hand Carved Cow Skull & Horns", 66, ProType.DECORATION,
					"This authentic cow skull with the intricate carving was hand crafted in Bali, the \"Island of the Gods\", Indonesia. Balinese Master Artim",
					"images/bull.jpg", 10, 0);

			Product table = new Product("Whole wood coffee table from switz wood", 77, ProType.FURNITURE,
					"A beautifully hand made mosaic  IKEA wood table / stool / plant standStacking (if you have more than 1)Japanese styleColours: Red White Black and Mirror glass tilesSquare round star designRed iridesce",
					"images/table.jpg", 222, 0);

			Product sofa = new Product("New Hot 9 Solid Pure Colour Lounge Couch", 666, ProType.FURNITURE,
					"Hot Sofa Cover Stretch Slipcover Sofa Cover Removable Sofa Protector FREE Pillowcase",
					"images/sofa.jpg", 8, 0);

			Product tread = new Product("Goplus 500W Folding Electric Treadmill", 500, ProType.SPORT,
					"Good condition but belt does make a noise as it moves around - may be fixable but I've not really used it much despite buying for £400 a few years back! ",
					"images/tread.jpg", 1, 0);

			Product piano = new Product("Yamaha U3 52'' Studio Upright Piano", 500, ProType.MUSIC,
					"The U3 is Yamaha's full-size \"flagship\" studio upright piano. With a string length equal to many baby grand pianos, it is an ideal choice for professional, school, studio, and home use.  Yamaha U-series pianos are phenomenal choices for ",
					"images/yamaha.jpg", 1, 0);

			Product xbox = new Product("Xbox One S Console \"sky\" Edition", 260, ProType.TECHNOLOGY,
					"Microsoft's Xbox One S console is sleeker and more advanced than previous models. It features 4K Ultra HD video, High Dynamic Range, a 40% smaller console, and a sleeker controller. Stream 4K content on Netflix and Amazon Instant Video",
					"images/xbox.jpg", 1, 0);

			Product closet = new Product("ClosetMaid 1608 5ft. to 8 Ft. Closet Organizer Kit, White", 150,ProType.FURNITURE,
					"All of the 5 feet to 8 feet. Fixed Mount Closet Organizer Kits include all of the necessary pieces that you need to build the closet of your dreams. The kit includes wire shelving, brackets, closet rods, support rod, easy instalation",
					"images/closet.jpg", 1, 0);
			
		Product heater = new Product("Vermont Castings Encore Flexburn", 460,ProType.APPLIANCES,
					"This the oven of the highest class available in the U.S. Burning on one bookmark - up to 12 hours, will continue to heat the heat storage for a few hours after the fire goes out in the furnace. Encore model - the most popular version",
					"images/heater.jpg", 1, 0);
		
		Product dress = new Product("Heloise Women's A-Line Sleeveless Pleated Little Cocktail Party Dress", 90,ProType.CLOTHING,
				"This dress is made of high quality stretchy fabric. Héloïse Fashion's priority is to make stylish, beautiful and durable dresses with absolutely no compromise on quality.",
				"images/dress.jpg", 1, 0);
		
		Product shoes = new Product("Adidas Originals ARD1 Low Men's Court Sneakers Shoes", 50,ProType.CLOTHING,
				"Made with flexible canvas upper for everyday comfort Lace up front for a customizable fit Padded collar and tongue for added comfort Contrast 3-Stripes at sides for a signature look Rubber outsole for grip green colored M size",
				"images/shoes.jpg", 1, 0);
		
		Product belt = new Product("Men Women Tree Belt Buckle Round Silver Metal 60's Item", 150,ProType.CLOTHING,
				"Tree Silver Metal Round Belt Buckle - for everyday wear work or a fashion accessory for a party , Made from strong quality matirial",
				"images/beltB.JPG", 1, 0);
		
		Product dumbbell = new Product("Gold's Gym Dumbbell Power Set", 210,ProType.SPORT,
				"Add muscle tone to your body with this Gold's Gym Dumbbell Set. The power dumbbell weight set includes one pair of 3 lb dumbbells, one pair of 5 lb weights and one pair of 8 lb weights.",
				"images/bumbbell.jpeg", 1, 0);
		
		Product golf = new Product("Cobra KING F7 ONE Length Irons golf club", 785,ProType.SPORT,
				"The KING F7 ONE LENGTH irons debut as COBRA’s first ever single-length iron set. Inspired by rising PGA star Bryson DeChambeau",
				"images/golf.jpg", 1, 0);
		
		Product picture = new Product("Dali's The Metamorphosis of Narcissus", 115,ProType.DECORATION,
				"Flamboyant and groundbreaking Spanish artist Salvador Dali is considered one of the 20th century’s greatest artists. An icon of the Surrealist movement his blend of eccentric perspectives and controversial influences.",
				"images/picture.jpg", 1, 0);
		
		Product marshall = new Product("Marshall JVM 100W Stack head", 115,ProType.MUSIC,
				"This used Marshall JVM 100W Stack head and cab combo is in Excellent condition and a real powerhouse of an amp. You've got 100 watts of blistering power here plus an incredible array of effects at hand.",
				"images/marshall.jpg", 1, 0);
		
		Product vacum = new Product("Panasonic MC-CG917 Vacuum Cleaner", 115,ProType.APPLIANCES,
				"Clean with ease using this MC-CG917 \"bag\" canister vacuum cleaner by Panasonic. Equipped with a powerful 12 amp system, the vacuum cleaner can handle everything from a quick once over before company arrives.",
				"images/vacum.jpg", 1, 0);
		
		
		Product lion = new Product("Bronze metal lion sculpture handmade", 2500,ProType.DECORATION,
				"This Lion sculpture hand made by the famous artist \"Magneto\" is one of only 3 he made, Quality bronze metal material will last forever ",
				"images/lion.jpg", 1, 0);
		
		Product hat = new Product("Jacaru ‘Wallaroo Suede’ Hat", 70,ProType.CLOTHING,
				"Medium Size Brown Colored  Cowhide Material Braided Leather Trim, Water Repellent Brim3 inch Shape,Aussie",
				"images/hat.jpg", 1, 0);
		
		Product card = new Product("GeForce GTX 1080 Graphics Card", 70,ProType.TECHNOLOGY,
				"Play the world and incredible definition with the GeForce GTX 1080 graphics card. Designed to cope with 4K gaming, high frame rates and exquisite detail, the Pascal GPU architecture takes the GDDR5 memory inside this card to a whole new level.",
				"images/card.JPG", 1, 0);
		
		Product mirror = new Product("Juliette Teardrop Oval Mirror", 55,ProType.DECORATION,
				"With generous curves and a name that means “young,” our Juliette mirror will inspire a certain flirtatious joy in anyone who gazes upon it. The antiqued frame scrolls elegantly from top to bottom, with an overall gilded appeal not unlike a royal vanity.",
				"images/mirror.jpg", 1, 0);
		
		

			// -- Dummy Customers
			Customer oren = new Customer("Oren", "Hoffman", "oren", "oren@gmail.com");
			Customer lena = new Customer("lena", "Broid", "lena", "lena@gmail.com");
			Customer nela = new Customer("Nela", "Dogmeat", "nela", "nela@gmail.com");
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
			adminStub.createProduct(piano);
			adminStub.createProduct(xbox);
			adminStub.createProduct(closet);
			adminStub.createProduct(heater);
			adminStub.createProduct(dress);
			adminStub.createProduct(shoes);
			adminStub.createProduct(belt);
			adminStub.createProduct(dumbbell);
			adminStub.createProduct(golf);
			adminStub.createProduct(picture);
			adminStub.createProduct(marshall);
			adminStub.createProduct(vacum);
			adminStub.createProduct(lion);
			adminStub.createProduct(hat);
			adminStub.createProduct(card);
			adminStub.createProduct(mirror);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
