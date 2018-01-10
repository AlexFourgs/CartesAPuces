package com.atelierBio.dao;

import java.sql.Connection;

import com.atelierBio.bean.Concern;

public class ConcernDAO extends DAO<Concern> {

	public ConcernDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean create(Concern obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Concern obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Concern obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Concern find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Concern find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
