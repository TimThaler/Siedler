package spiel;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enums.Struktur;
import interfaces.Konstanten;

public class DatabaseConnector
implements interfaces.DatabaseConnector{
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
	
	@Override
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
	
	@Override
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

	@Override
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
		case NODE:
			sql = Konstanten.NODE_TABLE_SETUP;
			break;
		default:
			break;
		}	
		try {	
			dmd = c.getMetaData();			 			 		
			stmt = c.createStatement(); 
        	stmt.executeUpdate(sql); 
		}catch(SQLException ex){
			ex.printStackTrace();
			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
			System.exit(0);
		}
	}

	@Override
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
			}		   		   
	   }catch(Exception ex){
		    System.err.println(ex.getClass().getName() + " " + ex.getMessage());
            System.exit(0);
	   }
		return primaryKey;
	}

	@Override
	public int addCorner(Corner corner) {
		int primaryKey = -1;
		String sql = "INSERT INTO " + Struktur.CORNER + " (field_id) VALUES(?);";
		
		try {
			PreparedStatement pstmt = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);			
			pstmt.setInt(1, corner.getPkLinkedField());
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
	
	@Override
	public int addNode(Knoten node) {
		int primaryKey = -1;
		String sql = "INSERT INTO " + Struktur.NODE + 
				" (corner_1_id, corner_2_id,corner_3_id,bauwerk)" +
				" VALUES(?,?,?,?)";	
		try {
			PreparedStatement pstmt = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);			
			pstmt.setNull(1, java.sql.Types.INTEGER);
			pstmt.setNull(2,java.sql.Types.INTEGER);
			pstmt.setNull(3, java.sql.Types.INTEGER);
			pstmt.setString(4,"");
			
			pstmt.executeUpdate();

 			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()){
				primaryKey = rs.getInt("ID");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		    System.err.println(ex.getClass().getName() + " " + ex.getMessage());
            System.exit(0);
		}
		return primaryKey;		
	}
	
	@Override
	public int addEdge(Edge edge) {
		int primaryKey = -1;
		String sql = "INSERT INTO " + Struktur.EDGE + 
				" (corner_1_id, corner_2_id)" +
				" VALUES('" + 
				edge.getFirstCorner().getPrimaryKey() + "', '" +
				edge.getSecondCorner().getPrimaryKey() + "');";
		
		try {
			c.setAutoCommit(false);	
			stmt = c.createStatement();
 			stmt.executeUpdate(sql);
 			c.commit();	
 			
 			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()){
				primaryKey = rs.getInt(1);				 
			}
		}catch(Exception ex){
			ex.printStackTrace();
		    System.err.println(ex.getClass().getName() + " " + ex.getMessage());
            System.exit(0);
		}			
		return primaryKey;		
	}

	@Override
	public boolean isCornerLinkedToNode(Corner corner) {
		boolean tmp = false;
		String sql = "SELECT * FROM NODE WHERE "
				+ "corner_1_id = '" + corner.getPrimaryKey() + "' OR "
				+ "corner_2_id = '" + corner.getPrimaryKey() + "' OR "
				+ "corner_3_id = '" + corner.getPrimaryKey() + "';";
				
		try {
			c.setAutoCommit(false);	
			stmt = c.createStatement();
			System.out.println(sql);
 			ResultSet rs = stmt.executeQuery(sql);

 			c.commit();	
 			 
 			if(!rs.next()) {
 				tmp = true;
 				System.out.println(tmp + "err");
 				stmt.close();
  			   c.commit();
 				return tmp;

 			}else {
 				tmp = false; 
 				System.out.println(tmp+ "treert");
 				stmt.close();
  			   c.commit();
 				return tmp;
 			}

		}catch(Exception ex){
			ex.printStackTrace();
		    System.err.println(ex.getClass().getName() + " " + ex.getMessage());
            System.exit(0);

    		return true;
		}
	}

	@Override
	public void linkCornerToNode(Knoten node, Corner corner) {
		System.out.println("er");
		String sqlSelect = "SELECT * FROM "
				+ Struktur.NODE + " WHERE ID = " + node.getPrimaryKey() + ";";
		try {
			c.setAutoCommit(false);	
			stmt = c.createStatement();
 			
			ResultSet rs = stmt.executeQuery(sqlSelect);
 			
			if (rs.next()){
				int corner_1_id = rs.getInt("corner_1_id");
				int corner_2_id = rs.getInt("corner_2_id");
				int corner_3_id = rs.getInt("corner_3_id");
				String sqlInsert = null;
				String tmp = null;
				if(corner_1_id == 0){
					tmp = "corner_1_id";
				}else if(corner_2_id == 0){
					tmp = "corner_2_id";
				}else if(corner_3_id == 0){
					tmp = "corner_3_id";
				}
				
				sqlInsert = "UPDATE " + Struktur.NODE + " SET "				
						+ tmp +" = '" + corner.getPrimaryKey() + "'"																	
						+ "WHERE ID = " + node.getPrimaryKey();
				System.out.println(sqlInsert);
				stmt.executeUpdate(sqlInsert);
	 			c.commit();	
			}
		}catch(Exception ex){
			ex.printStackTrace();
		    System.err.println(ex.getClass().getName() + " " + ex.getMessage());
            System.exit(0);
		}		
	}
	
	@Override
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
