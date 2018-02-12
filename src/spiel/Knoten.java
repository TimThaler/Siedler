package spiel;

import interfaces.Bauwerk;

public class Knoten 
implements interfaces.Knoten{
	private final Corner fieldCorner1;
	private Corner fieldCorner2;
	private Corner fieldCorner3;
	
	public boolean bebaut;
	public Bauwerk bauwerk;
	
	
	public Knoten(Feld field){
		this.bebaut = false;
		this.bauwerk = null;
		
		fieldCorner1 = field.getFreeCorner();
		
		
	}
	
	public boolean bebauenMoeglich(){
		//und ueberall zwei knoten weiter noch platz ist ...
		// get the neighbour of each corner 
		// via the edge relation
		return false;
	}

	@Override
	public Bauwerk getBauwerk() {
		// TODO Auto-generated method stub
		return this.bauwerk;
	}

	public Knoten getRightNeighbour() {
		return null;
	}

	public void setRightNeighbour(Knoten rightNeighbour) {
		//this.rightNeighbour = rightNeighbour;
	}

	public Knoten getLeftNeighbour() {
		return null;
	}

	public void setLeftNeighbour(Knoten leftNeighbour) {
		//this.leftNeighbour = leftNeighbour;
	}

	@Override
	public void setEdge1(Edge edge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEdge2(Edge edge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEdge3(Edge edge) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Edge getEdge1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge getEdge2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge getEdge3() {
		// TODO Auto-generated method stub
		return null;
	}
}
