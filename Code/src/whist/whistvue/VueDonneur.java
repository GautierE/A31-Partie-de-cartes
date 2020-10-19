package whist.whistvue;

import whist.whistcontroleur.WhistControleur;
import whist.whistmodel.Carte;
import whist.whistmodel.Joueur;
import whist.whistmodel.Pli;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class VueDonneur extends VueJoueur
{
    private VueJoueur vueJ;

    public VueDonneur(VueJoueur vueJ)
    {
        super(vueJ.getControleur(), vueJ.getJoueur());
        this.vueJ = vueJ;

        JButton jbDistribuer = new JButton("Distribuer");
        jbDistribuer.setForeground(Color.RED);

        JButton jbGo = new JButton("Commencer");
        jbGo.setForeground(Color.LIGHT_GRAY);

        JPanel jpNWest = new JPanel(new GridLayout(2,1) );
        jpNWest.add(jbDistribuer);
        jpNWest.add(jbGo);

        BorderLayout bl = (BorderLayout) super.getContentPane().getLayout();
        JPanel jpSWest = (JPanel) bl.getLayoutComponent(BorderLayout.WEST);

        JPanel west = new JPanel( new GridLayout( 2, 1 ) );
        west.add(jpNWest);
        west.add( jpSWest );

        this.getContentPane().add( west, BorderLayout.WEST );

        // écouteurs
        jbDistribuer.addActionListener( e -> distribuerCartes() );
        jbGo.addActionListener( e -> commencerPartie() );
    }

    // Méthode appelée par le donneur pour distribuer les cartes aux joueurs
    public void distribuerCartes( ) {
        getControleur().distribuerCartes();
        BorderLayout bl = (BorderLayout) this.getContentPane().getLayout();
        Component nordWest = ( (Container) bl.getLayoutComponent(BorderLayout.WEST) ).getComponent( 0 ) ;
        JButton distribuer = (JButton) ( (Container) nordWest ).getComponent( 0 );
        JButton commencer = (JButton) ( (Container) nordWest ).getComponent( 1 );

        distribuer.setForeground(Color.LIGHT_GRAY);
        commencer.setForeground(Color.RED);
    }

    // Méthode appelée par le donneur après avoir distribué les cartes pour lancer la partie
    public void commencerPartie() {

        vueJ.getControleur().avancer();

        BorderLayout bl = (BorderLayout) this.getContentPane().getLayout();
        JButton go = (JButton) ( (Container) ( (Container) bl.getLayoutComponent(BorderLayout.WEST) ).getComponent( 0 ) ).getComponent( 1 );
        go.setForeground( Color.LIGHT_GRAY );
    }

    // Calcule la position de la fenêtre sur l'écran en fonction de la largeur et de la hauteur souhaitées.
    // Prend en compte la résolution et la dimension de l'écran.
    @Override
    protected Point calculerPosition(int width, int height) {
        Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        p.translate( -width/2, -3*height/2 );
        return p;
    }


    @Override
    public void mettreAJour( ) {
        if(controleur.getDonneur() != joueur)
        {
            vueJ.setVisible(true);
            controleur.ajouterObservateur(vueJ);
            this.dispose();
        }

        BorderLayout bl = (BorderLayout) getContentPane().getLayout();

        JLabel warning = (JLabel) ( (Container) ( (Container) bl.getLayoutComponent(BorderLayout.WEST) ).getComponent( 1 ) ).getComponent( 0 );
        if (controleur.getJoueurCourant() == this.joueur) {
            warning.setBackground(Color.RED);
            warning.setForeground(Color.BLACK);
        } else {
            warning.setBackground(this.getContentPane().getBackground());
            warning.setForeground(Color.GRAY);
        }

        JList main = (JList) bl.getLayoutComponent(BorderLayout.EAST);
        main.setListData( joueur.getCartes().toArray( new Carte[13] ) );

        Component[] jlPli = ((Container) bl.getLayoutComponent(BorderLayout.CENTER)).getComponents();
        Pli pli = controleur.getPliCourant();
        if (pli != null) {
            int i = 0, max = pli.getCartes().size();
            for (; i < max; i++)
                ((JLabel) jlPli[i]).setText(pli.getCartes().get(i).toString());
            for (; i < 4; i++)
                ((JLabel) jlPli[i]).setText("");
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
}
