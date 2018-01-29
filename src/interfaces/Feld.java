package interfaces;

import enums.Rohstoff;

public interface Feld {
	int tmp = 0;
	public int getFeldWuerfelNummer();
	public Rohstoff getRohstoff();
	public void updateRohstoffePerBauwerk();
	public boolean istFeldBebaut();

}
