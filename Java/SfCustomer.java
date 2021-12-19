package beans;

import java.io.*;

/**
 * A SfCustomer object represents a business customer.
 * <p />
 * It maintains name and address information as well
 * as the customers login email and password.
 *
 * @author      WPI Java Group #1
 * @version     1.0
 * @since       1.4
 */
public class SfCustomer implements Serializable {

	// This objects database key
	private int Id;

	// Customer name
	private String Fname;
	private String Lname;

	// Customer address
	private String StreetAddress;
	private String City;
	private String State;
	private String Zip;

	// Customer phone number
	private String Phone;

	// Customer login email & password
	private String Email;
	private String Password;

	// HTML formatting for error labels
    private static String ERROR_PREFIX = "<font color=\"red\">";
    private static String ERROR_SUFFIX = "</font>";

	// Text labels used to prompt the user to input information
	// Each field has a text label, with good and error forms
	private static String FNAME_LABEL_GOOD  = "First Name";
	private static String FNAME_LABEL_ERROR = ERROR_PREFIX + FNAME_LABEL_GOOD + ERROR_SUFFIX;

	private static String LNAME_LABEL_GOOD  = "Last Name";
	private static String LNAME_LABEL_ERROR = ERROR_PREFIX + LNAME_LABEL_GOOD + ERROR_SUFFIX;

	private static String STREET_ADDRESS_LABEL_GOOD  = "Street Address";
	private static String STREET_ADDRESS_LABEL_ERROR = ERROR_PREFIX + STREET_ADDRESS_LABEL_GOOD + ERROR_SUFFIX;

	private static String CITY_LABEL_GOOD  = "City";
	private static String CITY_LABEL_ERROR = ERROR_PREFIX + CITY_LABEL_GOOD + ERROR_SUFFIX;

	private static String STATE_LABEL_GOOD  = "State";
	private static String STATE_LABEL_ERROR = ERROR_PREFIX + STATE_LABEL_GOOD + ERROR_SUFFIX;

	private static String ZIP_LABEL_GOOD  = "Zipcode";
	private static String ZIP_LABEL_ERROR = ERROR_PREFIX + ZIP_LABEL_GOOD + ERROR_SUFFIX;

	private static String PHONE_LABEL_GOOD  = "Phone Number";
	private static String PHONE_LABEL_ERROR = ERROR_PREFIX + PHONE_LABEL_GOOD + ERROR_SUFFIX;

	private static String EMAIL_LABEL_GOOD  = "Email Address";
	private static String EMAIL_LABEL_ERROR = ERROR_PREFIX + EMAIL_LABEL_GOOD + ERROR_SUFFIX;

	private static String PASSWORD_LABEL_GOOD  = "Password";
	private static String PASSWORD_LABEL_ERROR = ERROR_PREFIX + PASSWORD_LABEL_GOOD + ERROR_SUFFIX;

	private static String CONFIRM_PASSWORD_LABEL_GOOD  = "Confirm Password";
	private static String CONFIRM_PASSWORD_LABEL_ERROR = ERROR_PREFIX + CONFIRM_PASSWORD_LABEL_GOOD + ERROR_SUFFIX;

	// The labels for each field
	// Default to good form
	private String FnameLabel        	= FNAME_LABEL_GOOD;
	private String LnameLabel        	= LNAME_LABEL_GOOD;

	private String StreetAddressLabel	= STREET_ADDRESS_LABEL_GOOD;
	private String CityLabel         	= CITY_LABEL_GOOD;
	private String StateLabel        	= STATE_LABEL_GOOD;
	private String ZipLabel          	= ZIP_LABEL_GOOD;

	private String PhoneLabel        	= PHONE_LABEL_GOOD;

	private String EmailLabel        	= EMAIL_LABEL_GOOD;
	private String PasswordLabel		= PASSWORD_LABEL_GOOD;
	private String ConfirmPasswordLabel = CONFIRM_PASSWORD_LABEL_GOOD;

	/**
     * Initializes a newly created SfCustomer object
     * by resetting all fields.
     *
	 */
	public SfCustomer() {

		Id = -1;

		Fname = "";
		Lname = "";

		StreetAddress = "";
		City = "";
		State = "";
		Zip = "";

		Phone = "";

		Email = "";

		Password = "";

}


	/**
     * Returns whether this customer has logged in yet
     *
	 * @return	true if logged in, false if not
	 */
	public boolean isLoggedIn() {
		return (!(Id == -1));
	}


	/**
     * Sets the customers database ID
     *
     * @param  id   the ID
     */
	public void setId(int id) {
		Id = id;
	}

	/**
     * Returns the customers database ID
     *
	 * @return	the ID
	 */
	public int getId() {
		return Id;
	}

	/**
     * Sets the customers first name
     *
     * @param  fn    the first name
     */
	public void setFname(String fn) {
		Fname = fn;
	}

	/**
     * Returns the customers first name
     *
	 * @return	the first name
	 */
	public String getFname() {
		return Fname;
	}

	/**
     * Sets the customers last name
     *
     * @param  ln    the last name
     */
	public void setLname(String ln) {
		Lname = ln;
	}

	/**
     * Returns the customers last name
     *
	 * @return	the last name
	 */
	public String getLname() {
		return Lname;
	}

	/**
     * Sets the customers street address
     *
     * @param  addr    the street address
     */
	public void setStreetAddress(String addr) {
		StreetAddress = addr;
	}

	/**
     * Returns the customers street address
     *
	 * @return	the street address
	 */
	public String getStreetAddress() {
		return StreetAddress;
	}

	/**
     * Sets the customers city
     *
     * @param  city    the city
     */
	public void setCity(String city) {
		City = city;
	}

	/**
     * Returns the customers city
     *
	 * @return	the city
	 */
	public String getCity() {
		return City;
	}

	/**
     * Sets the customers state
     *
     * @param  state    the state
     */
	public void setState(String state) {
		State = state;
	}

	/**
     * Returns the customers state
     *
	 * @return	the state
	 */
	public String getState() {
		return State;
	}

	/**
     * Sets the customers zipcode
     *
     * @param  zip    the zipcode
     */
	public void setZip(String zip) {
		Zip = zip;
	}

	/**
     * Returns the customers zipcode
     *
	 * @return	the zipcode
	 */
	public String getZip() {
		return Zip;
	}

	/**
     * Sets the customers phone number
     *
     * @param  phone    the phone number
     */
	public void setPhone(String phone) {
		Phone = phone;
	}

	/**
     * Returns the customers phone number
     *
	 * @return	the phone number
	 */
	public String getPhone() {
		return Phone;
	}

	/**
     * Sets the customers email address
     *
     * @param  email    the email
     */
	public void setEmail(String email) {
		Email = email;
	}

	/**
     * Returns the customers email address
     *
	 * @return	the email
	 */
	public String getEmail() {
		return Email;
	}

	/**
     * Sets the customers password
     *
     * @param  email    the password
     */
	public void setPassword(String pword) {
		Password = pword;
	}

	/**
     * Returns the customers password
     *
	 * @return	the password
	 */
	public String getPassword() {
		return Password;
	}

	/**
     * Returns the field label for first name
     *
	 * @return	the first name label
	 */
	public String getFnameLabel() {
		return FnameLabel;
	}

	/**
     * Returns the field label for last name
     *
	 * @return	the last name label
	 */
	public String getLnameLabel() {
		return LnameLabel;
	}

	/**
     * Returns the field label for street address
     *
	 * @return	the street address label
	 */
	public String getStreetAddressLabel() {
		return StreetAddressLabel;
	}

	/**
     * Returns the field label for city
     *
	 * @return	the city label
	 */
	public String getCityLabel() {
		return CityLabel;
	}

	/**
     * Returns the field label for state
     *
	 * @return	the state label
	 */
	public String getStateLabel() {
		return StateLabel;
	}

	/**
     * Returns the field label for zipcode
     *
	 * @return	the zipcode label
	 */
	public String getZipLabel() {
		return ZipLabel;
	}

	/**
     * Returns the field label for phone number
     *
	 * @return	the phone number label
	 */
	public String getPhoneLabel() {
		return PhoneLabel;
	}

	/**
     * Returns the field label for email
     *
	 * @return	the email label
	 */
	public String getEmailLabel() {
		return EmailLabel;
	}

	/**
     * Returns the field label for password confirm
     *
	 * @return	the password label
	 */
	public String getPasswordLabel() {
		return PasswordLabel;
	}

	/**
     * Returns the field label for password confirm
     *
	 * @return	the password label
	 */
	public String getConfirmPasswordLabel() {
		return ConfirmPasswordLabel;
	}

	/**
     * Checks if all the customer data fields are valid.
     * Sets the field label strings accordingly
     * to their good or error forms.
     *
	 * @return	true if all the fields are valid, false otherwise
     */
	public boolean isValid() {

		boolean valid = true;

		// Validate the first name
		Fname = Fname.trim().toLowerCase();
		if (Fname.length() == 0) {
			valid = false;
			FnameLabel = FNAME_LABEL_ERROR;
		}
		else {
			Fname = Fname.substring(0,1).toUpperCase() + Fname.substring(1);
			FnameLabel = FNAME_LABEL_GOOD;
		}

		// Validate the last name
		Lname = Lname.trim().toLowerCase();
		if (Lname.length() == 0) {
			valid = false;
			LnameLabel = LNAME_LABEL_ERROR;
		}
		else {
			Lname = Lname.substring(0,1).toUpperCase() + Lname.substring(1);
			LnameLabel = LNAME_LABEL_GOOD;
		}

		// Validate the street address
		StreetAddress = StreetAddress.trim();
		if (StreetAddress.length() == 0) {
			valid = false;
			StreetAddressLabel = STREET_ADDRESS_LABEL_ERROR;
		}
		else {
			StreetAddressLabel = STREET_ADDRESS_LABEL_GOOD;
		}

		// Validate the city
		City = City.trim().toLowerCase();
		if (City.length() == 0) {
			valid = false;
			CityLabel = CITY_LABEL_ERROR;
		}
		else {
			City = City.substring(0,1).toUpperCase() + City.substring(1);
			CityLabel = CITY_LABEL_GOOD;
		}

		// Validate the state
		State = State.trim().toUpperCase();
		if (State.length() != 2) {
			valid = false;
			StateLabel = STATE_LABEL_ERROR;
		}
		else {
			StateLabel = STATE_LABEL_GOOD;
		}

		// Validate the ZIP
		Zip = Zip.trim();
		if ((Zip.length() == 5) && (isAInteger(Zip))) {
			ZipLabel = ZIP_LABEL_GOOD;
		}
		else {
			valid = false;
			ZipLabel = ZIP_LABEL_ERROR;
		}

		// Validate the Phone#
		Phone = Phone.trim();
		if (Phone.matches("\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d")) {
			PhoneLabel = PHONE_LABEL_GOOD;
		}
		else {
			valid = false;
			PhoneLabel = PHONE_LABEL_ERROR;
		}

		// Validate the email
		Email = Email.trim();
		if (Email.length() == 0) {
			valid = false;
			EmailLabel = EMAIL_LABEL_ERROR;
		}
		else {
			EmailLabel = EMAIL_LABEL_GOOD;
		}

		return valid;

	}


	/**
     * Checks if all the email/password fields are valid.
     * Sets the field label strings accordingly to their good or error forms.
     *
	 * @return	true if all the fields are valid, false otherwise
     */
	public boolean isPasswordValid() {

		boolean valid = true;


		// Validate the password
		Password = Password.trim();
		if (Password.length() == 0) {
			valid = false;
			PasswordLabel = PASSWORD_LABEL_ERROR;
			ConfirmPasswordLabel = CONFIRM_PASSWORD_LABEL_ERROR;
		}
		else {
			PasswordLabel = PASSWORD_LABEL_GOOD;
			ConfirmPasswordLabel = CONFIRM_PASSWORD_LABEL_GOOD;
		}



		return valid;

	}


	// Checks if the given string can be parsed into an integer
	private static boolean isAInteger (String val) {

		try {
			int x = Integer.parseInt(val);
			return true;
		}
		catch (Exception ee) {
			return false;
		}
	}


}
