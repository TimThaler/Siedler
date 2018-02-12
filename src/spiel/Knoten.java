package spiel;

import interfaces.Bauwerk;

public class Knoten 
implements interfaces.Knoten{
	private final Corner corner1;
	private Corner corner2;
	private Corner corner3;
	private final int primaryKey;
	
	private Bauwerk bauwerk;

/*	public void assignToNode() {
		if(this.isCornerUnassigned()) {
			ConnectionPoolManager cmp = ConnectionPoolManager.getInstance();
			DatabaseConnector dbc  = cmp.getDBCfromPool();
			node should update itself
			dbc.updateNode(this, this);
			cmp.pushDBCtoPool(dbc);
			dbc = null;
			cmp = null;	
		}
	}*/
	
	public Knoten(Feld field){
		ConnectionPoolManager cmp = ConnectionPoolManager.getInstance();
		DatabaseConnector dbc  = cmp.getDBCfromPool();	
		this.bauwerk = null;	
		
		corner1 = field.getFreeCorner();
		System.out.println(corner1.getPrimaryKey());
		primaryKey = dbc.addNode(this);
		dbc.linkCornerToNode(this, corner1);
		
		cmp.pushDBCtoPool(dbc);
		dbc = null;
		cmp = null;
	}
	
	public boolean bebauenMoeglich(){
		//und ueberall zwei knoten weiter noch platz ist ...
		// get the neighbour of each corner 
		// via the edge relation
		return false;
	}

	public int getPrimaryKey() {
		return primaryKey;
	}

	@Override
	public Bauwerk getBauwerk() {
		return this.bauwerk;
	}
}
