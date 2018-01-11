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
		String rqtInsert = "INSERT INTO public.ab_user"
				+ "VALUES (newUserID(), ?, ?, ?, ?, ?);";

		/* Impl�mentation de la m�thode d�finie dans l'interface UtilisateurDao */
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;
		try {
			/* R�cup�ration d'une connexion depuis la Factory */
			connexion = this.connect;
			preparedStatement = initialisationRequetePreparee(connexion,
					rqtInsert, true, obj.getNom(), obj.getPrenom(),
					obj.getLogin(), obj.getEmpreintePassword());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourn� par la requ�te d'insertion */
			if (statut == 0) {
				throw new Exception(
						"�chec de la cr�ation de l'utilisateur, aucune ligne ajout�e dans la table.");
			}
			/* R�cup�ration de l'id auto-g�n�r� par la requ�te d'insertion */
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				/*
				 * Puis initialisation de la propri�t� id du bean Utilisateur
				 * avec sa valeur
				 */
				obj.setId(valeursAutoGenerees.getString(1));
			} else {
				throw new Exception(
						"�chec de la cr�ation de l'utilisateur en base, aucun ID auto-g�n�r� retourn�.");
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
				Unuser = new User(result.getString("nomuser"),
						result.getString("prenomuser"),
						result.getString("mailuser"),
						result.getString("loginuser"));
			Unuser.setId(id);
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
	 * Initialise la requ�te pr�par�e bas�e sur la connexion pass�e en argument,
	 * avec la requ�te SQL et les objets donn�s.
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
