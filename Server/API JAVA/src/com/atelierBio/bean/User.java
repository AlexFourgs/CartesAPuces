package com.atelierBio.bean;

public class User {
	private String id;
	private String nom;
	private String prenom;
	private String mail;
	private String login;
	private String empreintePassword;

	public User(String nom, String prenom, String mail, String login) {
		super();
	
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.login = login;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
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

	public String getEmpreintePassword() {
		return empreintePassword;
	}

	public void setEmpreintePassword(String empreintePassword) {
		this.empreintePassword = empreintePassword;
	}

}
