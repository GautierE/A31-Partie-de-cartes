package whist.whistmodel;

public class DecompteWhist implements MethodeDeDecompteDesPoints {

	@Override
	public int getPoints(Pli[] plis, Couleur atout) {
		
		return Math.max( plis.length - 6, 0 );
		
	}

}
