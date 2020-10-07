package whist.whistmodel;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 */
public class PaquetDeCartes {
	
	private static final Random r = new Random();
	private final ArrayList<Carte> cartes ;
	
	/**
	 * Construit un paquet de 52 cartes.
	 * @param j 
	 * 
	 */
	public PaquetDeCartes(int n) {
		
		cartes = new ArrayList<>( n );
		
		int rangMax = n / 4;
		for( Couleur c: Couleur.values() )
			for( int i = 0; i < rangMax; i++)
				cartes.add( new Carte(c, i) );
	}

	/**
	 * Renvoie une carte tirée aléatoirement dans le jeu.
	 * 
	 * @return une carte du jeu
	 */
	public Carte donnerCarte( ) {
		return cartes.remove( r.nextInt( cartes.size() ) );
	}

	/**
	 * Indique si le paquet a distribué ou non toutes ses cartes.
	 * 
	 * @return true si le paquet contient encore des cartes
	 */
	public boolean hasNext() {
		return cartes.size() > 0;
	}
};
