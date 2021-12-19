package dbdemo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.MappingException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

/**
 * @author MEagle
 * @author Paulo Jerônimo
 *
 * This class will demonstrate how to use a one to one
 * relationship with Hibernate.
 *
 * THIS CLASS IS FOR DEMONSTRATION PURPOSES ONLY.  THIS IS NOT
 * PRODUCTION STYLE CODE.  NAMING CONVENTIONS ARE FICTITIOUS AND
 * IS ONLY HERE FOR A SPRINGBOARD FOR LEARNING.  CODE STYLE
 * SHOULD ALSO BE IGNORED OR REFACTORED FOR PRODUCTION QUALITY.
 */
public class HibernateDemo {

	public static void main(String[] args) {
		run();
	}

	public static void run() {
		// Hibernate 1.2.4
		//Datastore ds = null;

		Configuration cfg = null;
		SessionFactory sf = null;
		Session session = null;
		Properties props = new Properties();

		try {
			// create a datastore object with Hibernate 1.2.4
			//ds = Hibernate.createDatastore();
			//ds
			//	.storeClass(User.class)
			//	.storeClass(Contact.class)
			//	.storeClass(Book.class)
			//	.storeClass(Address.class);
			cfg =
				new Configuration()
					.addClass(User.class)
					.addClass(Contact.class)
					.addClass(Book.class)
					.addClass(Address.class)
					.addProperties(props);

			// create a session object to the database
			sf = cfg.buildSessionFactory();
			session = sf.openSession();

			// Create new User and store them in the database
			User user = new User();
			user.setUserID("joe_cool");
			user.setUserName("Joseph Cool");
			user.setPassword("abc1211");
			user.setEmailAddress("joe@cool.com");
			user.setLastLogon(new Date());

			User user2 = new User();
			user2.setUserID("joe_cool2");
			user2.setUserName("Joseph Cool2");
			user2.setPassword("abc1212");
			user2.setEmailAddress("joe2@cool.com");
			user2.setLastLogon(new Date());

			// Add an address for the cool user
			Address addr = new Address();
			addr.setId(user.getUserID());
			addr.setCity("Atlanta");
			addr.setState("GA");
			addr.setZip("30097");

			// Add an address for the cool user 2
			Address addr2 = new Address();
			addr2.setId(user2.getUserID());
			addr2.setCity("Los Angeles");
			addr2.setState("CA");
			addr2.setZip("90210");

			// link the one-to-one mapping
			user.setAddress(addr);
			user2.setAddress(addr2);

			// And the Hibernate call which stores it
			session.save(user);
			session.save(addr);
			session.save(user2);
			session.save(addr2);

			// Let's load the same user into another instance...
			User coolUser = new User();
			coolUser = (User) session.load(User.class, "joe_cool");
			System.out.println("User = " + coolUser.getPassword());

			// list the user's back and set the password to a default value...

			List myUsers = session.find("from user in class dbdemo.User");
			if (myUsers.size() > 0) {

				ListIterator iter = myUsers.listIterator();
				while (iter.hasNext()) {
					User nextUser = (User) iter.next();
					System.out.println(nextUser);
					nextUser.setPassword("secret");
				}
			}

			// make sure that you flush and commit the changes to the database
			session.flush();
			session.connection().commit();

		} catch (MappingException me) {
			System.out.println("MappingException " + me.getMessage());
			try {
				session.connection().rollback();
			} catch (HibernateException he1) {
				System.out.println("HibernateException " + he1.getMessage());
			} catch (SQLException se1) {
				System.out.println("SQLException " + se1.getMessage());
			}
			
		} catch (HibernateException he) {
			System.out.println("HibernateException " + he.getMessage());
			try {
				session.connection().rollback();
			} catch (HibernateException he1) {
				System.out.println("HibernateException " + he1.getMessage());
			} catch (SQLException se1) {
				System.out.println("SQLException " + se1.getMessage());
			}
		} catch (SQLException se) {
			System.out.println("SQLException " + se.getMessage());
			try {
				session.connection().rollback();
			} catch (HibernateException he1) {
				System.out.println("HibernateException " + he1.getMessage());
			} catch (SQLException se1) {
				System.out.println("SQLException " + se1.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
			try {
				session.connection().rollback();
			} catch (HibernateException he1) {
				System.out.println("HibernateException " + he1.getMessage());
			} catch (SQLException se1) {
				System.out.println("SQLException " + se1.getMessage());
			}
		} finally {
			try {

				// closes the session and returns the underlying connection
				// to the pool.  If you provide your own connection to the
				// session then make sure that you close it as well.
				Connection con = session.close();
				System.out.println("con=" + con);
                System.exit(0);

			} catch (HibernateException he) {
				System.out.println("HibernateException " + he.getMessage());
			}
		}

	}
}
