package spiel;

import java.util.Vector;

import enums.Rohstoff;
import interfaces.Konstanten;

public class Spiel 
implements interfaces.Konstanten{
	public static void main(String[] args){
		/**
		 * Phasen
		 * Spiel initialisieren(Spielbrett, Spieler, Startposition)
		 * Wuerfeln
		 * Rohstoffe einsammeln
		 * Optionen anzeigen
		 * Bauen 
		 * punkte berechnen wenn gesamtpunkte erreicht -> zug vorbei
		 * wieder wuerfeln
		 */
		/**
		 * toDo create database table relation to setup siedler board
		 * 
		 * class und tbale knoten erstellen
		 * knoten zu kanten hinzufügen
		 */
		
		
		/**
		 *   catch(ClassNotFoundException cnfe)
		 *   catch(RelationNotFoundException rex) {
		 */
		
		ConnectionPoolManager cmp = ConnectionPoolManager.getInstance();
		DatabaseConnector dbc = cmp.getDBCfromPool();		
		Spielbrett spielbrett = Spielbrett.getInstance(dbc);
		SpielModerator moderator = SpielModerator.getInstance();
		
		Spieler spieler1 = new Spieler("Giu","blau");
		Spieler spieler2 = new Spieler("Tim", "gruen");
		Spieler[] s = new Spieler[2];


		s[0] = spieler1;
		s[1] = spieler2;
		s[0].setIstAmZug(true);
		
		while(!moderator.playerWonGame(s)) {
			for(int i = 0; i<s.length;i++) {
				System.out.println(s[0].anzGesamtpunkte());
			}
			
			Spieler activePlayer = moderator.nextActivePlayer(s);
 			
			int augenZahl = Wuerfel.wuefeln();
			if(augenZahl == Konstanten.ROBBER_ATTACK_NUMBER) {
				System.out.println("Der Raeuber kommt");
				moderator.checkAndRemoveCardsFromPlayers(s);
			}else {
				spielbrett.updateRohstoffeNachWurf(augenZahl);
			}
			if(!activePlayer.FourCardsOfOneKind().isEmpty()) {
				//trade with bank possible
				System.out.println("Player can trade with bank");
			}
			activePlayer.buildStructure();
			activePlayer.longestStreet();
		}
				
		cmp.pushDBCtoPool(dbc);
		dbc = null;
		cmp.close();
	}
}
