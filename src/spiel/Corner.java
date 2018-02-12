package spiel;

public class Corner {
	final int primaryKey;
	int pkLinkedField = 0;
	
	public Corner(Feld feld) {
		ConnectionPoolManager cmp = ConnectionPoolManager.getInstance();
		DatabaseConnector dbc  = cmp.getDBCfromPool();
		
		setPkLinkedField(feld.getPrimaryKey());
		primaryKey = dbc.addCorner(this);
		
		cmp.pushDBCtoPool(dbc);
		dbc = null;
		cmp = null;
	}
	
	public int getPrimaryKey() {
		return primaryKey;
	}
	
	public int getPkLinkedField() {
		return pkLinkedField;
	}
	
	private void setPkLinkedField(int pkLinkedField) {
		this.pkLinkedField = pkLinkedField;
	}
}
