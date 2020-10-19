package observateursujet;

import java.util.ArrayList;
import java.util.List;

public abstract class Sujet {
	
	List<Observateur> mesObservateurs = new ArrayList<>( ); 
	
	protected void notifierObservateurs() {

		for( int i = 0; i < mesObservateurs.size(); i++ )
			mesObservateurs.get(i).mettreAJour();
		
	}
	
	
	public void ajouterObservateur( Observateur o ) {
		
		mesObservateurs.add( o );
		
	}
	
	
	public void retirerObservateur( Observateur o ) {
		
		mesObservateurs.remove( o );
		
	}

}
