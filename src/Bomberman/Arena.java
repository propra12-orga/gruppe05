package Bomberman;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ListIterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Arena extends JPanel implements Runnable, KeyListener, ActionListener
{

	JFrame frame;
	
	int w, h;
	
	long delta = 0;
	long last = 0;
	long fps = 0;
	
	Player human;
	Vector<Objekt> actors;
	Vector<Objekt> painter;
	
	BufferedImage[] humanImages;
	BufferedImage[] solidBlocks;
	BufferedImage background;
  
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	
	int speed = 140;
	
	public Arena(int w, int h)
	{
		this.w = w;
		this.h = h;
		
		init();
		
		this.setPreferredSize(new Dimension(w,h));
		
		frame = new JFrame("Bomber-Heinz");
		frame.setLocation(100,100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.addKeyListener(this);
		frame.pack();
		frame.setVisible(true);
		
		
		Thread th = new Thread(this);
		th.start();
	}

	private void init() {

		last = System.nanoTime();

		humanImages = loadPics("images/player/p1/bomber2.png", 16);
		solidBlocks = loadPics("images/block_solid.jpg", 1);
		background = loadPics("images/background.jpg",1) [0];
		
		actors  = new Vector<Objekt>();
		painter = new Vector<Objekt>();
		
		human  = new Player(humanImages, 45, 45, 200, this);
		
		createEdge();
		classicField();
		
		actors.add(human);			
	}
	
	public void run() 
	{
		while(frame.isVisible())
		{

			computeDelta();
			
			checkKeys();
			doLogic();
			moveObjects();
			cloneVectors();
			
			repaint();
			
		  try 
		  {
				Thread.sleep(10);
		  } catch (InterruptedException e) {}	
		}		
	}

	@SuppressWarnings("unchecked")
	private void cloneVectors()
	{
		painter = (Vector<Objekt>) actors.clone();
	}
	
	
	private void moveObjects() 
	{		
		for(ListIterator<Objekt> it = actors.listIterator();it.hasNext();)
		{
			Objekt r = it.next();
			r.move(delta);
		}				
	}

	private void doLogic() 
	{		
		for(ListIterator<Objekt> it = actors.listIterator();it.hasNext();)
		{
			Objekt r = it.next();
			r.doLogic(delta);
		}
		
		for (int i = 0; i < actors.size(); i++)
		{
			for(int j = i + 1; j < actors.size(); j++)
			{
				Objekt ob1 = actors.elementAt(i);
				Objekt ob2 = actors.elementAt(j);
				
				ob1.collidedWith(ob2);
			}
		}
	}
	
	private void checkKeys()
	{			
		if(up && !right && !left)
		{
			human.setVerticalSpeed(-speed);
		}
		
		if(down && !right && !left)
		{
			human.setVerticalSpeed(speed);
		}
		
		if(right && !up && !down)
		{
			human.setHorizontalSpeed(speed);
		}
		
		if(left && !up && !down)
		{
			human.setHorizontalSpeed(-speed);
		}
		
		if(!up&&!down)
		{
			human.setVerticalSpeed(0);
		}
		
		if(!left&&!right)
		{
			human.setHorizontalSpeed(0);
		}		
	}
	
	private void computeDelta() 
	{
		delta = System.nanoTime() - last;
		last = System.nanoTime();
		fps = ((long) 1e9)/delta;		
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		g.drawImage(background, 0, 0, this);
		
		for (ListIterator<Objekt> it = painter.listIterator(); it.hasNext();) 
		{
			Objekt r = it.next();
			r.drawObjects(g);
		}		
	}
		
	private BufferedImage[] loadPics(String path, int pics)
	{
		
		BufferedImage[] anim = new BufferedImage[pics];
		BufferedImage source = null;
		
		URL pic_url = getClass().getClassLoader().getResource(path);

		try 
		{
			source = ImageIO.read(pic_url);
		} catch (IOException e) {}
		
		for(int x=0;x<pics;x++)
		{
			anim[x] = source.getSubimage(x*source.getWidth()/pics, 0, source.getWidth()/pics, source.getHeight());
		}
		
		return anim;
	}

	public void keyPressed(KeyEvent e) 
	{	
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			up = true;
		}

		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			down = true;
		}

		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			left = true;
		}

		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			right = true;
		}		
	}

	public void keyReleased(KeyEvent e) 
	{		
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			up = false;
		}

		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			down = false;
		}

		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			left = false;
		}

		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			right = false;
		}
	}

	public void keyTyped(KeyEvent e)
	{
		
	}

	public void actionPerformed(ActionEvent e) 
	{
		
	}

	private void createEdge()
	{	
		for(int i = 0; i < w; i = i + 40)
			for (int j = 0; j < h; j = j + 40)
			{
				if(i == 0 || i == w - 40)
				{
					SolidBlock solid = new SolidBlock(solidBlocks, i, j, 1000, this);
					actors.add(solid);
				}
				else if(j == 0 || j == h - 40)
				{
					SolidBlock solid = new SolidBlock(solidBlocks, i, j, 1000, this);
					actors.add(solid);
				}
			}
	}
	
	//Folgender Code muss noch implementiert werden.
	
	/*
	private void createExit()
	{
		
		int exit_x;
		int exit_y;
		//Der Ausgang soll zufaellig platziert werden. Dabei betraegt der Abstand zum Arena-Rand mind. 2 Bloecke.
		exit_x = (int)(Math.random() * (w - 6*40) + 3*40);
		exit_y = (int)(Math.random() * (h - 6*40) + 3*40);
		
		Exit solid = new Exit(solidBlocks, exit_x, exit_y, 1000, this);
		actors.add(solid);
		
		for(int i = 0; i < actors.size(); i++)
			if(solid.collidedWith(actors.elementAt(i)))
			{
				
			}
	}
	/*
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
	*/
	
	private void classicField()
	{
		for(int i = 80; i < (w - 80); i = i + 80)
			for(int j = 80; j < (h - 80); j = j + 80)
			{
				SolidBlock solid = new SolidBlock(solidBlocks, i, j, 1000, this);
				actors.add(solid);
			}				
	}
}
