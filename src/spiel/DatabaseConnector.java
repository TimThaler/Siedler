package spiel;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import enums.Struktur;
import interfaces.Konstanten;

public class DatabaseConnector {
	
	public static void addFeld()
	{
		try {			   
			Connection c = DriverManager
					.getConnection("jdbc:postgresql://localhost:5432/sandbox",
							"postgres", "123");
	   
			c.setAutoCommit(false);	   
			Statement stmt = c.createStatement();	   
			String sql = "INSERT INTO FELD(NAME,ROHSTOFF)" + 
	   
			   "VALUES('Anfang','Erz');";
		   System.out.println(sql);
		   stmt.executeUpdate(sql);	
		   
		   stmt.close();
		   c.commit();
		   c.close();
	   }catch(Exception ex){
		   System.err.println(ex.getClass().getName() + " " + ex.getMessage());
		   System.exit(0);
	   }
	}
	public static void connectToSiedlerDatabase()
	{
		try {
			   Class.forName("org.postgresql.Driver");
			   Connection c = DriverManager
			      .getConnection("jdbc:postgresql://localhost:5432/siedler",
			      "postgres", "123");
			   System.out.println("[***] Opened database successfully");
			  
			   c.close();			   
			} catch (Exception e) {
			   /*e.printStackTrace();*/
			   System.err.println(e.getClass().getName()+": "+e.getMessage());
			   System.exit(0);
			}
	}

	public static void createTable(Struktur tableName) {
		String tableNameString = null;
		String setupParams = null;
		String[] types = {"TABLE"};
		
		Connection c = null;
		Statement stmt = null;
		DatabaseMetaData dbmd = null;
		ResultSet rs = null;

		try {			
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/siedler",
					"postgres", "123");
			
			dbmd = c.getMetaData();
		
			switch (tableName){
				case Feld:
					tableNameString = "feld";
					setupParams = Konstanten.FELDTABLESETUP;
					break;
				case Knoten:
					tableNameString = "knoten";
					setupParams = Konstanten.KNOTENTABLESETUP;
					break;
				case Ecke:
					tableNameString = "ecke";
					setupParams = Konstanten.ECKENTABLESETUP;
					break;
			}
			
			rs =  dbmd.getTables(null, null, tableNameString.toLowerCase()  , types);
			stmt = c.createStatement(); 
			String sql = null;
			
			if (rs.next()) {	
				System.out.println("[***] Table " + tableNameString + " exists");
						
                /*System.out.println(rs.getString("TABLE_NAME"));*/
                sql = "DELETE  FROM " + tableNameString;
                stmt.executeUpdate(sql); 
                System.out.println("[***] Table "+ tableNameString + " cleared");
			}else{
                System.out.println("[***] Table " + tableNameString + " does not exist");                     	
        		sql = "Create table "+ tableNameString  + 
        		" (ID SERIAL," +
        		setupParams +
        		");";                     	    
            }
			System.out.println(sql);
			stmt.executeUpdate(sql); 
      	    stmt.close();
			c.close();			
		}catch(Exception ex){
			ex.printStackTrace();
			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
			System.exit(0);
		}		
	}
}
