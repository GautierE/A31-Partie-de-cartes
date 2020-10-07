package whist.whistmodel;

import java.util.ArrayList;

/**
 * Modélise un pli dans une partie de whist.
 */
public class Pli {

	// La liste ordonnée des cartes composant le pli
	private final ArrayList<Carte> cartes = new ArrayList<>( 4 );
	

	
	/**
	 * Ajoute une carte au pli en cours de construction.
	 * 
	 * @param c la carte à ajouter à this
	 */
	public void add(Carte c) {
		cartes.add( c );
		
	}

	
	
	/**
	 * Renvoie la couleur de la premiere carte de ce pli.
	 * 
	 * @return la couleur de la 1ere carte de ce pli
	 */
	public Couleur getCouleurDemandee() {
		return cartes.get(0).getCouleur();
	}



	/**
	 * Renvoie l'index de la carte gagnante de ce pli.
	 * 
	 * @param atout l'atout de la partie de Whist en cours
	 * @return l'index de la carte maitresse dans ce pli
	 */
	public int indexGagnante(Couleur atout) {
		
		int num_gagnante = 0;
		Couleur c_gagnante = cartes.get(0).getCouleur();
		int r_gagnante = cartes.get(0).getRang();
		for( int i = 1; i < 4; i++ ) {
			Couleur c = cartes.get(i).getCouleur();
			int r = cartes.get(i).getRang();
			if( c == c_gagnante && r > r_gagnante ) {
				num_gagnante = i;
				r_gagnante = r;
			}
			else if( c == atout && c_gagnante != atout ) {
				c_gagnante = atout;
				num_gagnante = i;
				r_gagnante = r;
			}
		}
		return num_gagnante;
	}


	
	/**
	 * Renvoie les cartes composant ce pli.
	 * 
	 * @return les cartes de this
	 */
	public ArrayList<Carte> getCartes() {
		return new ArrayList<>( cartes );
	}
	

	/**
	 * Renvoie le nombre de cartes dans le pli.
	 * 
	 * @return La taille actuelle de ce pli.
	 */
	public int taille() {
		
		return cartes.size();
		
	}
	
	/**
	 * Renvoie les cartes de ce pli sous forme de chaîne de caractères.
	 *
	 * @return Une chaîne décrivant les cartes contenues dans ce pli
	 */
	@Override
	public String toString() {
		return cartes.toString();
	}

}
