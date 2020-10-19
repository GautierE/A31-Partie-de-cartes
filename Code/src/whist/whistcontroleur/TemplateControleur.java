package whist.whistcontroleur;

import observateursujet.Sujet;
import whist.whistmodel.*;

public abstract class TemplateControleur extends Sujet
{
    private Partie partie;

    private Joueur joueurCourant;

    private Joueur donneur;

    private Carte carteCourante;

    private boolean partieFinie;

    private Pli pliCourant;

    // Méthodes d'instances

    /**
     * Fait avancer le déroulement de la partie de carte après chaque action d'un joueur
     */
    public abstract void avancer();
    /*
    La methode avancer() doit être redéfinie pour le jeu de Belote.
    Il est possible de partir sur la base de la méthode avancer() du whist tout en modifiant certaines lignes.
    Le nombre de points à atteindre, le roulement du donneur, le sens de distribution des cartes,
     */

    /**
     * Distribue les cartes aux joueurs et fixe l'atout.
     *
     * Les cartes sont distribuées de façon aléatoire.
     */
    public abstract void distribuerCartes();

    /**
     * Renvoie l'instance de Partie associée à ce controleur.
     *
     * @return la partie controlée par ce controleur
     */
    public Partie getPartie() {
        return partie;
    }

    /**
     * Renvoie le joueur actif dans la donne en cours.
     *
     * @return le joueurCourant
     */
    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    /**
     * Renvoie le pli en cours de construction dans le tour de table.
     *
     * @return le pliCourant
     */
    public Pli getPliCourant() {
        return pliCourant;
    }

    /**
     * Renvoie la carte jouée par le joueur courant.
     *
     * @return la dernière carte sélectionné par une des vues.
     */
    public Carte getCarteCourante() {
        return carteCourante;
    }

    // Modifieur

    /**
     * Utilisé par les vues pour désigner la carte choisie par un des joueurs.
     *
     * @param carteJouee la nouvelle valeur de l'attribur carteCourante
     */
    public void setCarteCourante(Carte carteJouee) {
        this.carteCourante = carteJouee;
    }

    public Joueur getDonneur()
    {
        return donneur;
    }

    public boolean isPartieFinie() {
        return partieFinie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public void setJoueurCourant(Joueur joueurCourant) {
        this.joueurCourant = joueurCourant;
    }

    public void setDonneur(Joueur donneur) {
        this.donneur = donneur;
    }

    public void setPartieFinie(boolean partieFinie) {
        this.partieFinie = partieFinie;
    }

    public void setPliCourant(Pli pliCourant) {
        this.pliCourant = pliCourant;
    }
}
