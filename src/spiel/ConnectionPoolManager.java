package spiel;

import java.util.Vector;
import interfaces.Konstanten;

public class ConnectionPoolManager {
	 
	private static ConnectionPoolManager instance = null;
	public Vector<DatabaseConnector> connectionPool = new Vector<DatabaseConnector>();
	
	public static ConnectionPoolManager getInstance()
	{
		if(instance == null)
		{
			instance = new ConnectionPoolManager();
		}
		return instance;
	}
	
	private ConnectionPoolManager()
	{
		while(!isConnectionPoolFull())
		{
			DatabaseConnector dbc = new DatabaseConnector();
			connectionPool.addElement(dbc);
		}
	}
	
	public boolean isConnectionPoolFull() {
		if(connectionPool.size() < Konstanten.MAX_POOL_SIZE)
		{
			return true;
		}
		return false;
	}
	
	public synchronized DatabaseConnector getDBCfromPool()
	{
		DatabaseConnector dbc = null;
		if(this.connectionPool.size() > 0) {
			dbc = this.connectionPool.firstElement();
			this.connectionPool.removeElementAt(0);
		}
		
		return dbc;
	}
		
	public synchronized void pushDBCtoPool(DatabaseConnector dbc)
	{
		this.connectionPool.addElement(dbc);		 
	}
	
	public void close()
	{
		for(DatabaseConnector dbc : connectionPool)
		{
			dbc.close();
		}
	}
}
