package spiel;

import java.util.Random;
import java.util.Vector;

import enums.Rohstoff;
import enums.Struktur;

public class Spielbrett {
	public static Spielbrett instance = null;
	public DatabaseConnector dbc = null;
	private Vector<Feld> felder = null;
	
	public static Spielbrett getInstance(int anzFelder, DatabaseConnector dbc)
	{
		if(instance == null){
			instance = new Spielbrett(anzFelder, dbc);
		}
		return instance;
	}
	
	private Spielbrett(int anzFelder, DatabaseConnector dbc)
	{
		this.dbc = dbc;					
		this.felder = new Vector<Feld>();
		Random r = new Random();

		for(int i = 0; i < anzFelder; i++){	
			Rohstoff rohstoff = Rohstoff.values()[(r.nextInt(5))];
			Feld feld = new Feld((r.nextInt(11)+1),rohstoff);
			felder.addElement(feld);
		}
		System.out.println("[***] dbc constructor");
        if(dbc.tableExists(Struktur.FIELD)){
        	dbc.clearTable(Struktur.FIELD);
        }else{
        	dbc.createTableField();
        }
        
        for(Feld f : felder){
        	int primaryKey = dbc.addField(f);
        	for(int x =0; x<5; x++){
        		dbc.addCorner(primaryKey);
        	}
		}			
	}
	
	public void updateRohstoffeNachWurf(int augenZahl)
	{
		for(Feld feld: felder){
			if (feld.getFeldWuerfelNummer() == augenZahl){
				if(feld.istFeldBebaut()){
					feld.updateRohstoffePerBauwerk();
				}
			}
		}
	}
}
