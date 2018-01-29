package spiel;

import enums.Rohstoff;
import interfaces.Bauwerk;

public class Feld 
implements interfaces.Feld{
	/*private static final Object Siedlung = null;
	private static final Object Stadt = null;*/
	private enums.Rohstoff rohstoff = null;
	private int wuerfelzahl = 0;
	private Knoten[] knoten;
	private boolean bebaut = false;
	private boolean vomRaeuberBesetzt = false;
	
	/**
	 * Jedes Feld hat 6 Ecken und 6 Knoten
	 * Es sucht nach Knoten die existieren, welche weniger als 3 angrenzende Felder haben
	 * Falls das Feld keine findet baut es neue Knoten.
	 */
	public Feld(int wurfelzahl, Rohstoff rohstoff)
	{
		this.rohstoff = rohstoff;
		this.wuerfelzahl = wurfelzahl;
		
		knoten = new Knoten[6];
		
		for( int i = 0; i < 6; i++)
		{
			knoten[0] = new Knoten(this);
		}
	}
	
	public void updateRohstoffePerBauwerk()
	{
		if(!vomRaeuberBesetzt)
		{
			for(int i = 0; i < knoten.length; i++)
			{
				if(knoten[i].bebaut)
				{
					Bauwerk bauwerk = knoten[i].getBauwerk();
					bauwerk.getBesitzer().updateRohstoff(this.getRohstoff(),bauwerk);
				}
			}
		}
	}
	
	public boolean istVomRaeuberBesetzt() {
		return vomRaeuberBesetzt;
	}

	public void setVomRaeuberBesetzt(boolean vomRaeuberBesetzt) {
		this.vomRaeuberBesetzt = vomRaeuberBesetzt;
	}

	@Override
	public int getFeldWuerfelNummer() {
		return this.wuerfelzahl;
	}

	@Override
	public Rohstoff getRohstoff() {
		return this.rohstoff;
		
	}
	
	public boolean istFeldBebaut()
	{
		return this.bebaut;
	}
}
