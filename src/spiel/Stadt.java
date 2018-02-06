package spiel;

public class Stadt 
implements interfaces.Bauwerk{
	private Spieler besitzer = null;
	private Knoten knoten = null;
	
	public Stadt(Spieler besitzer, Knoten knoten){
		this.besitzer = besitzer;
		this.knoten = knoten;
	}

	@Override
	public interfaces.Spieler getBesitzer() {
		return this.besitzer;
	}
	

}
