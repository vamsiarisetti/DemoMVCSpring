package org.mvc.controller.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class LoginUtils {

	public static String getRandomSalt() {
		String strRandom = UUID.randomUUID().toString().replace("-", "");
		return strRandom;
	}
	public static String demoMD5(String str) throws NoSuchAlgorithmException {
		MessageDigest message = MessageDigest.getInstance("MD5");
		message.update(str.getBytes(), 0, str.length());
		String hashmd5 = new BigInteger(1,message.digest()).toString();
		return hashmd5;
	}

	public String doValidateRegDetails(String fstName,String lstName, 
			String Passwd, String RePasswd, String email, String UserNm, String Cty) {
		StringBuffer errorMsg = new StringBuffer();
			if ("".equals(fstName) || fstName == null) {
				errorMsg.append("First Name is null").append("<br>");
			}
			if ("".equals(lstName) || lstName == null) {
				errorMsg.append("Last Name is null").append("<br>");
			}
			if ("".equals(Passwd) || Passwd == null) {
				errorMsg.append("Password is null").append("<br>");
			}
			if ("".equals(RePasswd) || RePasswd == null) {
				errorMsg.append("Retype Password is null").append("<br>");
			}
			if (Passwd != null && RePasswd != null) {
				if (!Passwd.equals(RePasswd)) {
					errorMsg.append("Password and Retype password are not identical").append("<br>");
				}
			}
			if ("".equals(UserNm) || UserNm == null) {
				errorMsg.append("UserName is null").append("<br>");
			}
			if ("".equals(Cty) || Cty == null) {
				errorMsg.append("City is null").append("<br>");
			} 

			if (errorMsg.length() > 0) {
				System.err.println("errorMsg>>"+errorMsg);
				return false+"~"+errorMsg.toString();
			} else {
				return true+"~"+" ";
			}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		LoginUtils obj = new LoginUtils();
		String salt = getRandomSalt();
		String dbPwdHash = obj.demoMD5("A@123a"+salt);

		String userPwdHash = obj.demoMD5("A@123a"+salt);

		//String dbPwdHash = "338517085008887402609568403619445755887";
		//String userPwdHash = obj.demoMD5("A@123a"+"b14e40eceb044b8084730ac2f461c4b7");
		System.out.println("is Hashed Same :: "+dbPwdHash.equals(userPwdHash));
	}
}