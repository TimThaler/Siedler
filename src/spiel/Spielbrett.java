package spiel;

import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import enums.Rohstoff;
import enums.Struktur;
import interfaces.Konstanten;

public class Spielbrett 
implements interfaces.Spielbrett{
	public static Spielbrett instance = null;
	public DatabaseConnector dbc = null;
	private Vector<Feld> felder = null;
	private Vector<Knoten> nodes = null;
	
	public static Spielbrett getInstance(DatabaseConnector dbc){
		if(instance == null){
			instance = new Spielbrett(dbc);
		}
		return instance;
	}
	
	private Spielbrett(DatabaseConnector dbc){
		this.dbc = dbc;					
		this.felder = new Vector<Feld>();
		this.nodes = new Vector<Knoten>();
		Random r = new Random();
    
		if(dbc.tableExists(Struktur.EDGE)){
        	dbc.clearTable(Struktur.EDGE);
        }else{
        	dbc.createTable(Struktur.EDGE);
        }            
		
		if(dbc.tableExists(Struktur.CORNER)){
        	dbc.clearTable(Struktur.CORNER);
        }else{
        	dbc.createTable(Struktur.CORNER);
        }
		
		if(dbc.tableExists(Struktur.FIELD)){	        			
			dbc.clearTable(Struktur.FIELD);	        		 
		}else{	     		
			//dbc.createTableField();	        			
			dbc.createTable(Struktur.FIELD);	        	
		} 
		      
                
        for(int i = 0; i < Konstanten.FIELDS_ON_BOARD; i++){	
        	
			Rohstoff rohstoff = Rohstoff.values()[(r.nextInt(5))];
			Feld feld = new Feld((r.nextInt(11)+1),rohstoff);
			
			felder.addElement(feld);
		}        
       
        for(Feld f : felder){
 
        	
        	
        	
        	// Create nodes for first field
        	for(int k = 0; k < Konstanten.CORNERS_PER_FIELD; k++) {
        		// Create 6 nodes and assign each a free and unique corner id 
        		// from the first field
        	/*	Knoten node = new Knoten(f.getFreeCornerID())
        		nodes.add(node);
        		int pkNode = dbc.addNode(node);*/
        		//save pk of node in the node object and then in the vector
        	}
        	//create first ring
        //	drawHexagon(felder,nodes);
        	//create 2nd ring
        	
		}	                  
	}
	
	private void drawHexagon(Vector<Feld> felder, Vector<Knoten> nodes){
		
		
	}
	
	public void updateRohstoffeNachWurf(int augenZahl){
		for(Feld feld: felder){
			if (feld.getFeldWuerfelNummer() == augenZahl){
				if(feld.istFeldBebaut()){
					feld.updateRohstoffePerBauwerk();
				}
			}
		}
	}
}
