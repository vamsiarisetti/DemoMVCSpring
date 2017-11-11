package org.mvc.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.mvc.controller.mapping.Person;
import org.mvc.controller.mapping.UserInfo;
import org.mvc.controller.utils.HibernateUtil;
import org.mvc.controller.utils.LoginUtils;

@Controller
@RequestMapping("/")
public class HelloWorldController {

	@RequestMapping(method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
        model.addAttribute("greeting", "Hello World from Spring 4 MVC");
        return "login";
    }

    @RequestMapping(value = "/helloagain", method = RequestMethod.GET)
    public String sayHelloAgain(ModelMap model) {
        model.addAttribute("greeting", "Hello World Again from Spring 4 MVC");
        return "login";
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String sayHelloUser(ModelMap model) {
    	model.addAttribute("greeting", "ECS");
    	return "registerUser";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String doLoginUser(ModelMap model, @RequestParam("userNm") String userNm, @RequestParam("userPwd") String pwd, HttpSession appSession) {
    	Session session = null;
		SessionFactory sessionFactory = null;

		String dbPassword = null;
		String dbSalt = null;
    	try {
    	//model.addAttribute("greeting", "ECS");
    	boolean blnIsloginSuccess = Boolean.FALSE;
    	System.out.println("USERNAME : "+userNm+": PASSWORD : "+pwd);

    	sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();
		session.beginTransaction();

		Query qry = session.createQuery("FROM UserInfo where username=:username");
		qry.setParameter("username", userNm);
		List<UserInfo> lst = qry.list();
		Iterator<UserInfo> itr = lst.iterator();
		while (itr.hasNext()) {
			UserInfo uInfo = (UserInfo) itr.next();
			dbPassword = uInfo.getPassword();
			dbSalt = uInfo.getSalt();
		}
		System.out.println("Password>>"+pwd+">>SALT>>"+dbSalt);
		String userPwdHash = LoginUtils.demoMD5(pwd+dbSalt);

		appSession.setAttribute("UserName", userNm);
		if (userPwdHash.equalsIgnoreCase(dbPassword)) {
			blnIsloginSuccess = Boolean.TRUE;
		} else {
			blnIsloginSuccess = Boolean.FALSE;
		}
		appSession.setAttribute("IsLogin", blnIsloginSuccess);
    	if (blnIsloginSuccess) {
    		System.out.println("LOGIN SUCCESS");
			return "landingPage";
		} else {
			System.out.println("LOGIN FAILED :: PLEASE ENTER VALID CREDENTIALS");
			model.addAttribute("LoginErrMsg", "Please Enter valid Credentials...");
			return "login";
		}
    	} catch(HibernateException he) {
    		System.out.println("caught in HibernateException :: "+he.getMessage());
			he.printStackTrace();
    	} catch (NoSuchAlgorithmException e) {
    		System.out.println("caught in NoSuchAlogorithmException :: "+e.getMessage());
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return null;
    }

    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String doLogOut(ModelMap model, HttpServletRequest request, HttpSession appSession) {
    	//HttpSession session = request.getSession(false);

    	System.out.println("UserName>>"+appSession.getAttribute("UserName"));
    	System.out.println("IsLogin>>"+appSession.getAttribute("IsLogin"));

    	/*System.out.println("session :: UserName>>"+session.getAttribute("UserName"));
    	System.out.println("session :: IsLogin>>"+session.getAttribute("IsLogin"));*/

    	appSession.removeAttribute("UserName");
    	appSession.removeAttribute("IsLogin");

    	/*session.removeAttribute("UserName");
    	session.removeAttribute("IsLogin");*/

    	System.out.println("After :: UserName>>"+appSession.getAttribute("UserName"));
    	System.out.println("After :: IsLogin>>"+appSession.getAttribute("IsLogin"));

    	/*System.out.println("After :: session :: UserName>>"+session.getAttribute("UserName"));
    	System.out.println("After :: session :: IsLogin>>"+session.getAttribute("IsLogin"));*/

    	if (appSession != null) {
    		System.out.println("Invalidating session");
    		appSession.invalidate();
			//appSession.invalidate();
		}
		return "logout";
    }

    @RequestMapping(value="/home", method=RequestMethod.GET)
    public String goToHome(ModelMap model) {
    	return "landingPage";
    }

    @RequestMapping(value="/RegisterNewUser", method=RequestMethod.POST)
    public String doRegNewUser(ModelMap model,
    		@RequestParam("FstNm") String fstName, @RequestParam("LstNm") String lstName,
    		@RequestParam("Passwd") String Passwd, @RequestParam("RePasswd") String RePasswd,
    		@RequestParam("email") String email, @RequestParam("UserNm") String UserNm,
    		@RequestParam("Cty") String Cty) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		boolean blnIsRegVaidated = Boolean.FALSE;
		String errMsg = null;
    	try{

    		String lgnValDtls = new LoginUtils().doValidateRegDetails(fstName, lstName, Passwd, RePasswd, email, UserNm, Cty);
    		if (lgnValDtls != null) {
    			String[] loginDtls = lgnValDtls.split("~");
    			for (int i = 0; i < loginDtls.length; i++) {
    				if(loginDtls[0] != null)
    					blnIsRegVaidated = Boolean.valueOf(loginDtls[0]);
					errMsg = loginDtls[1];
				}
			}
    		if (blnIsRegVaidated) {
        		Person prsn = new Person();
        		prsn.setFirstName(fstName);
        		prsn.setLastName(lstName);
        		prsn.setEmail(email);

        		String salt = LoginUtils.getRandomSalt();
        		String dbPwdHash = LoginUtils.demoMD5(Passwd + salt);
        		String passHash = LoginUtils.demoMD5(Passwd);

        		prsn.setUserInfo(new UserInfo(UserNm, Cty.toUpperCase(), dbPwdHash, salt, passHash));
        		long personId = (Long) session.save(prsn);
        		session.getTransaction().commit();
        		return "login";
			} else {
				model.addAttribute("RegErrors", errMsg);
				return "registerUser";
			}
    	} catch (Exception e) {
    		session.getTransaction().rollback();
    		e.printStackTrace();
    	}
    	return null;
    }

    @RequestMapping(value = "/RdrResetpwd", method = RequestMethod.GET)
	public String doRedirect2ResetPwd(ModelMap model) {
    	System.out.println("In redirect reset Password");
    	return "/resetPwd";
    }

    @RequestMapping(value = "/ResetPassword", method = RequestMethod.POST)
    public String resetPassword(ModelMap model,
    			@RequestParam("userNm") String userNm, @RequestParam("userPwd") String userPwd) {
    	Session session = null;
		SessionFactory sessionFactory = null;
    	try {
			System.out.println("in ResetPassword>>");

			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			String salt = LoginUtils.getRandomSalt();
			String dbPwdHash = LoginUtils.demoMD5(userPwd + salt);
			String passHash = LoginUtils.demoMD5(userPwd);

			String hql = "UPDATE UserInfo set password=:userPwd,pwdhash=:pwdhash,salt=:salt WHERE "
					+ "username=:username";
			Query qry = session.createQuery(hql);
			qry.setParameter("userPwd",dbPwdHash);
			qry.setParameter("pwdhash",passHash);
			qry.setParameter("salt",salt);
			qry.setParameter("username",userNm);

			int result = qry.executeUpdate();
			System.out.println("No. Of Records Updated >> "+result);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
    	
    	return "login";
    }
}