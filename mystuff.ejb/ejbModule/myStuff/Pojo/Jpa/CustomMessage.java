package myStuff.Pojo.Jpa;



public class CustomMessage {
	private String message;
	private boolean flag ;
	private String additionalAtt ; 

	public CustomMessage() {
	}

	public CustomMessage(String message, boolean flag, String additionalAtt) {
		super();
		this.message = message;
		this.flag = flag;
		this.additionalAtt = additionalAtt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getAdditionalAtt() {
		return additionalAtt;
	}

	public void setAdditionalAtt(String additionalAtt) {
		this.additionalAtt = additionalAtt;
	}

}