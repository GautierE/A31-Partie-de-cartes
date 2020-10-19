package whist.whistvue;

public abstract class VueDecorator extends VueJoueur
{
    private VueJoueur vueJ;

    public VueDecorator(VueJoueur vueJ)
    {
        super(vueJ.getControleur(), vueJ.getJoueur());
        this.vueJ = vueJ;
    }

    public VueJoueur getVueJ() {
        return vueJ;
    }
}
