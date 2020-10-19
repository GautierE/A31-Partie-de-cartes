package whist.whistvue;

public class VueDecorator extends VueJoueur
{
    private VueJoueur vueJ;

    public VueDecorator(VueJoueur vueJ)
    {
        super(vueJ.getControleur(), vueJ.getJoueur());
        this.vueJ = vueJ;
    }
}
