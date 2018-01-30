package interfaces;

public interface Konstanten {
	public static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/siedler";
	public static final String POSTGRES_USER = "postgres";
	public static final String POSTGRES_PASSWORD = "123";
	
	
	public static final int MAX_KARTEN_BEI_RAEUBER = 7;
	public static final int GESAMT_PUNKTE_ZUM_GEWINNEN = 12;
	public static final int ANZ_FELDER_AUF_SPIELBRETT = 9;
	public static final String FIELD_TABLE_SETUP = 
			" Name TEXT NOT NULL," +
			" Rohstoff TEXT NOT NULL" 
					;
	
	public static final String KNOTENTABLESETUP = 
			" Name TEXT NOT NULL," +
			" Rohstoff TEXT NOT NULL";
	
	public static final String ECKENTABLESETUP = 
			" feld_id integer REFERENCES field(id)";
					
	
	public static final String POSTGRES_CREATE_TABLE_PREFIX =
			" (ID SERIAL PRIMARY KEY,";
	
	public static final String POSTGRES_CREATE_TABLE_SUFFFIX =
			");";

	
	
			
	
}
