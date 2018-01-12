package com.atelierBio.bean;

public class User {
	private String id;
	private String lastName;
	private String firstName;
	private String mail;
	private String login;
	private String password;
	private String histoR;
	private String histoG;
	private String histoB;

	public User(String lastName, String firstName, String mail, String login, String password, String histoR, String histoG, String histoB) {
		super();
	
		this.lastName = lastName;
		this.firstName = firstName;
		this.mail = mail;
		this.login = login;
		this.password = password;
		this.histoR = histoR;
		this.histoG = histoG;
		this.histoB = histoB;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String id, String lastName, String firstName, String mail, String login, String password,
			String histoR, String histoG, String histoB) {
		super();
		
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.mail = mail;
		this.login = login;
		this.password = password;
		this.histoR = histoR;
		this.histoG = histoG;
		this.histoB = histoB;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getHistoR() {
		return histoR;
	}

	public void setHistoR(String histoR) {
		this.histoR = histoR;
	}

	public String getHistoG() {
		return histoG;
	}

	public void setHistoG(String histoG) {
		this.histoG = histoG;
	}

	public String getHistoB() {
		return histoB;
	}

	public void setHistoB(String histoB) {
		this.histoB = histoB;
	}
}
