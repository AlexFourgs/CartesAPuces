import com.atelierBio.bean.User;
import com.atelierBio.connexion.Connexion;
import com.atelierBio.dao.DAO;
import com.atelierBio.dao.UserDAO;

public class FirstTest {
	public static void main(String[] args) {
		// Testons des élèves
		System.out.println("Affichage des users");
		DAO<User> userDAO = new UserDAO(Connexion.getInstance());

		User aUser = userDAO.find("ju3X5Zj5SPRlist");
		System.out.println("Utilisateur id=" + aUser.getId() + "  - " + aUser.getNom() + " " + aUser.getPrenom() + " "
				+ aUser.getMail());

	}
}