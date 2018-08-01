package it.unibs.pajc.note.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Classe per la gestione delle pwd
 * @author danielevezz
 *
 */
public class AuthenticationUtility {

	private static String hashToString(byte[] hash) {
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++) {
	    String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	public static String generateHash(String text) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("No such algorithm");
		}
		
		return hashToString(digest.digest(text.getBytes(StandardCharsets.UTF_8)));
	}
	
//	public static String generateHashString(String text) {
//		return hashToString(generateHash(text));
//	}

}
