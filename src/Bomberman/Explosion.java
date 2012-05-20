package bomberman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class Explosion extends Objekt
{
	
	public Explosion(BufferedImage[] i, double x, double y, long delay, Arena p)
	{
		super(i, x, y, delay, p);
	}

	public boolean collidedWith(Objekt s) 
	{
		return false;
	}
}
