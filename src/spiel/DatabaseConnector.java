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

public class DatabaseConnector {
	Connection c = null;

	public DatabaseConnector(){
		try{
			this.c = DriverManager
					.getConnection(Konstanten.POSTGRES_URL,
							Konstanten.POSTGRES_USER,
							Konstanten.POSTGRES_PASSWORD);
		}catch(Exception e){
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}
	
	public static void clearTable(Struktur struktur) {
		Connection c = null;
		Statement stmt = null;
		String sql = "DELETE  FROM " + struktur.toString().toLowerCase();
        
		try {
			c = DriverManager
					.getConnection(Konstanten.POSTGRES_URL,
							Konstanten.POSTGRES_USER,
							Konstanten.POSTGRES_PASSWORD);
			
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("[***] Table "+ struktur.toString() + " cleared");
		}catch (Exception ex) {
			ex.printStackTrace();
			}finally {
			   
			    try { stmt.close(); } catch (Exception e) { /* ignored */ }
			    try { c.close(); } catch (Exception e) { /* ignored */ }
			}


	}
	public boolean tableExists(Struktur struktur){
		try {
			DatabaseMetaData dmd = null;
			dmd = c.getMetaData();
			String tableName = struktur.toString().toLowerCase();

			ResultSet rs = dmd.getTables(null, null,"%", new String[] {"TABLE"});
			while (rs.next()){			
				if(rs.getString(3).equals(tableName)){
					return true;
				}

			}
			return false;
		}catch(SQLException e) {System.exit(0);}
		return false;
	}

	public static void connectToSiedlerDatabase()
	{
		try {
			Connection c = DriverManager
					.getConnection(Konstanten.POSTGRES_URL,
							Konstanten.POSTGRES_USER,
							Konstanten.POSTGRES_PASSWORD);
			System.out.println("[***] Opened database successfully");



			c.close();			   
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
	}

	public static void 	 createTable(Struktur struktur) {
		String tableName = null;
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
		
			switch (struktur){
				case FIELD:
					tableName = "feld";
					setupParams = Konstanten.FIELD_TABLE_SETUP;
					break;
				case Knoten:
					tableName = "knoten";
					setupParams = Konstanten.KNOTENTABLESETUP;
					break;
				case CORNER:
					tableName = "corner";
					setupParams = Konstanten.ECKENTABLESETUP;
					break;
			}
			
			rs =  dbmd.getTables(null, null, tableName.toLowerCase(), new String[] {"TABLE"});
			stmt = c.createStatement(); 
			String sql = null;
			
			if (rs.next()) {	
				System.out.println("[***] Table " + tableName + " exists");
						
                //System.out.println(rs.getString("TABLE_NAME"));
                //sql = "DELETE  FROM " + tableName;
                stmt.executeUpdate(sql); 
                //System.out.println(sql);

			}else{
                System.out.println("[***] Table " + tableName + " does not exist"); 
                if (struktur == Struktur.CORNER)
                {
                	sql = "Create table ecke (ID SERIAL UNIQUE, field_id integer REFERENCES field(id));" ;
                }else if (struktur ==Struktur.FIELD) {
                	sql = "Create table field (ID SERIAL UNIQUE," + setupParams + ");";       
                }
                System.out.println(sql + " HELLO ");
                	stmt.executeUpdate(sql); 	
    			
			}
			System.out.println("[***] Table " + tableName + " created"); 
			//System.out.println(sql);
			
		}/*atch(SQLException ex) {
			
			System.out.print("Hallo");
		}*/
		catch(SQLException ex){
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
		String sql = "INSERT INTO " + Struktur.FIELD + "( NAME,ROHSTOFF) VALUES(?, ?)";

		try {			   
			c = DriverManager.getConnection(Konstanten.POSTGRES_URL,
							Konstanten.POSTGRES_USER,
							Konstanten.POSTGRES_PASSWORD
							);
	   
			PreparedStatement pstmt = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, "Erz");
			pstmt.setString(2, "Erz");
			
			pstmt.executeUpdate();
			System.out.println(sql);
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
		return primaryKey;
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
