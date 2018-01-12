package main;

import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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

	public static byte[] stringToSHA256Bytes(String msg) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(msg.getBytes());

		return md.digest();
	}

	public static String stringToSHA256String(String msg) throws NoSuchAlgorithmException {
		StringBuilder sb = new StringBuilder();
		for (byte b : stringToSHA256Bytes(msg)) {
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
	
	public int compareHisto(int[] histoR, int[] histoG, int[] histoB, ArrayList<String[]> histos) {
		int min = 0, minI = -1;
		int current = 0;
		for(String[] histo : histos) {
			String[] valuesR = histo[0].split(",");
			String[] valuesG = histo[1].split(",");
			String[] valuesB = histo[2].split(",");
			int sumR = 0, sumG = 0, sumB = 0;
			int sumTotal = 0;
			for(int i=0 ; i<256 ; i++) {
				sumR += Math.abs(histoR[i] - Integer.valueOf(valuesR[i]));
				sumG += Math.abs(histoG[i] - Integer.valueOf(valuesG[i]));
				sumB += Math.abs(histoB[i] - Integer.valueOf(valuesB[i]));
			}
			sumTotal = sumR+sumG+sumB;
			if(sumTotal < min || minI == -1) {
				min = sumTotal;
				minI = current;
			}
			current ++;
		}
		
		return minI;
	}
	
	public static void trustSSL() {
		try
	    {
	        // Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        }
	        };

	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };

	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (KeyManagementException e) {
	        e.printStackTrace();
	    }
	}
}
