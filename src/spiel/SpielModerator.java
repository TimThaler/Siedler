package spiel;

public class SpielModerator
implements interfaces.SpielModerator, interfaces.Konstanten{
	private static SpielModerator instance = null;
	
	private SpielModerator(){
	}
	
	public static SpielModerator getInstance(){
		if (instance == null){
			instance = new SpielModerator();
		}
		return instance;
	}
	
	public Spieler aktiverSpieler(){
		return null;
	}

	public void naechsterSpieleramZug(){
		
	}
	
	/**
	 * jeder spieler setzt zwei siedlungen
	 * jeder spieler bekommt  rohstoffkarten fure die zweite siedlung
	 * ein aktiver spieler wird ausgewaehlt
	 * 
	 * @return
	 */
	public boolean spielInitialisieren(){
		return false;
	}
	
	public boolean gesammtpunkteBerechnen(Spieler s){
		boolean gewinnerVorhanden = false;
			if(s.anzGesamtpunkte()>= MAX_POINTS_TO_WIN ){
				gewinnerVorhanden = true;
			}	
		return gewinnerVorhanden;
	}

	@Override
	public boolean playerWonGame(Spieler[] player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Spieler nextActivePlayer(Spieler[] players) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkAndRemoveCardsFromPlayers(Spieler[] players) {
		for(int i = 0; i < players.length; i++){
			if (players[i].getAnzRohstoffkarten() == MAX_CARDS_WITH_ROBBER){
				players[i].kartenAbgeben();					
			}
		}
		
	}
}

