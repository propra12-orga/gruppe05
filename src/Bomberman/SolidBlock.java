package Bomberman;

import java.awt.image.BufferedImage;


public class SolidBlock extends Objekt
{	
	public SolidBlock(BufferedImage[] i, double x, double y, long delay, Arena p)
	{
		super(i, x, y, delay, p);
	}

	public boolean collidedWith(Objekt s) 
	{
		
		if(this.intersects(s))
		{
			if(s instanceof Player)
			{
				if(Math.abs((s.getX() - getMaxX())) <= 5 && s.getX() < getMaxX() && ( s.getY() < getMaxY() && s.getMaxY() > getY()))
				{
					s.x = getMaxX();
					return true;
				}
				if(Math.abs((s.getMaxX() - getX())) <= 5 && s.getMaxX() > getX() && ( s.getY() < getMaxY() && s.getMaxY() > getY()))
				{
					s.x = getX() - 30;
					return true;
				}
				if(Math.abs((s.getY() - getMaxY())) <= 5 && s.getY() < getMaxY() && ( s.getX() < getMaxX() && s.getMaxX() > getX()))
				{
					s.y = getMaxY();
					return true;
				}
				if(Math.abs((s.getMaxY() - getY())) <= 5 && s.getMaxY() > getY() && ( s.getX() < getMaxX() && s.getMaxX() > getX()))
				{
					s.y = getY() - 30;
					return true;
				}
				return true;
			}
		}
	
		return false;
	}	
}
