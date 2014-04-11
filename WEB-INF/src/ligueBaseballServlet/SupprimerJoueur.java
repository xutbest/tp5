package ligueBaseballServlet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ligueBaseball.GestionLigueBaseball;
import ligueBaseball.LigueBaseballException;

/**
 * Classe pour login syst�me de gestion de biblioth�que
 * <P>
 * Syst�me de gestion de biblioth�que &copy; 2004 Marc Frappier, Universit� de
 * Sherbrooke
 */

@SuppressWarnings("serial")
public class SupprimerJoueur extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

	// Dans les formulaire, on utilise la m�thode POST
	// donc, si le servlet est appel� avec la m�thode GET
	// on retourne un page d'erreur, afin de ne pas permettre
	// � l'utilisateur d'appeler un servler directement
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.sendError(response.SC_INTERNAL_SERVER_ERROR, "Acc�s
		// invalide");
		//doPost(request, response);
		if(request.getSession().getAttribute("etat") != null){
			if (request.getParameter("supprimerJoueur") != null)
				traiterSupprimerJoueur(request, response);
			else{
				RequestDispatcher dispatcher = request.getRequestDispatcher("/supprimerjoueur.jsp");
				dispatcher.forward(request, response);
			}
		}else{
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		}
		
	}
	
	public void traiterSupprimerJoueur(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			if ( request.getParameter("nomJoueur").isEmpty() || request.getParameter("prenomJoueur").isEmpty()){
				System.out.println("suprimer jouer??");
				throw new LigueBaseballException("Impossible de supprimer un joueur sans nom et prenom");
			}else{
				String prenom = request.getParameter("prenomJoueur");
				String nom = request.getParameter("nomJoueur");
				GestionLigueBaseball.gestionJoueur.supprimer(nom, prenom);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/menu.jsp");
				dispatcher.forward(request, response);
			}
		} catch (LigueBaseballException e) {
			List listeMessageErreur = new LinkedList();
			listeMessageErreur.add(e.getMessage());
			request.setAttribute("listeMessageErreur", listeMessageErreur);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/supprimerjoueur.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
		
		}
	}

} // class
