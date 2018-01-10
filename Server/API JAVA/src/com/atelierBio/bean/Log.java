package com.atelierBio.bean;

import java.sql.Date;

public class Log {
	private int idLog;
	private Date dateLog;
	private String textLog;

	public Log(int idLog, Date dateLog, String textLog) {
		super();
		this.idLog = idLog;
		this.dateLog = dateLog;
		this.textLog = textLog;
	}

	public int getIdLog() {
		return idLog;
	}

	public void setIdLog(int idLog) {
		this.idLog = idLog;
	}

	public Date getDateLog() {
		return dateLog;
	}

	public void setDateLog(Date dateLog) {
		this.dateLog = dateLog;
	}

	public String getTextLog() {
		return textLog;
	}

	public void setTextLog(String textLog) {
		this.textLog = textLog;
	}

}
