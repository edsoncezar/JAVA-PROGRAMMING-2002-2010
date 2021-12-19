package com.oreilly.struts.banking.view;
import java.util.Set;
import java.util.HashSet;
/**
 * A value object for that wraps all of the user's security information
 */
public class UserView implements java.io.Serializable {
  private String id;
  private String lastName;
  private String firstName;

  // A unique collection of permission String objects
  private Set permissions = new HashSet();

 /**
  * Constructors
  */
  public UserView(String first, String last) {
    this(first, last, new HashSet());
  }

  public UserView(String first, String last, Set userPermissions) {
    super();
    firstName = first;
    lastName = last;
    permissions = userPermissions;
  }
  /**
   * Returns true if the argument is among the collection of
   * permissions allowed for the user. Otherwise returns
   * false.
   */
  public boolean containsPermission(String permissionName) {
    return permissions.contains(permissionName);
  }
  /**
   * Retrieve the last name of the user
   */
  public String getLastName() {
    return lastName;
  }
  /**
   * Set the last name of the user
   */
  public void setLastName(String name) {
    lastName = name;
  }
  /**
   * Retrieve the first name of the user
   */
  public String getFirstName() {
    return firstName;
  }
  /**
   * Set the first name of the user
   */
  public void setFirstName(String name) {
    firstName = name;
  }
 /**
  * Retrieve the id of the user
  */
  public String getId() {
    return id;
  }
  /**
   * Set the id of the user
   */
  public void setId(String id) {
    id = id;
  }
}