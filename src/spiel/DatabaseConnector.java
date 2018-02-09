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

public class DatabaseConnector{
	Connection c = null;
	Statement stmt = null;
	DatabaseMetaData dmd = null;
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
	
	public int getFreeCornerID(Feld f) {
		//String sql = "select "
		
		return 0;
	}
	
	public void clearTable(Struktur struktur) {
		String sql = "DELETE  FROM " + struktur.toString().toLowerCase();
        
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
		try {
			dmd = c.getMetaData();
			String tableName = struktur.toString().toLowerCase();
			ResultSet rs = dmd.getTables(null, null,"%", new String[] {"TABLE"});
			while (rs.next()){			
				if(rs.getString(3).equals(tableName)){
					System.out.println("[***] Table " + struktur + " exists");
					return true;
				}
			}
			System.out.println("[***] Table " + struktur + " does not exists");
			return false;
		}catch(SQLException e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
			}
		return false;
	}

	public void createTable(Struktur struktur) {	
		String sql = null;
		switch (struktur) {
		case  FIELD:		
			sql = Konstanten.FIELD_TABLE_SETUP;		
			break;
		case  CORNER:
			sql = Konstanten.CORNER_TABLE_SETUP;
			break;
		case EDGE:
			sql = Konstanten.EDGE_TABLE_SETUP;
			break;
		case Knoten:
			break;
		default:
			break;
		}	
		try {	
			System.out.print(sql);
			dmd = c.getMetaData();			 			 		
			stmt = c.createStatement(); 
        	stmt.executeUpdate(sql); 
		}catch(SQLException ex){
			ex.printStackTrace();
			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
			System.exit(0);
		}
	}

	public int addField(Feld feld) {
		int primaryKey = -1;
		String sql = "INSERT INTO " + Struktur.FIELD + "( resource,dice_value) VALUES(?, ?)";

		try {
			//System.out.println(sql);
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

	public int addCorner(int pkField) {
		int primaryKey = -1;
		String sql = "INSERT INTO " + Struktur.CORNER + " (field_id) VALUES(?);";
		
		try {
			PreparedStatement pstmt = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);			
			pstmt.setInt(1, pkField);
			pstmt.executeUpdate();
 			
 			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				primaryKey = rs.getInt(1);				 
			}
		}catch(Exception ex){
			ex.printStackTrace();
		    System.err.println(ex.getClass().getName() + " " + ex.getMessage());
            System.exit(0);
		}			
		return primaryKey;
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

	public int addEdge(Edge edge) {
		int primaryKey = -1;
		String sql = "INSERT INTO " + Struktur.EDGE + 
				" (field_id, corner_1_id, corner_2_id)" +
				" VALUES('" + 
				edge.getFieldId() + "', '" + 
				edge.getEdge1Id() + "', '" +
				edge.getEdge2Id() + "');";
		
		try {
			c.setAutoCommit(false);	
			stmt = c.createStatement();
			System.out.println(sql);
 			stmt.executeUpdate(sql);
 			c.commit();	
 			
 			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				primaryKey = rs.getInt(1);				 
			}
		}catch(Exception ex){
			ex.printStackTrace();
		    System.err.println(ex.getClass().getName() + " " + ex.getMessage());
            System.exit(0);
		}			
		return primaryKey;
		
	}
}
