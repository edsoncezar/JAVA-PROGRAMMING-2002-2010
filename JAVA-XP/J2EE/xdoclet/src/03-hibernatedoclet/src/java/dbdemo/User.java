package dbdemo;

import java.util.Date;
import java.util.Set;

/**
 * @author MEagle
 *
 * @hibernate.class table="Users"
 *
 * Represents a User
 */
public class User {

	private String userID;
	private String userName;
	private String password;
	private String emailAddress;
	private Date lastLogon;
	private Set contacts;
	private Set books;
	private Address address;

	/**
	 * @hibernate.property column="EmailAddress" type="string"
	 * @return
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @hibernate.property column="LastLogon" type="date"
	 * @return
	 */
	public Date getLastLogon() {
		return lastLogon;
	}

	/**
	 * @hibernate.property column="Password" type="string"
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @hibernate.id generator-class="assigned" type="string"
	 * 				column="LogonID"
	 * @return
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @hibernate.property column="Name" type="string"
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param string
	 */
	public void setEmailAddress(String string) {
		emailAddress = string;
	}

	/**
	 * @param string
	 */
	public void setLastLogon(Date date) {
		lastLogon = date;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @param string
	 */
	public void setUserID(String string) {
		userID = string;
	}

	/**
	 * @param string
	 */
	public void setUserName(String string) {
		userName = string;
	}

	/**
	 * @hibernate.set role="contacts" table="Contacts"  
	 * 				  cascade="all" readonly="true"
	 * @hibernate.collection-key column="User_ID"
	 * @hibernate.collection-one-to-many class="dbdemo.Contact"
	 * @return
	 */
	public Set getContacts() {
		return contacts;
	}

	/**
	 * @param set
	 */
	public void setContacts(Set set) {
		contacts = set;
	}

	/**
	 * @hibernate.set role="books" table="Book_User_Link" 
	 * 					cascade="all" readonly="true"
	 * @hibernate.collection-key column="UserID"
	 * @hibernate.collection-many-to-many 
	 * 					class="dbdemo.Book" column="BookID"
	 * @return
	 */
	public Set getBooks() {
		return books;
	}

	/**
	 * @param set
	 */
	public void setBooks(Set set) {
		books = set;
	}

	/**
	 * @hibernate.one-to-one class="dbdemo.Address"
	 * @return
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
}
