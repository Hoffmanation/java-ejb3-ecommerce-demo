package myStuff.Pojo.Jpa;

public class SignupModel {
	
	private String name ;
	private String f_name ;
	private String password ;
	private String secondPassword ;
	private String email ;
	
	public SignupModel() {
		// TODO Auto-generated constructor stub
	}

	public SignupModel(String name, String f_name, String password, String secondPassword, String email) {
		super();
		this.name = name;
		this.f_name = f_name;
		this.password = password;
		this.secondPassword = secondPassword;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecondPassword() {
		return secondPassword;
	}

	public void setSecondPassword(String secondPassword) {
		this.secondPassword = secondPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



}
