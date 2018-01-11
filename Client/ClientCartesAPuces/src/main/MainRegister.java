package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

import smartcard.SmartCard;
import smartcard.Word;
import smartcard.WordSizeException;

public class MainRegister {

	public static void main(String[] args) {
		String urlString = "http://localhost/carteapuces.php";
		Scanner scan = new Scanner(System.in);
		
		// Demander login
		System.out.println("entrer votre login :");
		String login = scan.nextLine();
		
		// Demander mdp
		System.out.println("entrer votre mdp :");
		String mdp = scan.nextLine();
		
		// Demander nom
		System.out.println("entrer votre nom :");
		String lastname = scan.nextLine();
		
		// Demander prenom
		System.out.println("entrer votre prénom :");
		String firstname = scan.nextLine();
		
		// Demander mail
		System.out.println("entrer votre mail :");
		String mail = scan.nextLine();
		
		// Récupérer biométrie
		
		
		// Requête Serveur
		try {
			URL url = new URL(urlString);
			StringBuilder result = new StringBuilder();
			// trustSSL();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	
			if (conn != null) {
				String mdpMD5 = Functions.stringToMD5String(mdp);
				
				String biom = "";				
				
				String urlParameters = "action=register&login=" + login + "&mdp=" + mdpMD5 + "&lastname=" + lastname + "&firstname=" + firstname + "&mail=" + mail + "&biom=" + biom;
				byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
				int postDataLength = postData.length;
	
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("charset", "utf-8");
				conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
				conn.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
				conn.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
				
				System.out.println(url + " - " + conn.getResponseCode() + " " + conn.getResponseMessage());
				if (conn.getResponseCode() == 200) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(conn.getInputStream(), "UTF-8"));
	
					String line;
					while ((line = br.readLine()) != null) {
						result.append(line + "\n");
					}
					br.close();
				}
				System.out.println(result.toString());

				// Récupérer id
				String id = "098f6bcd4621d373cade4e832627b4f6".toUpperCase();

				// Ecrire dans la carte
				List<CardTerminal> terminauxDispos = TerminalFactory.getDefault().terminals().list();
				SmartCard sc = new SmartCard(terminauxDispos.get(0));
				// Attendre qu'il y a une carte qui se connecte
				System.out.println("Attente d'une carte dans le terminal");
				sc.getTerminal().waitForCardPresent(0);
				sc.connect();
				System.out.println("Ecriture sur la carte");
				Word pin0 = new Word((byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA);
				ResponseAPDU r = sc.testPin((byte) 0x07, pin0);
				System.out.println(String.format("%02X %02X", r.getSW1(), r.getSW2()));
				
				sc.writeToCard((byte) 0x10, Functions.hexStringToByteArray(id));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (CardException e) {
			e.printStackTrace();
		} catch (WordSizeException e) {
			e.printStackTrace();
		}
		scan.close();
	}

}
