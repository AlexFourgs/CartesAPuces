package com.atelierBio.bean;

public class Concern {
	private int idLog;
	private int idSession;

	public Concern(int idLog, int idSession) {
		super();
		this.idLog = idLog;
		this.idSession = idSession;
	}

	public int getIdLog() {
		return idLog;
	}

	public void setIdLog(int idLog) {
		this.idLog = idLog;
	}

	public int getIdSession() {
		return idSession;
	}

	public void setIdSession(int idSession) {
		this.idSession = idSession;
	}

}
