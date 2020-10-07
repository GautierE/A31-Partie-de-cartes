package whist.whistmodel;

import java.util.ArrayList;

/**
 * Modélise un joueur d'une partie de whist.
 */
public class Joueur { //extends Sujet{
	
	private final String nom;
	private final ArrayList<Carte> cartes = new ArrayList<>(13);
	private final Equipe equipe;
	
	
	/**
	 * Construit un joueur dont le nom et l'équipe sont passés en argument.
	 * 
	 * @param nom nom du joueur
	 * @param equipe équipe à laquelle appartient le joueur
	 */
	public Joueur(String nom, Equipe equipe) {
		this.equipe = equipe;
		this.nom = nom;
	}
	/**
	 * Construit un joueur dont l'équipe est passée en argument.
	 * 
	 * Le joueur reçoit comme nom par défaut la chaîne renvoyée par 
	 * la méthode toString de la classe Object
	 * @param equipe équipe à laquelle appartient le joueur
	 */

	public Joueur(Equipe equipe) {
		this.equipe = equipe;
		this.nom = super.toString();
	}
	
	
	
	
	/**
	 * Ajoute une carte dans la main du joueur.
	 * 
	 * Utilisé lors de la distribution des cartes
	 * @param c la carte à ajouter à la main du joueur
	 */
	public void recevoirCarte( Carte c) {
		cartes.add( c );
	}
	

	/**
	 * Supprime la carte passée en argument de la main du joueur.
	 * 
	 * @param c carte à retirer de la main de this
	 * @return false si la carte c n'était pas dans la main du joueur
	 */
	public boolean jouer( Carte c ) {
		//notifierObservateurs();
		return cartes.remove( c );
	}


	
	/**
	 * Ajoute le pli au tas de l'équipe du joueur.
	 * 
	 * @param p le pli remporté par ce joueur
	 */
	public void ramasserPli( Pli p) {
		
		getEquipe().ajouterPli(p); 
	}
	
	

	/**
	 * Determine si le joueur possede une carte 
	 * de la couleur passee en argument dans sa main.
	 * 
	 * @param couleur PIQUE COEUR CARREAU ou TREFLE
	 * @return true si le joueur a la couleur "couleur" dans sa main
	 */
	public Boolean possede( Couleur couleur ) {
		for (Carte carte : cartes) {
			if ( carte.getCouleur() == couleur ) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Indique si la main du joueur est vide ou non.
	 * 
	 * @return true si le joueur a encore des cartes en main
	 */
	public boolean possedeCarte() {
		return  ! cartes.isEmpty();
	}

	
	/**
	 * Renvoie le nom du joueur.
	 * 
	 * @return le nom de this
	 */
	public String getNom() {
		return nom;
	}
	
	
	/**
	 * Renvoie l'équipe à laquelle appartient le joueur.
	 * 
	 * @return l'équipe de this
	 */
	public Equipe getEquipe() {
		return equipe;
	}

	
	/**
	 * Renvoie la main du joueur.
	 * 
	 * @return la main du joueur
	 */
	public ArrayList<Carte> getCartes() {
		return new ArrayList<>( cartes );
	}
	


	
};
