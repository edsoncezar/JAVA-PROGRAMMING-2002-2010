package dbdemo;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.MappingException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

/**
 * @author MEagle
 *
 * This class will demonstrate how to use a many to many
 * relationship with Hibernate.
 *
 * THIS CLASS IS FOR DEMONSTRATION PURPOSES ONLY.  THIS IS NOT
 * PRODUCTION STYLE CODE.  NAMING CONVENTIONS ARE FICTITIOUS AND
 * IS ONLY HERE FOR A SPRINGBOARD FOR LEARNING.  CODE STYLE
 * SHOULD ALSO BE IGNORED OR REFACTORED FOR PRODUCTION QUALITY.
 */
public class HibernateDemoManyToMany {

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

			// locate a couple of users
			User coolUser = new User();
			coolUser = (User) session.load(User.class, "joe_cool");
			System.out.println("User ID = " + coolUser.getUserID());

			User coolUser2 = new User();
			coolUser2 = (User) session.load(User.class, "joe_cool2");
			System.out.println("User ID = " + coolUser2.getUserID());

			// create an instance of a new Book
			Book book1 = new Book();

			// find all of the books
			List myBooks = session.find("from book in class dbdemo.Book");

			boolean bookFound = false;

			if (myBooks.size() > 0) {

				ListIterator iter = myBooks.listIterator();
				while (iter.hasNext()) {
					book1 = (Book) iter.next();
					if (book1.getName().equals("Book_4")) {
						bookFound = true;
						break;
					}
				}
			}

			if (!bookFound) {
				// the book does not exist so create it
				book1 = new Book();
				book1.setName("Book_4");
			}

			if (book1.getUsers() == null
				|| book1.getUsers().size() == 0
				|| !bookFound) {
				book1.setUsers(new HashSet());
			}

			// add some users to the book if they do not exist
			// in the collection
			if (!book1.getUsers().contains(coolUser)) {
				book1.getUsers().add(coolUser);
				//coolUser.getBooks().add(book1);
			}
			if (!book1.getUsers().contains(coolUser2)) {
				book1.getUsers().add(coolUser2);
				//coolUser2.getBooks().add(book1);
			}

			// remember to flush and commit the changes so that they
			// will be persisted to the database.
			session.save(book1);
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

				// try and read back a persisted user
				session = sf.openSession();
				User coolUser3 = new User();
				coolUser3 = (User) session.load(User.class, "joe_cool2");
				Set s = coolUser3.getBooks();
				System.out.println(s);
				Set c = coolUser3.getContacts();
				System.out.println(c);
				session.close();

                System.exit(0);
			} catch (HibernateException he) {
				System.out.println("HibernateException " + he.getMessage());	
			}		
		}

	}
}
