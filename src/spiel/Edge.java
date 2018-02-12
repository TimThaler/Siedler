package spiel;

public class Edge {
	private final Corner firstCorner;
	private final Corner secondCorner;
	private final int primaryKey;
	
	public Edge(Corner firstCorner, Corner secondCorner) {
		ConnectionPoolManager cmp = ConnectionPoolManager.getInstance();
		DatabaseConnector dbc  = cmp.getDBCfromPool();
		
		this.firstCorner = firstCorner;
		this.secondCorner = secondCorner;
		primaryKey = dbc.addEdge(this);
		
		cmp.pushDBCtoPool(dbc);
		dbc = null;
		cmp = null;
	}

	public Corner getFirstCorner() {
		return this.firstCorner;
	}

	public Corner getSecondCorner() {
		return this.secondCorner;
	}	
}
