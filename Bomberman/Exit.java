package Bomberman;

import java.awt.Image;
import java.awt.Toolkit;


public class Exit extends Block
{
	private Image img;
	
	public Exit()
	{
		img = Toolkit.getDefaultToolkit().getImage("images/block_exit.jpg");
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
		return "exit";
	}
}
