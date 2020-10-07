package whist.whistcontroleur;

import observateursujet.Sujet;
import whist.whistmodel.Carte;
import whist.whistmodel.Couleur;
import whist.whistmodel.Joueur;
import whist.whistmodel.PaquetDeCartes;
import whist.whistmodel.Partie;
import whist.whistmodel.Pli;

/**
 * @author loic
 *
 */
public class WhistControleur extends Sujet {

	private final Partie partie;

	private Joueur joueurCourant;

	private Carte carteCourante;

	private Pli pliCourant;

	
	// Constructeur

	/**
	 * Renvoie un controleur associé à une instance de Partie.
	 * 
	 * @param p la partie de cartes controlée par ce controleur
	 */
	public WhistControleur(Partie p) {
		this.partie = p;
	}
	
	// Méthodes d'instances

	/**
	 * Fait avancer le déroulement de la partie de carte après chaque action d'un joueur
	 */
	public void avancer() {

		if (pliCourant == null) {
			// début de la donne
			Joueur donneur = partie.getJoueur(0);
			distribuerCartes();
			pliCourant = new Pli();
			joueurCourant = partie.getJoueurGauche(donneur);
		}

		else {
			// Vérification de la règle du jeu et du joueur qui posé la carte
			boolean succes = true;

			if (pliCourant.taille() > 0) {
				Couleur demandee = pliCourant.getCouleurDemandee();
				succes = carteCourante.getCouleur() == demandee || !joueurCourant.possede(demandee);
			}

			if (!succes)
				return;

			// le joueur courant joue la carte courante
			succes = joueurCourant.jouer(carteCourante);

			if (!succes)
				// Le joueurCourant ne possède pas la carteCourante !
				return;

			pliCourant.add(carteCourante);

			if (pliCourant.taille() < 4)
				// au moins encore 1 carte à jouer dans ce pli
				joueurCourant = partie.getJoueurGauche(joueurCourant);

			else {
				// Pli terminé
				// Remarque: le joueur à gauche du joueur courant est celui qui a commencé le
				// pli courant
				Joueur gagnant = partie.getJoueurGagnant(pliCourant, partie.getJoueurGauche(joueurCourant));
				gagnant.ramasserPli(pliCourant);

				if (joueurCourant.possedeCarte()) {
					// au moins encore une levée à faire
					joueurCourant = gagnant;
					pliCourant = new Pli();
				}
				
				else
					for( int i = 0; i < 2; i++ )
						partie.getEquipe( i ).compterPoints( partie.getAtout() );
			}
		}

	notifierObservateurs();

	}

	/**
	 * Distribue les cartes aux joueurs et fixe l'atout.
	 * 
	 * Les cartes sont distribuées de façon aléatoire.
	 */
	protected void distribuerCartes() {

		PaquetDeCartes pc = new PaquetDeCartes(52);

		int j = 0;
		Carte c = null;

		while (pc.hasNext()) {
			c = pc.donnerCarte();
			partie.getJoueur(j).recevoirCarte(c);
			j = (j + 1) % 4;
		}

		assert c != null;
		partie.setAtout(c.getCouleur());

	}

	/**
	 * Renvoie l'instance de Partie associée à ce controleur.
	 * 
	 * @return la partie controlée par ce controleur
	 */
	public Partie getPartie() {
		return partie;
	}

	/**
	 * Renvoie le joueur actif dans la donne en cours.
	 * 
	 * @return le joueurCourant
	 */
	public Joueur getJoueurCourant() {
		return joueurCourant;
	}

	/**
	 * Renvoie le pli en cours de construction dans le tour de table.
	 * 
	 * @return le pliCourant
	 */
	public Pli getPliCourant() {
		return pliCourant;
	}

	/**
	 * Renvoie la carte jouée par le joueur courant.
	 * 
	 * @return la dernière carte sélectionné par une des vues.
	 */
	public Carte getCarteCourante() {
		return carteCourante;
	}

	// Modifieur

	/**
	 * Utilisé par les vues pour désigner la carte choisie par un des joueurs.
	 * 
	 * @param carteJouee la nouvelle valeur de l'attribur carteCourante
	 */
	public void setCarteCourante(Carte carteJouee) {
		this.carteCourante = carteJouee;
	}

}
