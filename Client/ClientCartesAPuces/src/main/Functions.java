package main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Functions {

	public static byte[] stringToMD5Bytes(String msg) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(msg.getBytes());

		return md.digest();
	}

	public static String stringToMD5String(String msg) throws NoSuchAlgorithmException {
		StringBuilder sb = new StringBuilder();
		for (byte b : stringToMD5Bytes(msg)) {
			sb.append(String.format("%02X", b));
		}

		return sb.toString();
	}

	public static String bytesToString(byte[] byteTab) {
		StringBuilder sb = new StringBuilder();
		for (byte b : byteTab) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}

	public static String toString(byte[] byteTab) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (byte b : byteTab) {
			sb.append(String.format("0x%02X ", b));
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
}
