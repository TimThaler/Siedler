package interfaces;

import java.util.Vector;

import enums.Rohstoff;
import spiel.Knoten;

public interface Spieler {
	public String getName();
	public String getFarbe();
	public int getAnzSiedlungen();
	public int getAnzStaedte();
	public int getAnzZusammenhaengenderStrassen();
	public int anzGesamtpunkte();
	public boolean istSiedlungBauenMoeglich();
	public boolean istStadtBauenMoeglich();
	public void siedlungBauen(Knoten knoten);
	public void stadtBauen(Knoten knoten);
	public void updateRohstoff(Rohstoff rohstoff,Bauwerk bauwerk);
	public int getAnzRohstoffkarten();
	public void kartenAbgeben();
	public Vector<Rohstoff> FourCardsOfOneKind();
	public void buildStructure();
	public void longestStreet();
	

}
