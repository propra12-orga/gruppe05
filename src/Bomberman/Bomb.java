package Bomberman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class Bomb extends Objekt implements ActionListener
{
	Timer timer;
	
	public Bomb(BufferedImage[] i, double x, double y, long delay, Arena p)
	{
		super(i, x, y, delay, p);
		
		timer = new Timer(2000, this);
		timer.start();
	}

	public boolean collidedWith(Objekt s) 
	{
		return false;
	}
	
	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource().equals(timer))
		{
			remove = true;
			timer.stop();
		}
	}
}
