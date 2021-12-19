package dbdemo;

/**
 * @author MEagle
 *
 * @hibernate.class  table="Contacts"
 * Represents a contact.
 */
public class Contact {

	private long contactId;
	private String name;
	private String email;

	//	foreign key field for the parent
	private String userId;

	// relationship field
	private User user;

	/**
	 * @--hibernate.id generator-class="sequence" type="long" column="ID"
	 * @--hibernate.generator-param name="sequence" value="seq"	
	 * @hibernate.id generator-class="native" type="integer" column="ID"
	 * 
	 * @return long
	 */
	public long getContactId() {
		// use this if you are using a dialect that does not support sequences
		// @hibernate.id generator-class="native" type="integer" column="ID"	
		
		// Use this if you are using a dialect that supports sequences
		// @hibernate.id generator-class="sequence" type="long" column="ID"
		// @hibernate.generator-param name="sequence" value="seq"	
		return contactId;
	}

	/**
	 * @hibernate.property column="EmailAddress" type="string"
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @hibernate.property column="Name" type="string"
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param long
	 */
	public void setContactId(long l) {
		contactId = l;
	}

	/**
	 * @param string
	 */
	public void setEmail(String string) {
		email = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}



	/**
	 * @return String
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param string
	 */
	public void setUserId(String string) {
		userId = string;
	}

	/**
	 * @hibernate.many-to-one column="User_ID" class="dbdemo.User"
	 * @return User
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
