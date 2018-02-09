package interfaces;

import spiel.Edge;

public interface Knoten {
	public boolean bebauenMoeglich();
	public Bauwerk getBauwerk();
	
	public void setEdge1(Edge edge);
	public void setEdge2(Edge edge);
	public void setEdge3(Edge edge);
	
	public Edge getEdge1();
	public Edge getEdge2();
	public Edge getEdge3();


}
