package it.ingsw.revedia.utilities;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordManager 
{
	public static String getMD5(String password)
	{
		String hashPassword = "";
		
		try 
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte [] messageDigest = md.digest(password.getBytes());
			BigInteger num = new BigInteger(1,messageDigest);
			hashPassword = num.toString(16);
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		
		return hashPassword;
	}

	public static String generatePassword() {
		String psw = "";
		Random random = new Random();

		for(int i = 0; i < 16; i++) {
			psw += (char)(random.nextInt(94) + 33);
		}

		return psw;
	}
}
