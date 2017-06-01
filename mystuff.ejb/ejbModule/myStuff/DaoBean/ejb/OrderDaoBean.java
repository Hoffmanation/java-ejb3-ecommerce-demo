package myStuff.DaoBean.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import myStuff.Pojo.Jpa.Customer;
import myStuff.Pojo.Jpa.Order;
import myStuff.dao.ejb.OrderDao;
import myStuff.service.util.MyStuffException;


@Stateless
public class OrderDaoBean implements OrderDao {

	@PersistenceContext(unitName = "mystuff")
	private EntityManager em;
	
	
	@Override
	public boolean CreateOrder(Order order) throws MyStuffException {
		if(order!=null){
			em.persist(order);
			return true ;
		}
		else {
			throw new MyStuffException("Invalid Order entity");
		}
	}


}
