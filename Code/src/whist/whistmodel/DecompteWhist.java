package whist.whistmodel;

public class DecompteWhist implements MethodeDeDecompteDesPoints {

	@Override
	public int getPoints(Pli[] plis, Couleur atout) {
		//TODO 		return Math.max( plis.length - 6, 0 );
		return Math.max( plis.length - 0, 0 );
		
	}

}
