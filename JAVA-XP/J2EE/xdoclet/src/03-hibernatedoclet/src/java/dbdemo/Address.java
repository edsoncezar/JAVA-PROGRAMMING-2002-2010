package dbdemo;

/**
 * @author MEagle
 *
 * @hibernate.class table="Address2"
 *
 * Represents an address
 */
public class Address {
	private String id;
	private String city;
	private String state;
	private String zip;
	
	/**
	 * @hibernate.property column="City" type="string"
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @hibernate.id generator-class="assigned" type="string" column="ID"  
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @hibernate.property column="State" type="string"
	 * @return
	 */
	public String getState() {
		return state;
	}

	/**
	 * @hibernate.property column="Zip" type="string"
	 * @return
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param string
	 */
	public void setCity(String string) {
		city = string;
	}

	/**
	 * @param string
	 */
	public void setId(String string) {
		id = string;
	}

	/**
	 * @param string
	 */
	public void setState(String string) {
		state = string;
	}

	/**
	 * @param string
	 */
	public void setZip(String string) {
		zip = string;
	}
}
