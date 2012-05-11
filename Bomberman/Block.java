package Bomberman;

import java.awt.Image;


public abstract class Block
{	
	public Block()
	{

	}

	public abstract Object getBlock();
	
	public abstract String blockType();
	
	public abstract Image getImage();
}
