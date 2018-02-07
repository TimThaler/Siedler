package interfaces;

public interface Konstanten {
	public static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/siedler";
	public static final String POSTGRES_USER = "postgres";
	public static final String POSTGRES_PASSWORD = "123";
	
	public static final int ANZ_ECKEN_PRO_FELD = 6;
	public static final int MAX_POOL_SIZE = 5;
	
	public static final int MAX_KARTEN_BEI_RAEUBER = 7;
	public static final int GESAMT_PUNKTE_ZUM_GEWINNEN = 12;
	public static final int ANZ_FELDER_AUF_SPIELBRETT = 1;
		
	public static final String FIELD_TABLE_SETUP = 
			"Create table field (ID SERIAL UNIQUE," +
			" resource TEXT NOT NULL," +
			" dice_value TEXT NOT NULL"
			+ ");" ;	
	
	public static final String CORNER_TABLE_SETUP =
			"Create table corner (ID SERIAL UNIQUE,"
			+ "field_id integer REFERENCES field(id)"
			+ ");";
	
	public static final String EDGE_TABLE_SETUP = 
			"Create table edge (ID SERIAL UNIQUE, "
			+ "field_id integer REFERENCES field(id), "
			+ "corner_1_id integer REFERENCES corner(id) NOT NULL,"
			+ "corner_2_id integer REFERENCES corner(id) NOT NULL "
			+ ");";	
	
	public static final String NODE_TABLE_SETUP = "";
	
	public static final String POSTGRES_CREATE_TABLE_PREFIX =
			" (ID SERIAL PRIMARY KEY,";	
}
