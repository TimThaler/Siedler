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

		 
		if(dbc.tableExists(Struktur.FIELD)){	        			
			dbc.clearTable(Struktur.FIELD);	        		 
		}else{	     		
			//dbc.createTableField();	        			
			dbc.createTable(Struktur.FIELD);	        	
		}     
		
		
		if(dbc.tableExists(Struktur.CORNER)){
        	dbc.clearTable(Struktur.CORNER);
        }else{
        	dbc.createTable(Struktur.CORNER);
        }
		
		if(dbc.tableExists(Struktur.EDGE)){
        	dbc.clearTable(Struktur.EDGE);
        }else{
        	dbc.createTable(Struktur.EDGE);
        }                  
                
        for(int i = 0; i < Konstanten.FIELDS_ON_BOARD; i++){	
			Rohstoff rohstoff = Rohstoff.values()[(r.nextInt(5))];
			Feld feld = new Feld((r.nextInt(11)+1),rohstoff);
			felder.addElement(feld);
		}        
       
        for(Feld f : felder){
        	int primaryKeyField = dbc.addField(f);
        	int[] array = new int[Konstanten.CORNERS_PER_FIELD];
        	
        	// Create six corners for each field
        	for(int x =0; x < array.length; x++){
        		array[x] = dbc.addCorner(primaryKeyField);
        	}
        	// Create six edges for each field
        	int i = 0;
        	while(i < array.length - 1){
        		Edge edge = new Edge(array[i], array[i+1], primaryKeyField);
        		dbc.addEdge(edge);
        		i++;
        	}
        	Edge edge = new Edge(array[0], array[array.length-1], primaryKeyField);
        	dbc.addEdge(edge);
        	
        	// Create nodes for first field
        	for(int k = 0; k < Konstanten.CORNERS_PER_FIELD; k++) {
        		Knoten node = new Knoten(dbc.getFreeCornerID(f));
        		nodes.add(node);
        		int pkNode = dbc.addNode(node);
        		//save pk of node in the node object and then in the vector
        		nodes.add(new Knoten(dbc.getFreeCornerID(f)));
        	}
        	//create first ring
        	drawHexagon(felder,nodes);
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
