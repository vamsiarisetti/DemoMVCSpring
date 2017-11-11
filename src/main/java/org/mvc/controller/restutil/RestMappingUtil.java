package org.mvc.controller.restutil;

import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mvc.controller.utils.HibernateUtil;
import org.mvc.controller.utils.LoginUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestMappingUtil {

	@RequestMapping(value="/UserExists/{username}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON)
	public boolean getUserNames(@PathVariable("username") String username) {
		Session session = null;
		SessionFactory sessionFactory = null;
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query qry = session.createQuery("FROM UserInfo where username=:username");
			qry.setParameter("username", username);
			if (qry.list().size() > 0) {
				// if User Exists returning TRUE
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return Boolean.FALSE;
	}

	@RequestMapping(value="/chkPwdisPrev/{username}/{password}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON)
	public String chkPasswdExists(@PathVariable("username") String username, @PathVariable("password") String password) throws NoSuchAlgorithmException {
		Session session = null;
		SessionFactory sessionFactory = null;
		String dbPwdhash = "";
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			String passHash = LoginUtils.demoMD5(password);

			String hql = "SELECT pwdhash FROM UserInfo where username=:username";
			Query qry = session.createQuery(hql);
			qry.setParameter("username", username);
			List<String> listUserInfo = qry.list();
			Iterator<String> iterator = listUserInfo.iterator();
			while (iterator.hasNext()) {
				dbPwdhash = iterator.next();
				//dbPwdhash = iterator.next().toString();
			}

			// If Password from web page and DB are same
			if (dbPwdhash.equals(passHash)) {
				return "true";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return "false";
	}
}