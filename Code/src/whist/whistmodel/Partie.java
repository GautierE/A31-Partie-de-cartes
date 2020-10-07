package whist.whistmodel;

import java.util.Arrays;
import java.util.List;

/**
 * Modélise une partie de Whist.
 */
public class Partie {
	
	private Couleur atout;
	private Joueur[] joueur = new Joueur[4];

	
	/**
	 * Constructeur d'une partie de Whist avec les joueurs dont 
	 * les noms sont donnés en argument.
	 * 
	 * L'ordre des joueurs détermine leur placement : le premier
	 *  de la liste est le joueur à gauche du donneur et le dernier est le donneur
	 * @param nomJoueurs nom des joueurs (optionnel)
	 */
	public Partie(String ... nomJoueurs) {
		MethodeDeDecompteDesPoints m = new DecompteWhist();
		Equipe[]  eq =  {new Equipe( m ), new Equipe( m )} ;
		int position = 0;
		for (; position < nomJoueurs.length; position++ ) 
			joueur[position] = new Joueur(nomJoueurs[position], eq[position%2] );
		for (; position < 4; position++ )
			joueur[position] = new Joueur( eq[position%2] );
	}

	
	/**
	 * Renvoie l'atout.
	 * 
	 * @return la couleur d'atout
	 */
	public Couleur getAtout() {
		return atout;
	}


	/**
	 * Permet au controleur d'indiquer la couleur d'atout.
	 * 
	 * @param couleur la couleur d'atout
	 */
	public void setAtout(Couleur couleur) {
		this.atout = couleur;
	}

	
	/**
	 * Renvoie l'équipe du joueur d'indice i.
	 * 
	 * @return l'équipe du joueur d'indice i.
	 */
	public Equipe getEquipe( int i ) {
		return joueur[ i%2 ].getEquipe();
	}


	
	/**
	 * Renvoie la liste ordonnée des joueurs (donneur en dernière position)
	 * @return la liste des joueurs
	 */
	public List<Joueur> getJoueurs() {
		return Arrays.asList( joueur );
	}

	

	/**
	 * Renvoie la référence du joueur dont l'index est passé en argument.
	 * 
	 * @param i l'index du joueur
	 * @return le joueur d'index i
	 */
	public Joueur getJoueur(int i) {
		return joueur[i];
	}


	
	/**
	 * Renvoie le joueur à gauche du joueur passé en paramètre.
	 * 
	 * @param j un joueur de la partie
	 * @return le joueur à sa gauche
	 */
	public Joueur getJoueurGauche( Joueur j) {
		return joueur[( getJoueurs().indexOf( j ) + 1) % 4];
	}


	
	/**
	 * Renvoie le joueur à droite du joueur passé en paramètre.
	 * 
	 * @param j un joueur de la partie
	 * @return le joueur à sa droite
	 */
	public Joueur getJoueurDroite( Joueur j) {
		return joueur[( getJoueurs().indexOf( j ) + 3) % 4];
	}
	
	
	/**
	 * Renvoie le joueur qui remporte le pli passé en argument.
	 * 
	 * @param p levee dont on veut connaitre le gagnant
	 * @param j0 le premier joueur de la levee p
	 * @return la reference du vainqueur
	 */
	public Joueur getJoueurGagnant( Pli p, Joueur j0) {
		int decalage = p.indexGagnante( atout );
		int indexInitial = getJoueurs().indexOf( j0 );
		return joueur[ ( indexInitial + decalage ) % 4 ];
	}


	/**
	 * Renvoie l'équipe gagnante.
	 * 
	 * @return Le numéro de l'équipe gagnante et son score
	 */
	public int[] resultats() {
		return new int[] { getEquipe( 0 ).getPoints(), getEquipe( 1 ).getPoints() };
	}

};
