package spiel;

import java.util.Enumeration;
import java.util.Vector;

import enums.Struktur;
import interfaces.Konstanten;


public class Spiel 
implements interfaces.Konstanten{

	
	public static void main(String[] args)
	{
		/**
		 *   catch(ClassNotFoundException cnfe)
		 */
		/**
		 * Pool of Connection Elements. Get one if one is free and give it to
		 * spielbrett
		 * construktor each object connects to predefinded database 
		 */
		ConnectionPoolManager cmp = ConnectionPoolManager.getInstance();
		DatabaseConnector dbc = cmp.getDBCfromPool();
		/**
		 * ToDo: 
		 * Spielbrett baut sich selber
		 * und zwar mit Spielfeldern die aneinander gehängt werden.
		 * jedes feld hat 6 kanten und 6 knoten
		 */
		
		/**
		 * Phasen
		 * Spiel initialisieren(Spielbrett, Spieler, Startposition)
		 * Wuerfeln
		 * Rohstoffe einsammeln
		 * Optionen anzeigen
		 * Bauen 
		 * punkte berechnen wenn gesamtpunkte erreicht -> spiel vorbei
		 * wieder wuerfeln
		 */

		Spielbrett spielbrett = Spielbrett.getInstance(ANZ_FELDER_AUF_SPIELBRETT,dbc);
		SpielModerator moderator = SpielModerator.getInstance();
		Spieler spieler1 = new Spieler("Tim","rot");
		Spieler spieler2 = new Spieler("Giu", "gruen");
		Spieler[] s = new Spieler[2];


		s[0] = spieler1;
		s[1] = spieler2;
		s[0].setIstAmZug(true);
		
		System.out.println(s[0].anzGesamtpunkte());
		System.out.println(s[1].anzGesamtpunkte());
		int wuerfelZahl = Wuerfel.wuefeln();
		System.out.println("Spieler ");
		System.out.println(wuerfelZahl);
		if (wuerfelZahl == 7)
		{
			System.out.println("Der Raeuber kommt");
			for(int i = 0; i < s.length; i++)
			{
				if (s[i].getAnzRohstoffkarten() == MAX_KARTEN_BEI_RAEUBER)
				{
					s[i].kartenAbgeben();					
				}
			}
		}else {
		//	spielbrett.updateRohstoffeNachWurf(wuerfelZahl);
		}
		
		
		/**
		 * Phase Punkte berechnen
		 * Nach der Bauphase werden die Gesamtpunkte berechnet und 
		 * so erittelt ob ein Spieler das Spiel gewonnen hat
		 */
		cmp.close();
	}
	
	//for-each element in Pool close connection
	//for(Enumeration e =connectionPool.elements(); e.hasMoreElements();)
	//for(Vector v=ConnectionPool.elements())
	//dbc.close();
	
}
