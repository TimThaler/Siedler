package spiel;

public class Corner {
	int primaryKey = 0;
	int pkLinkedField = 0;
	
	public Corner(int pkField) {
		setPkLinkedField(pkField);
	}
	
	public int getPrimaryKey() {
		return primaryKey;
	}
	
	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public int getPkLinkedField() {
		return pkLinkedField;
	}
	
	private void setPkLinkedField(int pkLinkedField) {
		this.pkLinkedField = pkLinkedField;
	}
}
