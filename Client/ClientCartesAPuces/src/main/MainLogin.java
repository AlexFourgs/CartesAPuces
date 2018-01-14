package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

import org.json.JSONObject;

import smartcard.SmartCard;
import smartcard.Word;

public class MainLogin {

	public static void main(String[] args) {
		String urlString = "https://192.168.1.3:8443/AtelierBiometrie/AtelierBio";
		Scanner scan = new Scanner(System.in);
		Runtime runtime = Runtime.getRuntime();
		Process p;
		BufferedReader is;
		BufferedReader br;
		String line;

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

			// Récupérer l'id
			ArrayList<ResponseAPDU> responses = sc.readCard((byte) 0x10, 8);
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
					String mdpSHA256 = Functions.stringToSHA256String(mdp);

					String urlParameters = "action=login&mode=password&login=" + login + "&password=" + mdpSHA256 + "&id=" + id;
					URL url = new URL(urlString + "?" + urlParameters);
					StringBuilder result = new StringBuilder();
					Functions.trustSSL();
					HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

					if (conn != null) {						
						System.out.println(url + " - " + conn.getResponseCode() + " " + conn.getResponseMessage());
						if (conn.getResponseCode() == 200) {
							br = new BufferedReader(
									new InputStreamReader(conn.getInputStream(), "UTF-8"));

							while ((line = br.readLine()) != null) {
								result.append(line + "\n");
							}
							br.close();
						}
						System.out.println(result.toString());
						JSONObject json = new JSONObject(result.toString());
						String registerResult = json.getString("result");
						if(registerResult != null && registerResult.equals("ok")) {
							System.out.println("IDENTIFICATION OK");
						}
						else {
							System.out.println("IDENTIFICATION FAILED");
						}
					}
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
				break;

			case 2:
				try {
					// Récupérer biométrie
//					p = runtime.exec("/home/antoine/git/CartesAPuces/Client/TI/facial_reco_c/build/Hough_exec");
					p = new ProcessBuilder("./Hough_exec").start();
					is = new BufferedReader(new InputStreamReader(p.getInputStream()));
					
					ArrayList<int[][]> histoList = new ArrayList<int[][]>();
					int[][] histos = new int[3][];
					int[] histoR = new int[256];
					int[] histoG = new int[256];
					int[] histoB = new int[256];
//					
//					while((line = is.readLine()) != null) {
//						System.out.println(line);
//						String[] values = line.split(",");
//						if(values.length == 257) {
//							switch(values[0]) {
//							case "R":
//								for(int i=0 ; i<256 ; i++) {
//									histoR[i] = Integer.valueOf(values[i+1]);
//								}
//								histos[0] = histoR;
//								break;
//							case "G":
//								for(int i=0 ; i<256 ; i++) {
//									histoG[i] = Integer.valueOf(values[i+1]);
//								}
//								histos[1] = histoG;
//								break;
//							case "B":
//								for(int i=0 ; i<256 ; i++) {
//									histoB[i] = Integer.valueOf(values[i+1]);
//								}
//								histos[2] = histoB;
//								break;
//							}
//						}
//						else {
//							System.out.println("Incorrect values length = " + values.length);
//						}
//						histoList.add(histos);
//					}
					p.waitFor();
					
					FileReader fr = new FileReader("./histo.txt");
					
					br = new BufferedReader(fr);

					while ((line = br.readLine()) != null) {
						System.out.println(line);
						String[] values = line.split(",");
						if(values.length == 257) {
							switch(values[0]) {
							case "R":
								for(int i=0 ; i<256 ; i++) {
									histoR[i] = Integer.valueOf(values[i+1]);
								}
								histos[0] = histoR;
								break;
							case "G":
								for(int i=0 ; i<256 ; i++) {
									histoG[i] = Integer.valueOf(values[i+1]);
								}
								histos[1] = histoG;
								break;
							case "B":
								for(int i=0 ; i<256 ; i++) {
									histoB[i] = Integer.valueOf(values[i+1]);
								}
								histos[2] = histoB;
								break;
							}
						}
						else {
							System.out.println("Incorrect values length = " + values.length);
						}
						histoList.add(histos);
					}
					fr.close();	
					new File("./histo.txt").delete();
					String urlParameters = "action=login&mode=biom";
					
					StringBuilder paramsBuilder = new StringBuilder();
					String histoTemp = "";
					int[][] histograms = histoList.get(0);

					paramsBuilder.append("&histoR=");
					histoTemp = "";
					System.out.println(histograms[0].length);
					for(int i=0 ; i<256 ; i++) {
						histoTemp += ","+histograms[0][i];
					}
					paramsBuilder.append(histoTemp.substring(1));
					
					paramsBuilder.append("&histoG=");
					histoTemp = "";
					for(int i=0 ; i<256 ; i++) {
						histoTemp += ","+histograms[1][i];
					}
					paramsBuilder.append(histoTemp.substring(1));
					
					paramsBuilder.append("&histoB=");
					histoTemp = "";
					for(int i=0 ; i<256 ; i++) {
						histoTemp += ","+histograms[2][i];
					}
					paramsBuilder.append(histoTemp.substring(1));
										
					urlParameters += paramsBuilder.toString();
					System.out.println(urlParameters.length());
					System.out.println(urlParameters);
						
					URL url = new URL(urlString + "?" + urlParameters);
					StringBuilder result = new StringBuilder();
					Functions.trustSSL();
					HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

					
					if (conn != null) {						
						System.out.println(url + " - " + conn.getResponseCode() + " " + conn.getResponseMessage());
						if (conn.getResponseCode() == 200) {
							br = new BufferedReader(
									new InputStreamReader(conn.getInputStream(), "UTF-8"));

							while ((line = br.readLine()) != null) {
								result.append(line + "\n");
							}
							br.close();
						}
						System.out.println(result.toString());
						JSONObject json = new JSONObject(result.toString());
						String registerResult = json.getString("result");
						if(registerResult != null && registerResult.equals("ok")) {
							System.out.println("IDENTIFICATION OK");
						}
						else {
							System.out.println("IDENTIFICATION FAILED");
						}
					}
				} catch(IOException ioe) {
					ioe.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				break;
			}
			
		} catch (CardException e) {
			e.printStackTrace();
		}
		scan.close();
	}

}
