package com.atelierBio.bean;

import java.sql.Date;

public class Session {
	private int idSession;
	private Date dateDebutSession;
	private Date datefinSession;

	public Session(int idSession, Date dateDebutSession) {
		super();
		this.idSession = idSession;
		this.dateDebutSession = dateDebutSession;
	}

	public int getIdSession() {
		return idSession;
	}

	public Date getDateDebutSession() {
		return dateDebutSession;
	}

	public void setDateDebutSession(Date dateDebutSession) {
		this.dateDebutSession = dateDebutSession;
	}

	public Date getDatefinSession() {
		return datefinSession;
	}

	public void setDatefinSession(Date datefinSession) {
		this.datefinSession = datefinSession;
	}

}
