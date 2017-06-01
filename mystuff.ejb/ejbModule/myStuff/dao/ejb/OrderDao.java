package myStuff.dao.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import myStuff.Pojo.Jpa.Order;
import myStuff.service.util.MyStuffException;

@Remote
public interface OrderDao {
public boolean CreateOrder(Order order) throws MyStuffException ; 
}
