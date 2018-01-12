

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atelierBio.bean.User;
import com.atelierBio.connexion.Connexion;
import com.atelierBio.dao.DAO;
import com.atelierBio.dao.UserDAO;

/**
 * Servlet implementation class AtelierBio
 */
@WebServlet("/AtelierBio")
public class AtelierBio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AtelierBio() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("action : " + request.getParameter("action"));
		String jsonData = "";
		if(!action.equals("")) {
			System.out.println(request.getParameter("action"));
			DAO<User> userDAO = new UserDAO(Connexion.getInstance());
			switch(action) {
			default:
				System.out.println("Default case : none or wrong action given in parameters");
				break;
				
			case "login":
				System.out.println("entre case login");
				String login = request.getParameter("login");
				String sha256password = request.getParameter("password");
				String sha256id = request.getParameter("id");
				System.out.println("login : " + login);
				System.out.println("password : " + sha256password);
				System.out.println("id : " + sha256id);
				User userConnection = userDAO.find(login, sha256password);
				if(userConnection == null) {
					System.out.println("No results");
					jsonData = "{\"result\" : \"ko\"}";
				}
				else {
					if(!userConnection.getId().equals("")) {
						System.out.println("user non vide");
						try {
							if(sha256String(userConnection.getId()).equals(sha256id)) {
								System.out.println("sha256 du login en base équivaut au login hashé envoyé");
								jsonData = "{\"result\" : \"ok\"}";
							}
							else {
								System.out.println("sha256 du login en base ne correspond pas au login hashé envoyé");
								jsonData = "{\"result\" : \"ko\"}";
							}
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						System.out.println("No id for this login/password");
					}
				}
				break;
				
			case "register":
				System.out.println("Entree case register");
				String newUserLastName = request.getParameter("lastName");
				String newUserFirstName = request.getParameter("firstName");
				String newUserMail = request.getParameter("mail");
				String newUserLogin = request.getParameter("login");
				String newUsersha256password = request.getParameter("password");
				String newUserHistoR = request.getParameter("histoR");
				String newUserHistoG = request.getParameter("histoG");
				String newUserHistoB = request.getParameter("histoB");
				User newUser = new User(newUserLastName, newUserFirstName, newUserMail, newUserLogin, newUsersha256password, newUserHistoR, newUserHistoG, newUserHistoB);
				try {
					userDAO.create(newUser);
					//Si aucun idUser n'est retourné
					if(newUser.getId().equals("")) {
						System.out.println("result ko");
						jsonData = "{\"result\" : \"ko\"}";
					}
					else {
						System.out.println("result ok");
						jsonData = "{\"result\" : \"ok\", \"newUserID\" : \"" + sha256String(newUser.getId()) + "\"}";
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}

	    response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    out.println(jsonData) ; 
	    out.close();
	}

	private String sha256String(String inputStr) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] bytesOfMessage = inputStr.getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] thedigest = md.digest(bytesOfMessage);
		StringBuilder sb = new StringBuilder();
		for (byte b : thedigest) {
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
