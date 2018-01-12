package com.atelierBio.connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Connexion {

	/**
	 * URL de connection
	 */
	private static String url = "jdbc:postgresql://localhost:5432/atelierBio";

	/**
	 * Nom du user
	 */
	private static String user = "postgres";

	/**
	 * Mot de passe du user
	 */
	private static String passwd = "root";

	/**
	 * Objet Connection
	 */
	private static Connection connect;

	/**
	 * M�thode qui va retourner notre instance et la cr�er si elle n'existe
	 * pas...
	 * 
	 * @return
	 */
	public static Connection getInstance() {
		if (connect == null) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
					System.out.println("classe non trouv�e");
			}

			try {

				connect = DriverManager.getConnection(url, user, passwd);
				System.out.println("Connexion ok ");
			} catch (SQLException e) {
				System.out.println("RAD_ATELIERBIO: " + e.getMessage());
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"ERREUR DE CONNEXION ! ", JOptionPane.ERROR_MESSAGE);
			}
		}
		return connect;
	}
}