package dbdemo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.MappingException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

/**
 * @author MEagle
 *
 * This class will demonstrate how to use HQL to retrieve
 * information about the data in the tables.  This shows 
 * different ways to do the same operation in some instances
 * to show the flexibility of the Hibernate Query Language. 
 * Also notice how much SQL is required based on the 
 * different HQL statments used for the same selection.
 *
 * THIS CLASS IS FOR DEMONSTRATION PURPOSES ONLY.  THIS IS NOT
 * PRODUCTION STYLE CODE.  NAMING CONVENTIONS ARE FICTITIOUS AND
 * IS ONLY HERE FOR A SPRINGBOARD FOR LEARNING.  CODE STYLE
 * SHOULD ALSO BE IGNORED OR REFACTORED FOR PRODUCTION QUALITY.
 */
public class HibernateDemoHQL {

	public static void main(String[] args) {
		run();
	}

	public static void run() {
		Configuration cfg = null;
		SessionFactory sf = null;
		Session session = null;
		Properties props = new Properties();

		try {
			cfg =
				new Configuration()
					.addClass(User.class)
					.addClass(Contact.class)
					.addClass(Book.class)
					.addClass(Address.class)
					.addProperties(props);

			sf = cfg.buildSessionFactory();
			// create a session object from the factory
			session = sf.openSession();

			/***************************************************************
			 * Grab all of the Contacts for all users using a left outer 
			 * join to handle potential missing contact elements if a user
			 * does not have any contacts.
			 ***************************************************************/
			StringBuffer sb = new StringBuffer(50);
			sb.delete(0, sb.length());
			sb.append(
				"select user.userName, contacts.name from dbdemo.User user ");
			sb.append("left join user.contacts contacts ");

			List list = session.find(sb.toString());
			if (list.size() > 0) {
				System.out.println("----Contact list-1--------");
				ListIterator iter = list.listIterator();
				while (iter.hasNext()) {
					Object[] obj = (Object[]) iter.next();
					System.out.println(
						"User = "
							+ ((String) obj[0])
							+ " Contact = "
							+ (obj[1] == null ? "" : ((String) obj[1])));
				}
				System.out.println("--------------------------\r\n");
			}

			/***************************************************************
			 * Grab all of the Contacts for joe_cool2
			 ***************************************************************/
			sb.delete(0, sb.length());
			sb.append("select elements(user.contacts) from dbdemo.User user");
			sb.append(" where user.userID='joe_cool2'");

			List contactList = session.find(sb.toString());
			if (contactList.size() > 0) {
				System.out.println("----Contact list-2--------");
				ListIterator iter = contactList.listIterator();
				while (iter.hasNext()) {
					Contact contact = (Contact) iter.next();
					System.out.println("Contact = " + contact.getName());
				}
				System.out.println("--------------------------\r\n");
			}

			/**************************************************************
			 * Grab all of the books for joe_cool2
			 **************************************************************/
			sb.delete(0, sb.length());
			sb.append("select elements(user.books) from ");
			sb.append("dbdemo.User user ");
			sb.append("where user.userID='joe_cool2'");

			List bookList = session.find(sb.toString());
			if (bookList.size() > 0) {
				System.out.println("----Book list----------");
				ListIterator iter = bookList.listIterator();
				while (iter.hasNext()) {
					Book book = (Book) iter.next();
					System.out.println("book = " + book.getName());
				}
				System.out.println("--------------------------\r\n");
			}

			/**************************************************************
			 *  Yet another way to grab the city for joe_cool2
			 **************************************************************/
			sb.delete(0, sb.length());
			sb.append("select address.city from ");
			sb.append("dbdemo.User user ");
			sb.append("inner join user.address as address ");
			sb.append("where user.userID='joe_cool2'");

			Iterator iter = session.iterate(sb.toString());
			while (iter.hasNext()) {
				System.out.println("----City----------");
				System.out.println("city = " + (String) iter.next());
				System.out.println("--------------------------\r\n");
			}

			/**************************************************************
			 * Another way to grab city for joe_cool2 using an implicit 
			 * join and named parameters in the where clause
			 **************************************************************/
			// 	inner join.
			sb.delete(0, sb.length());
			sb.append("select user.address.city from ");
			sb.append("dbdemo.User user ");

			// get the user id as a named parameter and use the special 
			// (lowercase) id property to reference the unique identifier
			// of the object
			sb.append("where user.id=:user_id");

			// could have also substituted this line to use the property
			// name (userID) to reference the unique identifier.
			//sb.append("where user.userID=:user_id");				

			Query q = session.createQuery(sb.toString());
			q.setParameter("user_id", "joe_cool2");

			iter = q.iterate();
			while (iter.hasNext()) {
				System.out.println("----City-2----------------");
				System.out.println("city = " + (String) iter.next());
				System.out.println("--------------------------\r\n");
			}

			/**************************************************************
			 * Grab the city as a String from the 1-1 relationship between
			 * the user and address using implicit joins.
			 **************************************************************/
			sb.delete(0, sb.length());
			sb.append("select user.address.city from ");
			sb.append("dbdemo.User user ");
			sb.append("where user.userID='joe_cool2'");

			iter = session.iterate(sb.toString());
			while (iter.hasNext()) {
				System.out.println("----City-3----------------");
				System.out.println("city = " + (String) iter.next());
				System.out.println("--------------------------\r\n");
			}

			// make sure that you flush and commit the changes to the database
			session.flush();
			//session.connection().commit();

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
			try {
				session.connection().rollback();
			} catch (HibernateException he1) {
				System.out.println("HibernateException " + he1.getMessage());
			} catch (SQLException se1) {
				System.out.println("SQLException " + se1.getMessage());
			}
			System.out.println("HibernateException " + he.getMessage());
		} catch (Exception e) {
			try {
				session.connection().rollback();
			} catch (HibernateException he1) {
				System.out.println("HibernateException " + he1.getMessage());
			} catch (SQLException se1) {
				System.out.println("SQLException " + se1.getMessage());
			}
			System.out.println("Exception " + e.getMessage());
		} finally {
			try {

				// closes the session and returns the underlying connection
				// to the pool.  If you provide your own connection to the
				// session then make sure that you close it as well.
				Connection con = session.close();
                System.exit(0);
			} catch (HibernateException he) {
				System.out.println("HibernateException " + he.getMessage());
			}
		}

	}
}
