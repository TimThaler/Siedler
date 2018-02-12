package spiel;

import java.util.Vector;

import enums.Rohstoff;
import interfaces.Bauwerk;
import interfaces.Konstanten;

public class Feld 
implements interfaces.Feld{
	private final int primaryKey;
	private enums.Rohstoff rohstoff = null;
	private int wuerfelzahl = 0;
	private Knoten[] knoten;
	private boolean bebaut = false;
	private boolean occupiedByRobber = false;
	private Vector<Corner> corners = new Vector<Corner>();
	
	public Feld(int wurfelzahl, Rohstoff rohstoff){
		this.rohstoff = rohstoff;
		this.wuerfelzahl = wurfelzahl;
		
		ConnectionPoolManager cmp = ConnectionPoolManager.getInstance();
		DatabaseConnector dbc  = cmp.getDBCfromPool();
			
       	this.primaryKey = dbc.addField(this);     	
      
		for(int x =0; x < Konstanten.CORNERS_PER_FIELD; x++){
    		Corner corner = new Corner(this);
    		this.corners.addElement(corner);
      	}	
		
    	int i = 0;
    	while(i < Konstanten.CORNERS_PER_FIELD - 1 ){
    		new Edge(corners.elementAt(i),
    				corners.elementAt(i+1) 
    				);    		
    		i++;
    	}
      
    	new Edge(corners.elementAt(0), 
    			corners.lastElement()
    			);
    	
    	cmp.pushDBCtoPool(dbc);
		dbc = null;
		cmp = null;
	}
	
	@Override
	public Corner getFreeCorner() {
		for(Corner c : corners) {
			if (c.isCornerUnassigned())	{	
				System.out.println("corner  is free: " + c.getPrimaryKey());
				return c;
			}
			System.out.println("no no");

		}
		return null;
	}
	
	public void updateRohstoffePerBauwerk(){
		if(!occupiedByRobber){
			for(int i = 0; i < knoten.length; i++){
				if(knoten[i].getBauwerk().equals(null)){
					Bauwerk bauwerk = knoten[i].getBauwerk();
					bauwerk.getBesitzer().updateRohstoff(this.getRohstoff(),bauwerk);
				}
			}
		}
	}
	
	public void addCorner(Corner corner) {
		this.corners.addElement(corner);
	}
	
	public boolean isOccupiedByRobber() {
		return occupiedByRobber;
	}

	public void setVomRaeuberBesetzt(boolean occupiedByRobber) {
		this.occupiedByRobber = occupiedByRobber;
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

	public int getPrimaryKey() {
		return primaryKey;
	}
}
