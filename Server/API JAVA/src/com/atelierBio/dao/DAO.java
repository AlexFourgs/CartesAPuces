package com.atelierBio.dao;

import java.sql.Connection;

import com.atelierBio.bean.User;
import com.atelierBio.connexion.Connexion;

public abstract class DAO<T> {
  protected Connection connect = null;
   
  public DAO(Connection conn){
    this.connect = conn;
  }
   
  /**
  * M�thode de cr�ation
  * @param obj
  * @return boolean 
 * @throws Exception 
  */
  public abstract boolean create(T obj) throws Exception;

  /**
  * M�thode pour effacer
  * @param obj
  * @return boolean 
  */
  public abstract boolean delete(T obj);

  /**
  * M�thode de mise � jour
  * @param obj
  * @return boolean
  */
  public abstract boolean update(T obj);

  /**
  * M�thode de recherche des informations
  * @param id
  * @return T
  */
  public abstract T find(int id);

  /**
  * M�thode de recherche des informations
  * @param id
  * @return T
  */
  public abstract T find(String id);

public User find(String login, String password) {
	// TODO Auto-generated method stub
	return null;
}
}