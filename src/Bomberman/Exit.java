package bomberman;

import java.awt.image.BufferedImage;


public class Exit extends Objekt
{	
	public Exit(BufferedImage[] i, double x, double y, long delay, Arena p)
	{
		super(i, x, y, delay, p);
	}

	public boolean collidedWith(Objekt s) 
	{		
		//Wenn der Ausgang mit dem Spieler kollidiert wird true zurueckgegeben
		if(s instanceof Player)
		{
			//if-Bedinung, damit erst true zurueckgegeben wird, wenn der Spieler sind etwas mehr auf dem 
			//Ausgang befindet. Sonst wuerde direkt beim Randkontakt true geliefert werden.
			if(Math.abs(s.getCenterX() - this.getCenterX()) <= 15 && Math.abs(s.getCenterY() - this.getCenterY()) <= 15)
			{
				return true;
			}
		}
		
		return false;
	}	
	
	public void delete()
	{
		
	}
}

