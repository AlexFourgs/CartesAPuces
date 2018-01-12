package com.atelierBio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.atelierBio.bean.User;

public class UserDAO extends DAO<User> {

	public UserDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(User obj) throws Exception {
		String rqtInsert = "insert into public.ab_user values (newUserID(), ?, ?, ?, ?, ?, ?, ?, ?);";

		/* Implémentation de la méthode définie dans l'interface UtilisateurDao */
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		try {
			/* Récupération d'une connexion depuis la Factory */
			connexion = this.connect;
			preparedStatement = initialisationRequetePreparee(connexion,rqtInsert, true, obj.getLastName(), obj.getFirstName(), obj.getMail(), obj.getLogin(), obj.getPassword(), obj.getHistoR(), obj.getHistoG(), obj.getHistoB());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new Exception(
						"Echec de création de l'utilisateur, aucune ligne ajout�e dans la table.");
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				/*
				 * Puis initialisation de la propriété id du bean Utilisateur
				 * avec sa valeur
				 */
				obj.setId(valeursAutoGenerees.getString(1));
			} else {
				throw new Exception(
						"Echec de création de l'utilisateur en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new Exception(e);
		}
		return true;
	}

	@Override
	public boolean delete(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User find(String id) {
		User Unuser = new User();

		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM ab_user WHERE iduser = '" + id + "'");
			if (result.first())
				Unuser = new User(result.getString("lastName"), result.getString("firstName"), result.getString("mail"), result.getString("login"), result.getString("password"),  result.getString("histoR"), result.getString("histoG"), result.getString("histoB"));
			Unuser.setId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Unuser;
	}
	@Override
	public User find(String login, String password) {
		User Unuser = new User();

		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM ab_user WHERE login = '" + login + "' and password = '" + password + "'");
			if (result.first()) {
				Unuser = new User (result.getString("iduser"), result.getString("lastName"), result.getString("firstName"), result.getString("mail"), result.getString("login"), result.getString("password"),  result.getString("histoR"), result.getString("histoG"), result.getString("histoB"));
			}
			else {
				Unuser = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Unuser;
	}

	@Override
	public User find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Initialise la requête préparée basée sur la connexion passée en argument,
	 * avec la requête SQL et les objets donnés.
	 */
	public static PreparedStatement initialisationRequetePreparee(
			Connection connexion, String sql, boolean returnGeneratedKeys,
			Object... objets) throws SQLException {
		PreparedStatement preparedStatement = connexion.prepareStatement(sql,
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS
						: Statement.NO_GENERATED_KEYS);
		for (int i = 0; i < objets.length; i++) {
			preparedStatement.setObject(i + 1, objets[i]);
		}
		return preparedStatement;
	}
}
