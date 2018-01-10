package com.atelierBio.dao;

import java.sql.Connection;

import com.atelierBio.bean.Session;

public class SessionDAO  extends DAO<Session>  {

	public SessionDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Session obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Session obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Session obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Session find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session find(String id) {
		// TODO Auto-generated method stub
		return null;
	}


}
