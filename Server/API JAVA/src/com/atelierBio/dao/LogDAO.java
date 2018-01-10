package com.atelierBio.dao;

import java.sql.Connection;

import com.atelierBio.bean.Log;

public class LogDAO  extends DAO<Log>  {

	public LogDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Log obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Log obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Log obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Log find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Log find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
