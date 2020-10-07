package whist.whistmodel;

/**
 * Modélise une carte à jouer.
 */
public class Carte {

	private Couleur couleur;
	private int rang;
	
	/**
	 * Constructeur.
	 * 
	 * Le rang va de 0 pour le 2 à 12 pour l'As
	 * @param couleur couleur de la carte
	 * @param rang rang de la carte
	 */
	public Carte(Couleur couleur, int rang) {
		this.couleur = couleur;
		this.rang = rang;
	}
	
	/**
	 * Renvoie la couleur de la carte.
	 * 
	 * @return la couleur de la carte
	 */
	public Couleur getCouleur() {
		return couleur;
	}
	
	/**
	 * Renvoie le rang de la carte.
	 * 
	 * Les rangs vont de 0 (pour le 2) à 12 (pour l'As)
	 * @return le rang de la carte
	 */
	public int getRang() {
		return rang;
	}
	
	
	/**
	 * Renvoie le code de la carte.
	 * 
	 * Le code est de la forme <couleur>_<rang+2> où rang+2 est en hexadécimal. 
	 * Par exemple, PIQUE_9 pour le 9 de pique et TREFLE_B pour le valet de trèfle.
	 * @return Une chaîne identifiant la carte
	 */
	@Override
	public String toString() {
		return couleur.name()+String.format("_%X", rang+2);
	}
};
