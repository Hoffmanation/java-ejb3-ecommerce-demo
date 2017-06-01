package myStuff.dao.ejb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import myStuff.DaoBean.ejb.ProType;
import myStuff.Pojo.Jpa.Customer;
import myStuff.Pojo.Jpa.Message;
import myStuff.Pojo.Jpa.Order;
import myStuff.Pojo.Jpa.Product;
import myStuff.service.util.MyStuffException;

@Remote
public interface CustomerDao {

	public String signUp(String name, String f_name,String password, String email) throws MyStuffException;

	public boolean updateCustomer(Customer customer, String password, String email) throws MyStuffException;

	public List<Order> getCustomerOrderById(int CustomerId);
	
	public List<Product> getCurrProducts(List<Product> currProd,ProType type);

	public boolean login(String email, String password);

	public Product getProductByName(String name);

	public List<Product> getAllProducts();

	public List<Product> getAllProductByPriceEx();

	public List<Product> getAllProductByPriceChe();
	
	public List<Product> getAllProductsByType(ProType type) ; 

	public List<Product> getProductArrayById(int id);
	
	public Product getProductById(int id) throws MyStuffException;
	
	public Customer getCustomerByName(String name);
	
	public Customer getCustomerByEmail(String email);
	
	public Customer getCustomerInfo(Customer customer) ;
	
	public List<Product> searchProductByName(String name) throws SQLException ; 
	
	public List<Product> searchProductByPrice(double minPrice , double maxPrice ) throws SQLException ; 
	


}
