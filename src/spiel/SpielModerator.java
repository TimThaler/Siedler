package spiel;

public class SpielModerator
implements interfaces.Konstanten{
	private static SpielModerator instance = null;
	
	private SpielModerator()
	{
	}
	
	public static SpielModerator getInstance()
	{
		if (instance == null)
		{
			instance = new SpielModerator();
		}
		return instance;
	}
	
	public Spieler aktiverSpieler()
	{
		return null;
	}

	public void naechsterSpieleramZug()
	{
		
	}
	
	/**
	 * jeder spieler setzt zwei siedlungen
	 * jeder spieler bekommt  rohstoffkarten fure die zweite siedlung
	 * ein aktiver spieler wird ausgewaehlt
	 * 
	 * @return
	 */
	public boolean spielInitialisieren()
	{
		return false;
	}
	
	public boolean gesammtpunkteBerechnen(Spieler s)
	{
		boolean gewinnerVorhanden = false;
			if(s.anzGesamtpunkte()>= GESAMT_PUNKTE_ZUM_GEWINNEN )
			{
				gewinnerVorhanden = true;
			}	
		
		return gewinnerVorhanden;
	}
}

