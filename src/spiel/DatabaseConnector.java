package spiel;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import enums.Struktur;
import interfaces.Konstanten;

public class DatabaseConnector {
	
	public static void connectToSiedlerDatabase()
	{
		try {
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
		
		Connection c = null;
		Statement stmt = null;
		DatabaseMetaData dbmd = null;
		ResultSet rs = null;

		try {	
			c = DriverManager
					.getConnection(Konstanten.POSTGRES_URL,
							Konstanten.POSTGRES_USER,
							Konstanten.POSTGRES_PASSWORD
							);
			
			dbmd = c.getMetaData();
		
			switch (tableName){
				case FIELD:
					tableNameString = "field";
					setupParams = Konstanten.FIELD_TABLE_SETUP;
					break;
				case Knoten:
					tableNameString = "knoten";
					setupParams = Konstanten.KNOTENTABLESETUP;
					break;
				case CORNER:
					tableNameString = "ecke";
					setupParams = Konstanten.ECKENTABLESETUP;
					break;
			}
			
			rs =  dbmd.getTables(null, null, tableNameString.toLowerCase(), new String[] {"TABLE"});
			stmt = c.createStatement(); 
			String sql = null;
			
			if (rs.next()) {	
				System.out.println("[***] Table " + tableNameString + " exists");
						
                /*System.out.println(rs.getString("TABLE_NAME"));*/
                sql = "DELETE  FROM " + tableNameString;
                stmt.executeUpdate(sql); 
                System.out.println(sql);
                System.out.println("[***] Table "+ tableNameString + " cleared");
			}else{
                System.out.println("[***] Table " + tableNameString + " does not exist"); 
                if (tableName == Struktur.CORNER)
                {
                	sql = "Create table ecke (ID SERIAL UNIQUE, field_id integer REFERENCES field(id));" ;
                }else if (tableName ==Struktur.FIELD) {
                	sql = "Create table field (ID SERIAL UNIQUE," + setupParams + ");";       
                }
			}
			System.out.println("[***] Table " + tableNameString + " created"); 
			System.out.println(sql);
			stmt.executeUpdate(sql); 	
			
		}catch(Exception ex){
			ex.printStackTrace();
			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
			System.exit(0);
		}finally {
			try {								
				if(stmt != null) stmt.close();
				if(c != null) c.close();
				if(rs != null) rs.close();
			}catch(Exception e) {}
		}
	}

	public static int addField() {
		Connection c = null;
		Statement stmt = null;
		int primaryKey = -1;
		String query = "INSERT INTO " + Struktur.FIELD + "( NAME,ROHSTOFF) VALUES(?, ?)";

		try {			   
			c = DriverManager.getConnection(Konstanten.POSTGRES_URL,
							Konstanten.POSTGRES_USER,
							Konstanten.POSTGRES_PASSWORD
							);
	   
			c.setAutoCommit(false);	
			PreparedStatement pstmt = c.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, "bar");
			pstmt.setString(2, "baz");
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				primaryKey = rs.getInt(1);
			  System.out.println(primaryKey + " new id");
			}
		   
		   
	   }catch(Exception ex){
		    System.err.println(ex.getClass().getName() + " " + ex.getMessage());
            System.exit(0);
	   }finally {
		  try {
			  if(stmt != null) stmt.close();
			  if(c != null) c.close();
		  }catch(Exception e) {}
	   }
		return primaryKey -1;
	}

	public static int addCorner(int pkField) {
		Connection c = null;
		Statement stmt = null;
		try {			   
			c = DriverManager
					.getConnection(Konstanten.POSTGRES_URL,
							Konstanten.POSTGRES_USER,
							Konstanten.POSTGRES_PASSWORD
							);
	   
			c.setAutoCommit(false);	   
			stmt = c.createStatement();	   
			String sql = "INSERT INTO ecke (field_id)" +  "VALUES('"+ pkField +"');";			
  		    stmt.executeUpdate(sql);	
		    c.commit();
		   
	   }catch(Exception ex){
		    System.err.println(ex.getClass().getName() + " " + ex.getMessage());
            System.exit(0);
	   }finally {
		  try {
			  if(stmt != null) stmt.close();
			  if(c != null) c.close();
		  }catch(Exception e) {}
	   }
		return 0;		
	}
}
