package Bomberman;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class DestructiveBlock extends Block
{
	private Image img;
	
	public DestructiveBlock()
	{
		img = Toolkit.getDefaultToolkit().getImage("images/block_noSolid.jpg");
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
		return "destroyable";
	}
}
