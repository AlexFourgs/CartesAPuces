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
	private static String passwd = "azerty";

	/**
	 * Objet Connection
	 */
	private static Connection connect;

	/**
	 * Méthode qui va retourner notre instance et la créer si elle n'existe
	 * pas...
	 * 
	 * @return
	 */
	public static Connection getInstance() {
		if (connect == null) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
					System.out.println("classe non trouvée");
			}

			try {

				connect = DriverManager.getConnection(url, user, passwd);
				System.out.println("coucou ");
			} catch (SQLException e) {
				System.out.println("RAD_ATELIERBIO: " + e.getMessage());
				JOptionPane.showMessageDialog(null, e.getMessage(),
						"ERREUR DE CONNEXION ! ", JOptionPane.ERROR_MESSAGE);
			}
		}
		return connect;
	}
}