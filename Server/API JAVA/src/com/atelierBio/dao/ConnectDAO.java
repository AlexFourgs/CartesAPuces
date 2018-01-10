package com.atelierBio.dao;

import java.sql.Connection;

import com.atelierBio.bean.Connect;

public class ConnectDAO  extends DAO<Connect>  {

	public ConnectDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Connect obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Connect obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Connect obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connect find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connect find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
