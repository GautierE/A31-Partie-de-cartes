package whist.whistvue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import observateursujet.Observateur;
import whist.whistcontroleur.WhistControleur;
import whist.whistmodel.Carte;
import whist.whistmodel.Joueur;
import whist.whistmodel.Pli;

/**
 * 
 */
public class VueJoueur extends JFrame implements Observateur {

	protected WhistControleur controleur;

	protected Joueur joueur;

	/**
	 * Construit une vue pour le joueur dont le modèle est passé en paramètre.
	 * 
	 * @param controleur le contrôleur de cette vue dans le modèle MVC
	 * @param joueur     la référence de l'instance de Joueur liée à cette vue
	 */
	public VueJoueur(WhistControleur controleur, Joueur joueur) {
		super(joueur.getNom());
		this.controleur = controleur;
		this.joueur = joueur;

		controleur.ajouterObservateur(this);

		// widgets
		JLabel atout = new JLabel();
		atout.setHorizontalAlignment(SwingConstants.CENTER);
		atout.setOpaque(true);
		atout.setBackground(Color.WHITE);

		JList<Carte> main = new JList<>();
		main.setVisibleRowCount(13);
		main.setForeground(Color.BLUE);
		main.setFixedCellWidth(100);

		JLabel jlbInfo = new JLabel(); // 
		jlbInfo.setOpaque(true);
		jlbInfo.setBackground(Color.YELLOW);
		jlbInfo.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel jlWarning = new JLabel(" Jouer ");
		jlWarning.setOpaque(true);
		jlWarning.setForeground(Color.gray);
		jlWarning.setHorizontalAlignment(SwingConstants.CENTER);
		jlWarning.setSize(50, 50);

		JLabel equipe0 = new JLabel( "équipe NS" );// Nord - Sud
		JLabel equipe1 = new JLabel( "équipe EW" );// Est - Ouest
		JLabel ptsEq0 = new JLabel( "0" );// plis ou points Equipe NS
		JLabel ptsEq1 = new JLabel( "0" );// plis ou points Equipe EW

		JLabel[] jlPli = new JLabel[4];
		for (int i = 0; i < 4; i++) {
			jlPli[i] = new JLabel("carte " + (i + 1) + " du pli");
			jlPli[i].setHorizontalAlignment(SwingConstants.CENTER);
		}

		// maquette
		JPanel jpWest = new JPanel(new GridLayout(2, 1));
		jpWest.setOpaque(true);
		
		jpWest.add(jlWarning);
		JPanel jpSWest = new JPanel(new GridLayout(4, 1));
		jpSWest.setBackground(Color.WHITE);
		jpSWest.add(equipe0);
		jpSWest.add(ptsEq0);
		jpSWest.add(equipe1);
		jpSWest.add(ptsEq1);
		jpWest.add(jpSWest);

		JPanel jpCentre = new JPanel(new GridLayout(4, 1));
		for (int i = 0; i < 4; i++)
			jpCentre.add(jlPli[i], 0, i);

		JPanel jpPrincipal = (JPanel) this.getContentPane();
		jpPrincipal.add(atout, BorderLayout.NORTH);
		jpPrincipal.add(main, BorderLayout.EAST);
		jpPrincipal.add(jlbInfo, BorderLayout.SOUTH);
		jpPrincipal.add(jpWest, BorderLayout.WEST);
		jpPrincipal.add(jpCentre, BorderLayout.CENTER);

		// fenêtre principale
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		int width = 446, height = 306;
		this.setLocation(calculerPosition(width, height));
		this.setSize(width, height);
		this.setVisible(true);

		// Écouteurs
		main.addListSelectionListener((e) -> jouerCarte());

		mettreAJour();
	}

	/**
	 * Mets à jour les données affichées par cette fenêtre en interrogeant le
	 * contrôleur. 
	 * Seul le joueur associé à cette vue est interrogé directement
	 * (sans passer par le contrôleur)
	 */	
	public void mettreAJour() {
		if(controleur.getDonneur() == joueur)
		{
			System.out.println("decoration donneur" + this);
			new VueDonneur(this);
			controleur.retirerObservateur(this);
			this.dispose();
		}

		BorderLayout bl = (BorderLayout) getContentPane().getLayout();

		JLabel info = (JLabel) bl.getLayoutComponent(BorderLayout.SOUTH);
		int[] points = controleur.getPartie().resultats();
		info.setText( String.format( "Equipe NS : %d pts - Equipe EW : %d pts", points[0], points[1] ) );

		JLabel warning = (JLabel) ((Container) bl.getLayoutComponent(BorderLayout.WEST)).getComponent(0);
		if (controleur.getJoueurCourant() == this.joueur) {
			warning.setBackground(Color.RED);
			warning.setForeground(Color.BLACK);
		} else {
			warning.setBackground(this.getContentPane().getBackground());
			warning.setForeground(Color.GRAY);
		}
		JPanel equipes = (JPanel) ((Container) bl.getLayoutComponent(BorderLayout.WEST)).getComponent(1);
		for( int i = 0; i < 2; i++ ) {
			int plisEq = controleur.getPartie().getEquipe(i).getPlis().size();
			( (JLabel) ( equipes ).getComponent(1+2*i) ).setText( plisEq + " plis" );
		}

		JList<Carte> main = (JList<Carte>) bl.getLayoutComponent(BorderLayout.EAST);
		main.setListData(joueur.getCartes().toArray(new Carte[13]));

		Component[] jlPli = ((Container) bl.getLayoutComponent(BorderLayout.CENTER)).getComponents();
		Pli pli = controleur.getPliCourant();
		if (pli != null) { // la 'donne' commence
			int i = 0, max = pli.getCartes().size();
			if (max == 0 && controleur.getCarteCourante() != null) // nouvelle levee qui n'est pas la toute premiere
				// on affiche la derniere carte jouee dans la levee precedente pour info des
				// joueurs
				((JLabel) jlPli[3]).setText(controleur.getCarteCourante().toString());
			else { // premiere levee ou levee deja commencee
				for (; i < max; i++)
					((JLabel) jlPli[i]).setText(pli.getCartes().get(i).toString());
				for (; i < 4; i++)
					((JLabel) jlPli[i]).setText("");
			}
		}

		JLabel resultat = (JLabel) bl.getLayoutComponent(BorderLayout.NORTH);
		if(controleur.isPartieFinie())
		{
			if (joueur.getEquipe().getPoints() >= 3) {
				resultat.setText("Vous avez gagné");
				resultat.setBackground(Color.GREEN);
			}
			else
			{
				resultat.setText("Vous avez perdu");
				resultat.setBackground(Color.RED);
			}
		}
	}

	/*
	 * Message envoyé au controleur lorsque l'utilisateur joue une carte de sa main.
	 * 
	 * Envoie un message au contrôleur pour lui donner la carte sélectionnée par le
	 * joueur puis efface la sélection et demande au contrôleur d'avancer d'un pas
	 * dans l'algorithme de jeu.
	 */	
	protected void jouerCarte() {
		BorderLayout bl = (BorderLayout) getContentPane().getLayout();
		JList<Carte> main = (JList<Carte>) bl.getLayoutComponent(BorderLayout.EAST);
		if (main.getSelectedValue() != null) {
			controleur.setCarteCourante(main.getSelectedValue());
			main.clearSelection();
			controleur.avancer();
		}
	}

	/*
	 * Calcule la position de la fenêtre sur l'écran en fonction de la largeur et de
	 * la hauteur souhaitées. 
	 * 
	 * Prend en compte la résolution et la dimension de l'écran.
	 * @return le coin supérieur gauche de la fenetre (en tant qu'onjet Point)
	 */	
	protected Point calculerPosition(int width, int height) {
		Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		int i;

		switch (i = controleur.getPartie().getJoueurs().indexOf(joueur)) {
		case 0:
			p.translate(-width / 2, -3 * height / 2);
			break;
		case 1:
			p.translate(width / 2, -height / 2);
			break;
		case 2:
			p.translate(-width / 2, height / 2);
			break;
		case 3:
			p.translate(-3 * width / 2, -height / 2);
			break;
		default:
			System.err.println(i + " C'est quoi c'bordel Carpentier ? ");
		}
		return p;
	}

	public WhistControleur getControleur() {
		return controleur;
	}

	public void setControleur(WhistControleur controleur) {
		this.controleur = controleur;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
}
