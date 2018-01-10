package com.atelierBio.bean;

public class Connect {
	private int idUser;
	private int idSession;

	public Connect(int idUser, int idSession) {
		super();
		this.idUser = idUser;
		this.idSession = idSession;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdSession() {
		return idSession;
	}

	public void setIdSession(int idSession) {
		this.idSession = idSession;
	}

}
