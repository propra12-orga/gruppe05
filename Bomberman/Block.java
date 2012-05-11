package Bomberman;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Block
{	
	public Block()
	{

	}

	public abstract Object getBlock();
	
	public abstract String blockType();
	
	public abstract Image getImage();
}
