package spiel;

import java.util.Random;

public class Wuerfel {
	public static int wuefeln()
	{
		Random r = new Random();
		return r.nextInt(12)+1;
	}
	

}
