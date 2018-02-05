package spiel;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enums.Rohstoff;
import enums.Struktur;
import interfaces.Konstanten;

public class DatabaseConnector {
	Connection c = null;
	Statement stmt = null;
	DatabaseMetaData dmd = null;
	String sql = null;
	ResultSet rs = null;
	
	public DatabaseConnector(){		
		try{
			this.c = DriverManager
					.getConnection(Konstanten.POSTGRES_URL,
							Konstanten.POSTGRES_USER,
							Konstanten.POSTGRES_PASSWORD);
			System.out.println("[***] Opened database " +  Konstanten.POSTGRES_URL + " successful");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	public void clearTable(Struktur struktur) {
		sql = "DELETE  FROM " + struktur.toString().toLowerCase();
        
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("[***] Table "+ struktur.toString() + " cleared");
		}catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(ex.getClass().getName()+": "+ex.getMessage());
			ex.printStackTrace();
			}
	}
	
	public boolean tableExists(Struktur struktur){
		System.out.println("[***] Table exists");
		try {
			dmd = c.getMetaData();
			String tableName = struktur.toString().toLowerCase();
			ResultSet rs = dmd.getTables(null, null,"%", new String[] {"TABLE"});
			while (rs.next()){			
				if(rs.getString(3).equals(tableName)){
					return true;
				}

			}
			return false;
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
			}
		return false;
	}

	public void createTableField() {
		String tableName = "feld";
		
		try {	
			dmd = c.getMetaData();
			sql = "Create table field (ID SERIAL UNIQUE," + 
					Konstanten.FIELD_TABLE_SETUP
					+ ");";  			 			 		
			stmt = c.createStatement(); 
        	stmt.executeUpdate(sql); 
			System.out.println("[***] Table " + tableName + " created"); 	
		}catch(SQLException ex){
			ex.printStackTrace();
			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
			System.exit(0);
		}
	}
	
	public void createTableCorner() {
		String tableName =  "corner";
		
		try {	
			dmd = c.getMetaData();
			sql = "Create table ecke "
					+ "(ID SERIAL UNIQUE, "
					+ "field_id integer REFERENCES field(id));" ;
			stmt = c.createStatement(); 
        	stmt.executeUpdate(sql); 
			System.out.println("[***] Table " + tableName + " created"); 	
		}catch(SQLException ex){
			ex.printStackTrace();
			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
			System.exit(0);
		}
	}

	public int addField(Feld feld) {
		int primaryKey = -1;
		sql = "INSERT INTO " + Struktur.FIELD + "( NAME,ROHSTOFF) VALUES(?, ?)";

		try {			   
			PreparedStatement pstmt = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);			
			
			pstmt.setString(1, feld.getRohstoff().toString());
			pstmt.setInt(2, feld.getFeldWuerfelNummer());
			
			pstmt.executeUpdate();
			 
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				primaryKey = rs.getInt(1);
			  System.out.println(primaryKey + " new id");
			}
		   
		   
	   }catch(Exception ex){
		    System.err.println(ex.getClass().getName() + " " + ex.getMessage());
            System.exit(0);
	   }
		
		return primaryKey;
	}

	public void addCorner(int pkField) {
		try {	
			c.setAutoCommit(false);	   
			stmt = c.createStatement();	   
			String sql = "INSERT INTO ecke (field_id)" +  "VALUES('"+ pkField +"');";			
  		    stmt.executeUpdate(sql);	
		    c.commit();		 
		}catch(Exception ex){
		    System.err.println(ex.getClass().getName() + " " + ex.getMessage());
            System.exit(0);
		}			
	}
	
	public void close()
	{
		try {								
			if(stmt != null) stmt.close();
			if(c != null) c.close();
			if(rs != null) rs.close();
			System.out.println("[***] Database Connector closed");
		}catch(Exception e) {}
	}
}
