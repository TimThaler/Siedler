package interfaces;

public interface Konstanten {
	public static final int MAX_KARTEN_BEI_RAEUBER = 7;
	public static final int GESAMT_PUNKTE_ZUM_GEWINNEN = 12;
	public static final int ANZ_FELDER_AUF_SPIELBRETT = 9;
	public static final String FELDTABLESETUP = 
			" Name TEXT NOT NULL," +
			" Rohstoff TEXT NOT NULL" 
					;
	
	public static final String KNOTENTABLESETUP = 
			" Name TEXT NOT NULL," +
			" Rohstoff TEXT NOT NULL";
	public static final String ECKENTABLESETUP = 
			" Name TEXT NOT NULL," +
			" Rohstoff TEXT NOT NULL";

	
	
			
	
}
