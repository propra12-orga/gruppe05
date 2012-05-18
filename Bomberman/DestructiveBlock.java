package Bomberman;

import java.awt.Image;
import java.awt.image.BufferedImage;


public class DestructiveBlock extends Objekt
{	
	public DestructiveBlock(BufferedImage[] i, double x, double y, long delay, Arena p)
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

