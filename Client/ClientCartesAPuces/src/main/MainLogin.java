package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

import smartcard.SmartCard;
import smartcard.Word;

public class MainLogin {

	public static void main(String[] args) {
		String urlString = "http://localhost/carteapuces.php";
		Scanner scan = new Scanner(System.in);

		try {
			// Recherche des terminaux
			List<CardTerminal> terminauxDispos = TerminalFactory.getDefault().terminals().list();
			SmartCard sc = new SmartCard(terminauxDispos.get(0));
			// Attendre qu'il y a une carte qui se connecte
			sc.getTerminal().waitForCardPresent(0);
			sc.connect();

			// Check le code PIN
			System.out.println("entrez le code PIN :");
			String str = scan.nextLine();

			byte[] pin0 = Functions.hexStringToByteArray(str);
			System.out.println(Functions.toString(pin0));

			ResponseAPDU r = sc.testPin((byte) 0x07, new Word(pin0));
			if (r.getSW1() == (byte) 0x63) {
				System.err.println("Mauvais code PIN");
				r = sc.testPin((byte) 0x07, new Word((byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0xAA));
				System.out.println(String.format("%02X %02X", r.getSW1(), r.getSW2()));
				System.exit(1);
			}
			System.out.println(String.format("%02X %02X", r.getSW1(), r.getSW2()));

			// Récpérer l'id
			ArrayList<ResponseAPDU> responses = sc.readCard((byte) 0x10, 4);
			String id = "";
			for (ResponseAPDU resp : responses) {
				byte[] b = resp.getData();
				id += String.format("%02X%02X%02X%02X", b[0], b[1], b[2], b[3]);
			}
			System.out.println(id);

			// L'utilisateur choisis entre login et mdp ou biométrie

			int choice = 0;
			String choiceStr = "";
			
			do {
				System.out.println("entrer votre choix : \n  1 pour login/mdp\n  2 pour biométrie\n");
				choiceStr = scan.nextLine();
			} while (!choiceStr.equals("1") && !choiceStr.equals("2"));

			choice = Integer.valueOf(choiceStr);
			switch (choice) {
			case 1:
				// formulaire login + mdp
				System.out.println("entrer votre login :");
				String login = scan.nextLine();
				System.out.println("entrer votre mdp :");
				String mdp = scan.nextLine();

				try {
					String mdpMD5 = Functions.stringToMD5String(mdp);

					URL url = new URL(urlString);
					StringBuilder result = new StringBuilder();
					// trustSSL();
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();

					if (conn != null) {
						String urlParameters = "action=login&login=" + login + "&password=" + mdpMD5;
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
					}
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				break;

			case 2:
				break;
			}
			
		} catch (CardException e) {
			e.printStackTrace();
		}
		scan.close();

		// Connection au serveur et check
		//
	}

}
