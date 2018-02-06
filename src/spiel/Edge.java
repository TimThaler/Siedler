package spiel;

public class Edge {
	private int edge1Id = 0;
	private int edge2Id = 0;
	private int fieldId = 0;
	
	public Edge(int edge1ID, int edge2Id, int fieldID) {
		this.setEdge1Id(edge1ID);
		this.setEdge2Id(edge2Id);
		this.setFieldId(fieldID);
	}

	public int getEdge1Id() {
		return edge1Id;
	}

	private void setEdge1Id(int edge1Id) {
		this.edge1Id = edge1Id;
	}

	public int getEdge2Id() {
		return edge2Id;
	}

	private void setEdge2Id(int edge2Id) {
		this.edge2Id = edge2Id;
	}

	public int getFieldId() {
		return fieldId;
	}

	private void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

}
