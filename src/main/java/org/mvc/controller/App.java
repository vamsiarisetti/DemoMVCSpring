package org.mvc.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mvc.controller.mapping.Person;
import org.mvc.controller.mapping.UserInfo;
import org.mvc.controller.utils.HibernateUtil;
import org.mvc.controller.utils.LoginUtils;

/**
 * Hello world!
 * @author vamsi
 *
 */
public class App 
{
    public static void main1( String[] args )
    {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Person p1 = new Person();
        p1.setFirstName("FstNm1");
        p1.setLastName("LstNm1");
        long personId = (Long) session.save(p1);
        session.getTransaction().commit();

        Person person = (Person) session.get(Person.class, personId);
        System.out.println("PERSON :: "+person);
    }
    public static void main(String[] args) throws NoSuchAlgorithmException {

    	LoginUtils obj = new LoginUtils();
    	String password = "A@123a";
    	String salt = LoginUtils.getRandomSalt();
		String dbPwdHash = obj.demoMD5(password+salt);
		String pwdHash = obj.demoMD5(password);

		String userPwdHash = obj.demoMD5(password+salt);
		System.out.println("is Hashed Same :: "+dbPwdHash.equals(userPwdHash));

    	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Person p1 = new Person();
        p1.setFirstName("FstNm1");
        p1.setLastName("LstNm1");

        p1.setUserInfo(new UserInfo("VAMSI2","Hyd",dbPwdHash,salt,pwdHash));
        long personId = (Long) session.save(p1);
        session.getTransaction().commit();

        Person person = (Person) session.get(Person.class, personId);
        System.out.println("PERSON :: "+person);

        UserInfo userinfo = person.getUserInfo();
        System.out.println("USERINFO >>>"+userinfo);

        doSelectUserInfo();
	}
    public static void doSelectUserInfo() {
    	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
    	Query qry = session.createQuery("FROM Userinfo where username = :username");
        qry.setString("username", "VAMSI");
        List lst = qry.list();
        Iterator itr = lst.iterator();
        while (itr.hasNext()) {
			Object object = (Object) itr.next();
			System.out.println("object>>"+object);
		}
	}
}