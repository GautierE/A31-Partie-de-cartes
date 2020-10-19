package whist.whistcontroleur;

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
public class WhistControleur extends TemplateControleur {

	
	// Constructeur

	/**
	 * Renvoie un controleur associé à une instance de partie.
	 * 
	 * @param p la partie de cartes controlée par ce controleur
	 */
	public WhistControleur(Partie p) {
		setPartie(p);
	}
	
	// Méthodes d'instances

	/**
	 * Fait avancer le déroulement de la partie de carte après chaque action d'un joueur
	 */
	public void avancer() {

		if (getPliCourant() == null) {
			// début de la donne
			setDonneur(getPartie().getJoueur(0));
			setPliCourant(new Pli());
			setJoueurCourant(getPartie().getJoueurGauche(getDonneur()));
		}
		else {
			// Vérification de la règle du jeu et du joueur qui posé la carte
			boolean succes = true;

			if (getPliCourant().taille() > 0) {
				Couleur demandee = getPliCourant().getCouleurDemandee();
				succes = getCarteCourante().getCouleur() == demandee || !getJoueurCourant().possede(demandee);
			}

			if (!succes)
				return;

			// le joueur courant joue la carte courante
			succes = getJoueurCourant().jouer(getCarteCourante());

			if (!succes)
				// Le joueur courant ne possède pas la carte Courante !
				return;

			getPliCourant().add(getCarteCourante());
			if (getPliCourant().taille() < 4)
				// au moins encore 1 carte à jouer dans ce pli
				setJoueurCourant(getPartie().getJoueurGauche(getJoueurCourant()));
			else {
				// Pli terminé
				// Remarque: le joueur à gauche du joueur courant est celui qui a commencé le
				// pli courant
				Joueur gagnant = getPartie().getJoueurGagnant(getPliCourant(), getPartie().getJoueurGauche(getJoueurCourant()));
				gagnant.ramasserPli(getPliCourant());

				if (getJoueurCourant().possedeCarte()) {
					// au moins encore une levée à faire
					setJoueurCourant(gagnant);
					setPliCourant(new Pli());
				}
				else {
					for (int i = 0; i < 2; i++)
					{
						getPartie().getEquipe(i).compterPoints(getPartie().getAtout());
					}
					if(getPartie().getEquipe(0).getPoints() < 20 || getPartie().getEquipe(1).getPoints() < 20)
					{
						// Recommence une donne en changeant de donneur
						setDonneur(getPartie().getJoueurGauche(getDonneur()));
						setPliCourant(new Pli());
						setJoueurCourant(getPartie().getJoueurGauche(getDonneur()));
					}
					else
					{
						setPartieFinie(true);
					}
				}
			}
		}
		notifierObservateurs();

	}

	/**
	 * Distribue les cartes aux joueurs et fixe l'atout.
	 * 
	 * Les cartes sont distribuées de façon aléatoire.
	 */
	public void distribuerCartes() {

		PaquetDeCartes pc = new PaquetDeCartes(52);

		int j = 0;
		Carte c = null;

		while (pc.hasNext()) {
			c = pc.donnerCarte();
			getPartie().getJoueur(j).recevoirCarte(c);
			j = (j + 1) % 4;
		}

		assert c != null;
		getPartie().setAtout(c.getCouleur());
		notifierObservateurs();
	}
}
