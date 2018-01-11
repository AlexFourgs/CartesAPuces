
import java.io.IOException;
import java.io.PrintWriter;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Affichage des users");
		DAO<User> userDAO = new UserDAO(Connexion.getInstance());

		User aUser = userDAO.find("ju3X5Zj5SPRlist");
		out.println("Utilisateur id=" + aUser.getId() + "  - " + aUser.getNom()
				+ " " + aUser.getPrenom() + " " + aUser.getMail());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
