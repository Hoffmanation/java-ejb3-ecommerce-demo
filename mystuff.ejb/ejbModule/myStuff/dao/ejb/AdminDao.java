package myStuff.dao.ejb;

import java.util.List;

import javax.ejb.Remote;

import myStuff.DaoBean.ejb.ProType;
import myStuff.Pojo.Jpa.Customer;
import myStuff.Pojo.Jpa.Customer_order;
import myStuff.Pojo.Jpa.Order;
import myStuff.Pojo.Jpa.Product;
import myStuff.service.util.MyStuffException;

@Remote
public interface AdminDao {
	
	public Customer createCustomer(Customer customer) throws MyStuffException;
	
	public boolean createProduct(Product product) throws MyStuffException;

	public Product createProduct(String name, String description, String imagePath, int price, ProType protype,int quantity)throws MyStuffException;

	public boolean updateProduct(int id, String description, String imagePath, int price, int quantity)throws MyStuffException;

	public boolean removeProduct(int id)throws MyStuffException;
	
	public boolean uploadImage() throws MyStuffException;

	public Product getProductById(int id)throws MyStuffException;

	public List<Customer> getAllCutsomers()throws MyStuffException;
	
	public Customer getCustomerById(int id) throws MyStuffException;
	
	public Customer getCustomerByName(String name)throws MyStuffException;
	
	public boolean removeCustomer(int id)throws MyStuffException;

	public  List<Customer_order> getCustomerOrderById(int customerId) throws MyStuffException;

	public Order getOrderById(int id) throws MyStuffException;

	public boolean login(String userName, String password)throws MyStuffException;

}
