package com.atelierBio.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.atelierBio.bean.User;


public class UserDAO extends DAO<User> {

	public UserDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(User obj) {
		// TODO Auto-generated method stub
		return false;
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
		User user = new User();

		try {
			ResultSet result = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT * FROM ab_user WHERE iduser = '" + id + "'");
			if (result.first())
				user = new User(id, result.getString("nomuser"), result.getString("prenomuser"),
						result.getString("mailuser"), result.getString("loginuser"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
