package Bomberman;

import java.awt.image.BufferedImage;

public class Road extends Objekt
{
	public Road(BufferedImage[] i, double x, double y, long delay, Arena p)
	{
		super(i, x, y, delay, p);
	}

	public boolean collidedWith(Objekt s) 
	{
		return false;
	}
}
