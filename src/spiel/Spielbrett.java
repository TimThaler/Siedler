package spiel;

import java.util.Random;
import enums.Rohstoff;
import enums.Struktur;
import interfaces.Konstanten;

public class Spielbrett {
	public static Spielbrett instance = null;
	public DatabaseConnector dbc = null;
	private Feld[] felder;
	
	public static Spielbrett getInstance(int anzFelder, DatabaseConnector dbc)
	{
		if(instance == null)
		{
			instance = new Spielbrett(anzFelder, dbc);
		}
		return instance;
	}
	
	private Spielbrett(int anzFelder, DatabaseConnector dbc)
	{
		Random r = new Random();
		this.dbc = dbc;
		
		felder = new Feld[anzFelder];
		for(int i = 0; i < felder.length; i++)
		{	
			Rohstoff rohstoff = Rohstoff.values()[(r.nextInt(5))];
			Feld feld = new Feld((r.nextInt(11)+1),rohstoff);
			felder[i] = feld;
		}
		
        if(dbc.tableExists(Struktur.FIELD)){
        	dbc.clearTable(Struktur.FIELD);
        }else {
        	dbc.createTable(Struktur.FIELD);
        }
        dbc.tableExists(Struktur.CORNER);
        //DatabaseConnector.createTable(Struktur.FIELD);
        //DatabaseConnector.createTable(Struktur.Knoten);
		//DatabaseConnector.createTable(Struktur.CORNER);
		
	
			//int pk = DatabaseConnector.addField();
			for(int x = 0; x <5; x++)
			{
				dbc.addCorner(1);
			}
				
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
