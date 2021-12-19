package dbdemo;

import java.sql.SQLException;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.MappingException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

/**
 * @author MEagle
 *
 * This class will demonstrate how to use a one to many
 * relationship with Hibernate.
 *
 * THIS CLASS IS FOR DEMONSTRATION PURPOSES ONLY.  THIS IS NOT
 * PRODUCTION STYLE CODE.  NAMING CONVENTIONS ARE FICTITIOUS AND
 * IS ONLY HERE FOR A SPRINGBOARD FOR LEARNING.  CODE STYLE
 * SHOULD ALSO BE IGNORED OR REFACTORED FOR PRODUCTION QUALITY.
 */
public class HibernateDemoOneToMany {

	public static void main(String[] args) {
		run();

	}

	public static void run() {
		// Hibernate 1.2.4
		//Datastore ds = null;

		Configuration cfg = null;
		SessionFactory sf = null;
		Session session = null;

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
					.addClass(Address.class);

			// create a session object to the database
			sf = cfg.buildSessionFactory();
			session = sf.openSession();

			User coolUser = new User();
			coolUser = (User) session.load(User.class, "joe_cool2");
			System.out.println("User ID = " + coolUser.getUserID());

			// create some contacts
			Contact contact = new Contact();
			contact.setName("nobody6");
			contact.setEmail("nobody6@here.com");

			Contact contact2 = new Contact();
			contact2.setName("nobody1");
			contact2.setEmail("nobody1@here.com");

			// set the relationships in the objects
			contact.setUser(coolUser);
			contact2.setUser(coolUser);
			coolUser.getContacts().add(contact);
			coolUser.getContacts().add(contact2);

			// save the contact objects
			session.save(contact);
			session.save(contact2);

			// load back an instance of the user and print the
			// hashcode of the contact objects related to the user.
			User coolUser2 = new User();
			coolUser2 = (User) session.load(User.class, "joe_cool2");
			Set s = coolUser2.getContacts();
			System.out.println(s);

			// remember to flush and commit the changes so that they
			// will be persisted to the database.
			session.flush();
			session.connection().commit();
		} catch (MappingException me) {
			System.out.println("MappingException " + me.getMessage());
		} catch (HibernateException he) {
			System.out.println("HibernateException " + he.getMessage());
		} catch (SQLException se) {
			System.out.println("SQLException " + se.getMessage());
		} finally {
			try {
				// always remember to close the session object and close
				// any connection object if you passed one in when
				// creating the session.
				session.close();
                System.exit(0);

			} catch (HibernateException he) {
				System.out.println("HibernateException " + he.getMessage());
			} 
		}

	}
}
