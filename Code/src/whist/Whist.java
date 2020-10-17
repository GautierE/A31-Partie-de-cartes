package whist;

import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Window;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import whist.whistcontroleur.WhistControleur;
import whist.whistmodel.Joueur;
import whist.whistmodel.Partie;
import whist.whistvue.VueDonneur;
import whist.whistvue.VueJoueur;

public class Whist {

	static class MyIcon extends Window {
		public MyIcon() {
			super(null);
			Icon marius = new ImageIcon( getClass().getClassLoader().getResource("Marius.jpg") ); // dans le dossier bin/
			add(new JLabel(marius));
			pack();
			Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
			p.translate(-marius.getIconWidth() / 2, -marius.getIconHeight() / 2);
			setLocation(p);
			setVisible(true);
		}
	};

	public static void main(String[] args) {

		new MyIcon();

		// Création de la partie de Whist (donneur en dernier)
		Partie p = new Partie("Panisse", "Escartefigue", "M. Brun", "César");

		// Création du contrôleur
		WhistControleur ctrl = new WhistControleur( p );

		// Création des quatre interfaces graphiques
		/*for( Joueur j: p.getJoueurs() )
			new VueJoueur( ctrl, j );*/

		ArrayList<VueJoueur> avj = new ArrayList<>();
		for(int i = 0;i<p.getJoueurs().size();i++) {
			avj.add(new VueJoueur(ctrl, p.getJoueur(i)));
			if(i == p.getJoueurs().size() - 1){
				new VueDonneur(avj.get(i));
			}
		}
		
	}

}
