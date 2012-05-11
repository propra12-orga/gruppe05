package Bomberman;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class SolidBlock extends Block
{	
	private Image img;
	
	public SolidBlock()
	{
		img = Toolkit.getDefaultToolkit().getImage("images/block_solid.jpg");
	}
	
	public Object getBlock()
	{
		return this;
	}
	
	public Image getImage()
	{
		return img;
	}
	
	public String blockType()
	{
		return "solid";
	}
}
