package myStuff.dao.ejb;

public interface UtilDao {
	
	
	public boolean IsValidEmail(String email) ;
	public boolean IsUniqueUser(String email, String password);

}
