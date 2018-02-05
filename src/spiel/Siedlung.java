package spiel;

import interfaces.Bauwerk;

public class Siedlung 
implements interfaces.Bauwerk{
	private Spieler besitzer = null;
	private Knoten knoten = null;
	
	public Siedlung(Spieler besitzer, Knoten knoten){
		this.besitzer = besitzer;
		this.knoten = knoten;
	}

	public Spieler getBesitzer() {
		return this.besitzer;
	}

}
