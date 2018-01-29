package spiel;

import interfaces.Bauwerk;

public class Siedlung 
implements interfaces.Bauwerk{
	private Spieler besitzer = null;
	private Knoten knoten = null;
	
	public Siedlung(Spieler besitzer, Knoten knoten)
	{
		this.besitzer = besitzer;
		this.knoten = knoten;
	}

	@Override
	public Spieler getBesitzer() {
		// TODO Auto-generated method stub
		return this.besitzer;
	}

}
