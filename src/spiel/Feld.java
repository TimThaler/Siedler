package spiel;

import java.util.Vector;

import enums.Rohstoff;
import interfaces.Bauwerk;
import interfaces.Konstanten;

public class Feld 
implements interfaces.Feld{
	private final int pk;
	private enums.Rohstoff rohstoff = null;
	private int wuerfelzahl = 0;
	private Knoten[] knoten;
	private boolean bebaut = false;
	private boolean vomRaeuberBesetzt = false;
	private Vector<Corner> corners = new Vector<Corner>();
	
	public void addCorner(Corner corner) {
		this.corners.addElement(corner);
	}
	
	public Feld(int wurfelzahl, Rohstoff rohstoff){
		this.rohstoff = rohstoff;
		this.wuerfelzahl = wurfelzahl;
		
		ConnectionPoolManager cmp = ConnectionPoolManager.getInstance();
		DatabaseConnector dbc  = cmp.getDBCfromPool();
			
       	this.pk = dbc.addField(this);     	
      
		for(int x =0; x < Konstanten.CORNERS_PER_FIELD; x++){
    		Corner corner = new Corner(this);
    		System.out.println(corner.getPrimaryKey());
    		this.corners.addElement(corner);
      	}	
		
    	int i = 0;
    	while(i < Konstanten.CORNERS_PER_FIELD - 1 ){
    		Edge edge = new Edge(corners.elementAt(i).getPrimaryKey(),
    				corners.elementAt(i+1).getPrimaryKey(), 
    				pk);    		
    		dbc.addEdge(edge);
    		i++;
    	}
        
    	Edge edge = new Edge(corners.elementAt(0).getPrimaryKey(), 
    			corners.lastElement().getPrimaryKey(),
    			pk);
    	dbc.addEdge(edge);
    	
    	cmp.pushDBCtoPool(dbc);
		dbc = null;
		cmp = null;
	}
	
	@Override
	public Corner getFreeCorner() {
		if(!this.corners.isEmpty()) {
			Corner corner = this.corners.firstElement();
			this.corners.remove(0);
			return corner;
			}
		return null;
	}
	
	public void updateRohstoffePerBauwerk(){
		if(!vomRaeuberBesetzt){
			for(int i = 0; i < knoten.length; i++){
				if(knoten[i].bebaut){
					Bauwerk bauwerk = knoten[i].getBauwerk();
					bauwerk.getBesitzer().updateRohstoff(this.getRohstoff(),bauwerk);
				}
			}
		}
	}
	
	public boolean istVomRaeuberBesetzt() {
		return vomRaeuberBesetzt;
	}

	public void setVomRaeuberBesetzt(boolean vomRaeuberBesetzt) {
		this.vomRaeuberBesetzt = vomRaeuberBesetzt;
	}

	public int getFeldWuerfelNummer() {
		return this.wuerfelzahl;
	}

	public Rohstoff getRohstoff() {
		return this.rohstoff;
	}
	
	public boolean istFeldBebaut(){
		return this.bebaut;
	}

	public int getPk() {
		return pk;
	}
}
