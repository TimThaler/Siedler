package interfaces;

import enums.Struktur;

public interface DatabaseConnector {
	public void clearTable(Struktur struktur);
	public boolean tableExists(Struktur struktur);
	
	public void createTable(Struktur struktur);	
	
	public int addField(spiel.Feld feld);
	public int addCorner(spiel.Corner corner);
	public int addEdge(spiel.Edge edge);
		
	public boolean isCornerLinkedToNode(spiel.Corner corner);
	public void linkCornerToNode(spiel.Knoten node, spiel.Corner corner);
	
	public void close();
	public int addNode(spiel.Knoten node);
	
}