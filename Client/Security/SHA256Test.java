package login;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Test {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println("Exemple de hachage en SHA-256 de \"TEST\"");
		
		String to_digest = "TEST" ;
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(to_digest.getBytes(StandardCharsets.UTF_8));
		
		StringBuffer hexString = new StringBuffer();
		
		for(int i = 0 ; i < hash.length ; i++){
			String hex = Integer.toHexString(0xff & hash[i]);
			hexString.append(hex);
		}
		
		System.out.println("TEST = " + hexString.toString());

	}

}
