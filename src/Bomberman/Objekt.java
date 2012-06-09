package Bomberman;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Objekt extends Rectangle2D.Double implements Drawable, Movable
{
	long delay;
	long animation = 0;
	
	Arena parent;
	protected BufferedImage[] pics;
	
	int currentpic = 0;
  
	protected double dx;
	protected double dy;
	
	//Zwei Variablen um zu definieren, von wo bis wo die Animation der Bilder geht.
	int loop_from;
	int loop_to;
	
	boolean remove;
	
	//Ein neues Objekt bekommt Bilder, x und y Koordinaten, delay = Anzeigedauer der einzelnen Bilder (fuer
	//Animationen) und die Arena als "Vater" zugeteilt.
	public Objekt(BufferedImage[] i, double x, double y, long delay, Arena p )
	{
		pics = i;
		
		this.x = x;
		this.y = y;
		this.delay = delay;
		
		//Bestimmt Breite/Hoehe der uebergebenen Bilder.
		this.width = pics[0].getWidth();
		this.height = pics[0].getHeight();
		
		parent = p;
		
		//Zu Beginn wird die gesamte Animation durchlaufen
		loop_from = 0;
		loop_to = pics.length-1;
	}
	
	//Zeichnet die (Einzel-)Bilder.
	public void drawObjects(Graphics g) 
	{
		g.drawImage(pics[currentpic], (int) x, (int) y, null);
	}

	public void doLogic(long delta) 
	{	
		//Abhaenig von der Zeit des letzten Schleifendurchlaufs wird die Animationsdauer berechnet
		animation = animation + (delta/1000000);
		
		if (animation > delay) 
		{
			animation = 0;
			computeAnimation();
		}		
	}

	private void computeAnimation()
	{   
		//Fuer die eigentliche Animation. Die Bilder werden nacheinander durchlaufen.
		currentpic++;

		if(currentpic>loop_to)
			currentpic = loop_from;		
	}
	
	//Selbsterklaerend
	public void setLoop(int from, int to)
	{
		loop_from = from;
		loop_to   = to;
		currentpic = from;
	}
	
	//Bewegung des Objektes. Wenn dx bzw. dy = 0 sind, muss nichts bewegt werden. 
	//Andernfalss werden x- und y-Koordinate des Objektes um dx,dy erhoeht.
	public void move(long delta) 
	{	
		if(dx!=0)
			x = x + dx*(delta/1e9);
    
		if(dy!=0)
			y = y + dy*(delta/1e9);   
	}
	
	//Getter- und Setter-Methoden fuer die Bewegung.
	public double getHorizontalSpeed() 
	{
		return dx;
	}

	public void setHorizontalSpeed(double dx) 
	{
		this.dx = dx;
	}

	public double getVerticalSpeed() 
	{
		return dy;
	}

	public void setVerticalSpeed(double dy) 
	{
		this.dy = dy;
	}

	public abstract boolean collidedWith(Objekt s);
	
  
	public int getLoopFrom()
  	{
	  	return loop_from;
  	}
  
  	public int getLoopTo()
  	{
	  	return loop_to;
  	}
  
}
