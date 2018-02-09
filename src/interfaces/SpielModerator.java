package interfaces;

public interface SpielModerator {
	public boolean playerWonGame(spiel.Spieler[] players);
	public spiel.Spieler nextActivePlayer(spiel.Spieler[] players);
	public void checkAndRemoveCardsFromPlayers(spiel.Spieler[] players);

}
