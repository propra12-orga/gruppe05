package Bomberman;

import java.awt.image.BufferedImage;


public class Player extends Objekt 
{

	public Player(BufferedImage[] i, double x, double y, long delay, Arena p) 
	{
		super(i, x, y, delay, p);
	}

	public void doLogic(long delta) 
	{
		super.doLogic(delta);
		
		if(getX()<0)
		{
			setHorizontalSpeed(0);
			x = 0;
		}

		if(getY()<0)
		{
			setHorizontalSpeed(0);
			y = 0;
		}
		
		if(getX()+getWidth()>parent.getWidth())
		{
			setVerticalSpeed(0);
			x = parent.getWidth()-getWidth();
		}
		
		if(getY()+getHeight()>parent.getHeight())
		{
			setVerticalSpeed(0);
			y = parent.getHeight()-getHeight();
		}		
	}

	public void setHorizontalSpeed(double dx)
	{
		super.setHorizontalSpeed(dx);
		
		if(getHorizontalSpeed() > 0 && (getLoopFrom() != 8 && getLoopTo() != 11))
		{
			setLoop(8, 11);
		}
		else if(getHorizontalSpeed() < 0 && (getLoopFrom() != 12 && getLoopTo() != 15))
		{
			setLoop(12, 15);
		}
		else if(getHorizontalSpeed() == 0 && getVerticalSpeed() == 0)
			setLoop(6, 6);
	}
	
	public void setVerticalSpeed(double dy)
	{
		super.setVerticalSpeed(dy);

		if(getVerticalSpeed() > 0 && (getLoopFrom() != 4 && getLoopTo() != 7))
		{
			setLoop(4, 7);
		}
		else if(getVerticalSpeed() < 0 && (getLoopFrom() != 0 && getLoopTo() != 3))
		{
			setLoop(0, 3);
		}
		else if(getHorizontalSpeed() == 0 && getVerticalSpeed() == 0)
			setLoop(6, 6);
	}

	public boolean collidedWith(Objekt s) 
	{
		if(this.intersects(s))
		{
			if(s instanceof SolidBlock)
			{
				if(Math.abs((getX() - s.getMaxX())) <= 5 && getX() < s.getMaxX() && ( getY() < s.getMaxY() && getMaxY() > s.getY()))
				{
					x = s.getMaxX();
					return true;
				}
				if(Math.abs((getMaxX() - s.getX())) <= 5 && getMaxX() > s.getX() && ( getY() < s.getMaxY() && getMaxY() > s.getY()))
				{
					x = s.getX() - 30;
					return true;
				}
				if(Math.abs((getY() - s.getMaxY())) <= 5 && getY() < s.getMaxY() && ( getX() < s.getMaxX() && getMaxX() > s.getX()))
				{
					y = s.getMaxY();
					return true;
				}
				if(Math.abs((getMaxY() - s.getY())) <= 5 && getMaxY() > s.getY() && ( getX() < s.getMaxX() && getMaxX() > s.getX()))
				{
					y = s.getY() - 30;
					return true;
				}
				return true;
			}
		}
		
		return false;
	}
	
}

