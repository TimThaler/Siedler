package spiel;

import interfaces.Bauwerk;

public class Knoten 
implements interfaces.Knoten{
	// maybe not needed
	//private Knoten rightNeighbour = null;
	//private Knoten leftNeighbour= null;	
	
	public boolean bebaut = false;
	//Feld[] angrendeneFelder = new Feld[3];
	Bauwerk bauwerk = null;
	
	public Knoten(Feld feld){
		this.bebaut = false;		
	}
	
	public boolean bebauenMoeglich(){
		//und ueberall zwei knoten weiter noch platz ist ...
		if(!bebaut){
			return true;
		}else{
			return false;
		}
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
