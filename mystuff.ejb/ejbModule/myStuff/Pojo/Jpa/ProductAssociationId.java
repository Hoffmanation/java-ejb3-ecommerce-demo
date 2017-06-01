package myStuff.Pojo.Jpa;

import java.io.Serializable;

public class ProductAssociationId implements Serializable {

	private static final long serialVersionUID = -4808978909167200869L;

	private int customerId;

	private int orderId;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customerId;
		result = prime * result + orderId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductAssociationId other = (ProductAssociationId) obj;
		if (customerId != other.customerId)
			return false;
		if (orderId != other.orderId)
			return false;
		return true;
	}

}