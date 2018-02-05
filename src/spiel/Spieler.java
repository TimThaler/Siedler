package spiel;

import java.util.ArrayList;

import enums.Rohstoff;
import interfaces.Bauwerk;

public class Spieler 
implements interfaces.Spieler{
	private String name = null;
	private String farbe = null;
	
	private ArrayList<Siedlung> siedlungen;
	private ArrayList<Stadt> staedte;
	
	private int anzHolz = 0;
	private int anzLehm = 0;
	private int anzGetreide = 0;
	private int anzWolle = 0;
	private int anzErz = 0;
	
	private boolean istAmZug = false;
	
	
	public Spieler(String name,String farbe){
		this.name = name;
		this.farbe = farbe;
		istAmZug = false;
		staedte = new ArrayList<Stadt>();
		siedlungen = new ArrayList<Siedlung>();
	}

	public String getName() {
		return this.name;
	}

	public String getFarbe() {
		return this.farbe;
	}

	public int getAnzSiedlungen() {
		// TODO Auto-generated method stub
		return siedlungen.size();
	}

	public int getAnzStaedte() {
		// TODO Auto-generated method stub
		return staedte.size();
	}

	public int getAnzZusammenhaengenderStrassen() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int anzGesamtpunkte() {
		return this.siedlungen.size() + (this.staedte.size()) * 2;		
	}
	@Override
	public boolean istSiedlungBauenMoeglich() {
		if(this.anzHolz > 0 
				&& this.anzLehm > 0 
				&& this.anzGetreide > 0 
				&& this.anzWolle > 0){
			return true;
		}else{				
			return false;		
		}
	}
	@Override
	public boolean istStadtBauenMoeglich() {
		if(this.anzErz > 3 && this.anzGetreide > 2){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public void siedlungBauen(Knoten knoten) {
		if (istSiedlungBauenMoeglich()) {
			this.anzGetreide -= 1;
			this.anzHolz -= 1;
			this.anzLehm -= 1;
			this.anzWolle -= 1;
			
			Siedlung siedlung = new Siedlung(this, knoten);
			this.siedlungen.add(siedlung);		
		}
	}
	@Override
	public void stadtBauen(Knoten knoten) {
		if (istStadtBauenMoeglich()) {
			this.anzErz -= 3;
			this.anzGetreide -= 2;
			
			Stadt stadt = new Stadt(this, knoten);
			this.staedte.add(stadt);
		}	
	}
	@Override
	public void updateRohstoff(Rohstoff rohstoff, Bauwerk bauwerk) {
		int inc = 0;
		if(bauwerk.getClass().equals(Siedlung.class)){
			inc = 1; 
		}else if(bauwerk instanceof Stadt){
			inc = 2;
		}else{
			//throw new Exception();
			//throw exception e; 
		}
		
		switch(rohstoff){
			case Erz: 
				anzErz += inc;
				break;
			case Holz:
				anzHolz += inc;
				break;
			case Lehm:
				anzLehm += inc;
				break;
			case Wolle:
				anzWolle += inc;
				break;
			case Getreide:
				anzGetreide += inc;
				break;
		}
	}
	
	@Override
	public int getAnzRohstoffkarten() {
		return anzErz + anzGetreide + anzHolz + anzLehm + anzWolle;
	}
	
	@Override
	public void kartenAbgeben() {
		if (getAnzRohstoffkarten() % 2 == 0){
			
		}else{
			if (anzErz>0)
			anzErz --;
			kartenAbgeben();
		}		
	}
	
	public boolean isIstAmZug() {
		return istAmZug;
	}
	public void setIstAmZug(boolean istAmZug) {
		this.istAmZug = istAmZug;
	}
}
