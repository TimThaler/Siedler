package spiel;

import java.util.Random;
import enums.Rohstoff;
import enums.Struktur;
import interfaces.Konstanten;

public class Spielbrett {
	public static Spielbrett instance = null;

	private Feld[] felder;
	
	public static Spielbrett getInstance(int anzFelder)
	{
		if(instance == null)
		{
			instance = new Spielbrett(anzFelder);
		}
		return instance;
	}
	
	private Spielbrett(int anzFelder)
	{
		Random r = new Random();
		
		felder = new Feld[anzFelder];
		for(int i = 0; i < felder.length; i++)
		{	
			Rohstoff rohstoff = Rohstoff.values()[(r.nextInt(5))];
			Feld feld = new Feld((r.nextInt(11)+1),rohstoff);
			felder[i] = feld;
		}
		
        DatabaseConnector.connectToSiedlerDatabase();
        
        DatabaseConnector.createTable(Struktur.FIELD);
        DatabaseConnector.createTable(Struktur.Knoten);
		DatabaseConnector.createTable(Struktur.CORNER);
		
	//	for(int i = 0; i < Konstanten.ANZ_FELDER_AUF_SPIELBRETT; i++)
	//	{
			int pk = DatabaseConnector.addField();
			for(int x =0; x<5;x++)
			{
				System.out.println(pk +  " pk");
				DatabaseConnector.addCorner(pk);
			}
		//}		
	}
	public void updateRohstoffeNachWurf(int augenZahl)
	{
		for (int i = 0; i < felder.length; i++)
		{
			if (felder[i].getFeldWuerfelNummer() == augenZahl)
			{
				if(felder[i].istFeldBebaut());
				{
					felder[i].updateRohstoffePerBauwerk();
				}
			}
		}

	}
}
