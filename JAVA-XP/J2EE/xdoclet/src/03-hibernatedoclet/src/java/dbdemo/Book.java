package dbdemo;

import java.util.Set;

/**
 * @author MEagle
 *
 * @hibernate.class table="Books"
 *
 */
public class Book {
	private long id;
	private String name;
	private Set users;

	/**
	 * @hibernate.id generator-class="vm" type="long" column="ID"
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * @hibernate.property column="Name" type="string"
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param l
	 */
	public void setId(long l) {
		id = l;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @hibernate.set role="users" table="Book_User_Link" cascade="all"
	 * @hibernate.collection-key column="BookID"
	 * @hibernate.collection-many-to-many class="dbdemo.User" column="UserID"
	 * @return
	 */
	public Set getUsers() {
		return users;
	}

	/**
	 * @param set
	 */
	public void setUsers(Set set) {
		users = set;
	}

}
