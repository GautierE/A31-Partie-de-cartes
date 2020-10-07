package whist.whistmodel;

import java.util.ArrayList;

/**
 * Modélise une équipe dans une partie de Whist.
 */
public class Equipe {

	// Les plis remportés par l'équipe
	private ArrayList<Pli> plis =  new ArrayList<>(13);
	
	private int  points = 0;
	
	private MethodeDeDecompteDesPoints decompte;
	
	
	/**
	 * Constructeur des équipes.
	 * 
	 * Une équipe encapsule des plis dont la valeur sera calculée par la méthode passée en argument.
	 * Cette valeur sera ajoutée aux points de l'équipe à la fin de chaque donne.
	 * 
	 * @param m la méthode utilisée pour décompter les points d'une donne.
	 */
	public Equipe( MethodeDeDecompteDesPoints m) {
		decompte = m;
	}


	/**
	 * Ajoute un pli au tas des plis remportés par l'équipe.
	 * 
	 * @param pli le pli à ajouter
	 */
	public void ajouterPli(Pli pli) {
		plis.add(pli);
	}


	
	/**
	 * Renvoie les plis del'équipe.
	 * 
	 * @return la liste des plis de cette équipe
	 */
	public ArrayList<Pli> getPlis() {
		return new ArrayList<>(plis);
	}



	/**
	 * Renvoie les points remportés par l'équipe à la fin de la partie.
	 * 
	 * @return les points de l'équipe
	 */
	public int getPoints() {
		return points;
	}


	/**
	 * Mets à jour les points de l'équipe à la fin d'une donne
	 * et vide la liste des plis en cours.
	 * 
	 * @param la couleur d'atout
	 */
	public void compterPoints( Couleur atout ) {

		points += decompte.getPoints(plis.toArray( new Pli[0] ), atout);
		plis.clear();
		
	}


	
	/**
	 * Renvoie la liste des plis de l'équipe sous forme de chaîne de caractères.
	 *
	 * @return Une chaîne détaillant les cartes des plis remportés par this.
	 */
	@Override
	public String toString() {
		return plis.toString();
	}
};
