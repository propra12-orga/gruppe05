package Bomberman;
import java.awt.*;

import javax.swing.*;

public class Arena extends JPanel
{
	private Block [][] arena;
	private int height, width, style;
	private double chance;

	public Arena(String size, int style, double chance)
	{	
		if(size == "small") 
		{
			this.height = 9;
			this.width = 13;
		}
		else if(size == "medium")
		{
			this.height = 13;
			this.width = 15;
		}
		else if(size == "large")
		{
			this.height = 13;
			this.width = 19;
		}
		else if(size == "verylarge")
		{
			this.height = 15;
			this.width = 23;
		}
		
		//erstellt ein Spielfeld mit der Hoehe height und der Breite width;
		arena = new Block[width][height];
		
		this.style = style;
		
		this.chance = 1 - chance/100;
		
		setPreferredSize(new Dimension(width * 40, height * 40));
		
		initField();	
	}	
	
	public Arena(int width, int height, int style, double chance)
	{	
		this.height = height;
		this.width = width;
		
		//erstellt ein neues Spielfeld mit der Hoehe height und der Breite width;
		arena = new Block[width][height];
		
		this.style = style;	
		
		this.chance = 1 - chance/100;

		setPreferredSize(new Dimension(width * 40, height * 40));
		
		initField();
	}
	
	private void initField()
	{
		createEdge();
		
		if(style == 1)
			classicField();
		
		createExit();
		
		//createNonSolid();
	}
	
	private void createEdge()
	{
		for(int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
			{
				if(i == 0 || i == width - 1)
					arena[i][j] = new SolidBlock();
				else if(j == 0 || j == height - 1)
					arena[i][j] = new SolidBlock();
				else
					arena[i][j] = new Road();
			}
	}
	
	private void createExit()
	{
		int exit_x;
		int exit_y;
		//Der Ausgang soll zufaellig platziert werden. Dabei betraegt der Abstand zum Arena-Rand mind. 2 Bloecke.
		do
		{
		exit_x = (int)(Math.random() * (width - 6) + 3);
		exit_y = (int)(Math.random() * (height - 6) + 3);
		
		}while(arena[exit_x][exit_y].blockType() == "solid");
		
		arena[exit_x][exit_y] = new Exit();	
	}
	
	private void createNonSolid()
	{
		double possible;
		
		for(int i = 1; i < width - 1; i++)
			for(int j = 3; j < height - 3; j++)
			{
				possible = Math.random();
				
				if(arena[i][j].blockType() != "solid" && possible > chance)
					arena[i][j] = new DestructiveBlock();
			}
		
		for(int i = 3; i < width - 3; i++)
		{
			possible = Math.random();
			
			if(possible > chance)
			{
				arena[i][1] = new DestructiveBlock();
				arena[i][height - 2] = new DestructiveBlock();
				
				if(arena[i][2].blockType() != "solid" || arena[i][height - 3].blockType() != "solid")
				{
					arena[i][2] = new DestructiveBlock();
					arena[i][height - 3] = new DestructiveBlock();
				}
			}
		}
	}
	
	private void classicField()
	{
		for(int i = 2; i < (width-2); i = i + 2)
			for(int j = 2; j < (height-2); j = j + 2)
				arena[i][j] = new SolidBlock();
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
			{
					g.drawImage(arena[i][j].getImage(), 40*i, 40*j, this);
			}
	}
	
	public int getBreite()
	{
		return width * 40;
	}
	
	public int getHoehe()
	{
		return height * 40;
	}
}
