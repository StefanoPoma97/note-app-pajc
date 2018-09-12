package it.unibs.pajc.note.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe per la gestione delle pwd
 * 
 * @author danielevezz
 *
 */
public class AuthenticationUtility {

	/**
	 * restituisce hash code da un array di byte
	 * 
	 * @param hash
	 * @return stringa che rappresenta l'hash code dell'ingresso
	 * @author Daniele Vezzoli
	 */
	private static String hashToString(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	/**
	 * da una stringa restituisce il suo hashcode
	 * 
	 * @param text
	 * @return stringa che rappresenta l'hashcode dell'input
	 * @author Daniele Vezzoli
	 */
	public static String generateHash(String text) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("No such algorithm");
		}

		return hashToString(digest.digest(text.getBytes(StandardCharsets.UTF_8)));
	}

}
