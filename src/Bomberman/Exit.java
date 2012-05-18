package Bomberman;

import java.awt.image.BufferedImage;


public class Exit extends Objekt
{	
	public Exit(BufferedImage[] i, double x, double y, long delay, Arena p)
	{
		super(i, x, y, delay, p);
	}

	public boolean collidedWith(Objekt s) 
	{
		return false;
	}	
	
	public void delete()
	{
		
	}
}

