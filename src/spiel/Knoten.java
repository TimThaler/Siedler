package spiel;

import interfaces.Bauwerk;

public class Knoten 
implements interfaces.Konten{
	public boolean bebaut = false;
	//Feld[] angrendeneFelder = new Feld[3];
	Bauwerk bauwerk = null;
	
	public Knoten(Feld feld){
		this.bebaut = false;		
	}
	
	public boolean bebauenMoeglich(){
		//und ueberall zwei knoten weiter noch platz ist ...
		if(!bebaut){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Bauwerk getBauwerk() {
		// TODO Auto-generated method stub
		return this.bauwerk;
	}
}
