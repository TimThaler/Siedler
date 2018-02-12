package interfaces;

import enums.Struktur;
import spiel.Edge;

public interface DatabaseConnector {
	public void clearTable(Struktur struktur);
	public boolean tableExists(Struktur struktur);
	
	public void createTable(Struktur struktur);	
	public int addNode(spiel.Knoten node);
	public int addField(spiel.Feld feld);
	public int addCorner(spiel.Corner corner);
	public int addEdge(Edge edge);
		
	public void close();
	
}