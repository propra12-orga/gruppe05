package Bomberman;

import java.awt.Image;
import java.awt.Toolkit;


public class Road extends Block
{
	private Image img;
	
	public Road()
	{
		img = Toolkit.getDefaultToolkit().getImage("images/block_road.jpg");
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
		return "road";
	}
}
