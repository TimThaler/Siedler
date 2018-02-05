package spiel;

public class Spiel 
implements interfaces.Konstanten{

	
	public static void main(String[] args)
	{
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
		 * * Spielbrett baut sich selber
		 * und zwar mit Spielfeldern die aneinander gehängt werden.
		 * jedes feld hat 6 kanten und 6 knoten
		 * 
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
		
		System.out.println(s[0].anzGesamtpunkte());
		System.out.println(s[1].anzGesamtpunkte());
		int wuerfelZahl = Wuerfel.wuefeln();
		System.out.println("Spieler ");
		System.out.println(wuerfelZahl);
		if (wuerfelZahl == 7){
			System.out.println("Der Raeuber kommt");
			for(int i = 0; i < s.length; i++){
				if (s[i].getAnzRohstoffkarten() == MAX_KARTEN_BEI_RAEUBER){
					s[i].kartenAbgeben();					
				}
			}
		}else{
		//	spielbrett.updateRohstoffeNachWurf(wuerfelZahl);
		}
		
	
		cmp.pushDBCtoPool(dbc);
		cmp.close();
	}
	
	//for-each element in Pool close connection
	//for(Enumeration e =connectionPool.elements(); e.hasMoreElements();)
	//for(Vector v=ConnectionPool.elements())
	//dbc.close();
	
}
