package Bomberman;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ListIterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Arena extends JPanel implements Runnable, KeyListener
{

	JFrame frame;
	
	int w, h;
	
	//Diese 3 longs speichern Zeitwerte etc., damit das Spiel (relativ) fluessig laeuft
	long delta = 0;
	long last = 0;
	long fps = 0;
	
	//Speichert Positionen der Bloecke zur Weiterverarbeitung
	int [][] bloecke;
	
	//Menschl. Spielfigur
	Player human;
	Player human2;
	
	//Wir arbeiten mit Vectoren zur besseren Aufzaehlung, Zeichnen etc.
	Vector<Objekt> actors;
	Vector<Objekt> painter;
	
	//Speichert Bilddateien
	BufferedImage[] humanImages;
	BufferedImage[] solidBlocks;
	BufferedImage[] exitBlock;
	BufferedImage[] bomb;
	BufferedImage[] road;
	BufferedImage[] explosion;
	BufferedImage win_logo;
	//BufferedImage background;
  
	//Speichert Tastendruecke
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	boolean setAble = true;
	boolean layBomb;
	

	boolean up2;
	boolean down2;
	boolean left2;
	boolean right2;
	boolean setAble2 = true;
	boolean layBomb2;
	
	
	boolean hasWin = false;
	
	//Setzt die Geschwindigkeit der Spielfiguren
	int speed = 100;
	int speed2 =100;
	
	int currentBombs = 0;
	int currentBombs2 = 0;
	
	public Arena(int w, int h)
	{
		//Speichert Breite und Hoehe der Arena
		this.w = w;
		this.h = h;
		
		//Initialisierung
		init();
		
		//Das JPanel bekommt die eingegebene Groesse
		this.setPreferredSize(new Dimension(w,h));
		
		//Die Arena wird in einem eigenen Frame angezeigt
		frame = new JFrame("Bomber-Heinz");
		frame.setLocation(100,100);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(this);
		frame.addKeyListener(this);
		frame.pack();
		frame.setVisible(true);
		
		//Startet die Spielschleife
		Thread th = new Thread(this);
		th.start();
	}

	private void init() 
	{
		//Speichert die Systemzeit (spaeter Ermitteln, wie lange ein Schleifendurchlauf gedauert hat
		last = System.nanoTime();
		
		//es werden soviele 40x40 Bloecke erstellt, wie die Arena gross ist
		bloecke = new int[w / 40][h / 40];

		//Laedt Bilddaten
		humanImages = loadPics("./images/player/p1/bomber2.png", 16);
		solidBlocks = loadPics("./images/block_solid.jpg", 1);
		exitBlock = loadPics("./images/block_exit.jpg", 1);
		bomb = loadPics("./images/bomb/bombe1.gif", 5);
		road = loadPics("./images/block_road.jpg", 1);
		explosion = loadPics("./images/bomb/explosion/explosion_center.png", 4);
		win_logo = loadPics("./images/win_logo.png", 1) [0];
		//background = loadPics("images/background.jpg",1) [0];
		
		//Initialisiert die Vector-Objekte. Actors fuer Ligik und Bewegung und painters zum Zeichnen
		actors  = new Vector<Objekt>();
		painter = new Vector<Objekt>();
		
		//Erzeugt den menschl. Spieler
		human  = new Player(humanImages, 45, 45, 200, this);
		human2 = new Player(humanImages, 45*15.2, 45*11.8, 200, this); 
		//Erzeugt das Spielfeld 
		createEdge();
		classicField();
		createExit();
		
		//actors bekommt den menschl. Spieler.
		actors.add(human);	
		actors.add(human2);
	}
	
	public void run() 
	{
		//Die Spielschleife wird nur ausgefuehrt, wenn das Arena-Frame auch wirklich angezeigt wird
		while(frame.isVisible())
		{
			//Berechnet Zeitintervall etc. fuer fluessigen Spielablauf
			computeDelta();
			
			//Prueft Tastertureingaben, fuehrt Logiken (Kollisionen etc.) aus,
			//bewegt Objekte und klont actors-Vektoren
			if (fps>40)
			{
			checkKeys();
			checkKeys2();
			doLogic();
			moveObjects();
			cloneVectors();
			
			//Nachdem alle Befehle ausgefuehrt wurden, zeichnet sich das veraenderte Spielfeld
			repaint();
			}
			//Nach jedem Schleifendurchlauf wartet Java 3 Millisekunden. (Auch fuer fluessigen Spiellauf)
			try 
			{
				Thread.sleep(5);
			} catch (InterruptedException e) {}	
		}		
	}

	//Klont die actors-Vektoren (Also alle Objekte) und schreibt sie in painter.
	//Wir benutzen die Vektoren painter nur zum Zeichnen. Wuerde man actors
	//zum zeichnen benutzen, koennten Laufzeitfehler auftreten, da in actors auch
	//Logik ausgefuehrt wird
	@SuppressWarnings("unchecked")
	private void cloneVectors()
	{
		painter = (Vector<Objekt>) actors.clone();
	}
	
	
	private void moveObjects() 
	{		
		//Jedes Objekt aus actors wird aufgerufen. Solange ein Vektor noch einen
		//Nachfolger hat wird die Schleife ausgefuehrt. Jedes Vektor-Objekt ruft seine move-Methode auf.
		for(ListIterator<Objekt> it = actors.listIterator();it.hasNext();)
		{
			Objekt r = it.next();
			r.move(delta);
		}				
	}

	private void doLogic() 
	{	
		//Boolscher Wert um zu pruefen, ob Objekte kollidiert sind.
		boolean collide;
		Explosion explo;
		
		//Jedes Objekt aus actors wird aufgerufen. Solange ein Vektor noch einen
		//Nachfolger hat wird die Schleife ausgefuehrt. Jedes Vektor-Objekt ruft seine logic-Methode auf.
		for(ListIterator<Objekt> it = actors.listIterator();it.hasNext();)
		{
			Objekt r = it.next();
			r.doLogic(delta);
			
			if(r.remove)
			{	
				it.remove();
				currentBombs--;
				currentBombs2--;
				
			}
		}
		
		//Prueft je zwei Objekte aus actors auf Kollision miteinander.
		for (int i = 0; i < actors.size(); i++)
		{
			for(int j = i + 1; j < actors.size(); j++)
			{
				Objekt ob1 = actors.elementAt(i);
				Objekt ob2 = actors.elementAt(j);
				
				//Kollidieren zwei Objekte, wird collide true gesetzt.
				collide = ob1.collidedWith(ob2);

				
				//Prueft, ob der Spieler human auf den Ausgang gelaufen ist. Wenn ja, ist das Spiel gewonnen.
				if(ob1 instanceof Player && ob2 instanceof Exit || ob1 instanceof Exit && ob2 instanceof Player)
					if(collide)
					{
						hasWin = true;
					}
			}
		}
	}
	
	//Prueft die Tastertureingabe. Wurde eine Pfeiltaste gedrueckt, so wird die Geschwindigkeit des Spieler
	//human entsprechend gesetzt. Wurde keine Taste gedrueckt, so wird die Geschwindigkeit des Spielers
	//human auf 0 gesetzt.
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
		
		if(layBomb)
		{
			double bombX, bombY;
			
			bombX = 40 * ((int)human.getCenterX() / 40) + 5;
			bombY = 40 * ((int)human.getCenterY() / 40) + 5;
			
			Bomb bombe = new Bomb(bomb, bombX, bombY, 500, this);
			actors.add(bombe);
			
			currentBombs++;
			
			layBomb = false;
		}
//		else
//			layBomb = false;
	}
	

	//Prueft die Tastertureingabe. Wurde eine Pfeiltaste gedrueckt, so wird die Geschwindigkeit des Spieler
	//human entsprechend gesetzt. Wurde keine Taste gedrueckt, so wird die Geschwindigkeit des Spielers
	//human auf 0 gesetzt.
	private void checkKeys2()
	{			
		if(up2 && !right2 && !left2)
		{
			human2.setVerticalSpeed(-speed2);
		}
		
		if(down2 && !right2 && !left2)
		{
			human2.setVerticalSpeed(speed2);
		}
		
		if(right2 && !up2 && !down2)
		{
			human2.setHorizontalSpeed(speed2);
		}
		
		if(left2 && !up2 && !down2)
		{
			human2.setHorizontalSpeed(-speed2);
		}
		
		if(!up2&&!down2)
		{
			human2.setVerticalSpeed(0);
		}
		
		if(!left2&&!right2)
		{
			human2.setHorizontalSpeed(0);
		}
		
		if(layBomb2 && currentBombs2 < human2.getMaxBombs())
		{
			double bombX, bombY;
			
			bombX = 40 * ((int)human2.getCenterX() / 40) + 5;
			bombY = 40 * ((int)human2.getCenterY() / 40) + 5;
			
			Bomb bombe2 = new Bomb(bomb, bombX, bombY, 500, this);
			actors.add(bombe2);
			
			currentBombs2++;
			
			layBomb2 = false;
		}
		else
			layBomb2 = false;
	}

	
	//Berechnet, wie lange der letzte Schleifendurchlauf gedauert hat. Daraus wird die Framerate FPS errechnet.
	private void computeDelta() 
	{
		delta = System.nanoTime() - last;
		last = System.nanoTime();
		fps = ((long) 1e9)/delta;		
	}
	
	//Zeichenkomponente
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		//Hat der Spieler gewonnen (den Ausgang betreten) zeichnet Graphics nichts neues mehr. Nur eine
		//Meldung (You Win!) wird ausgegeben.
		if(hasWin)
		{	
			this.setBackground(Color.black);
			g.drawImage(win_logo, (w / 2) - 200, 100, this);
			return;
		}	
				
		//Zeichnet den Hintergrund
		//g.drawImage(background, 0, 0, this);

		//Jedes Objekt aus painters ruft seine draw-Methode auf.
		for (ListIterator<Objekt> it = painter.listIterator(); it.hasNext();) 
		{
			Objekt r = it.next();
			r.drawObjects(g);
		}

	}
		
	//Laden der Bilddateien
	private BufferedImage[] loadPics(String path, int pics)
	{
		//Bilder werden in einem Array gespeichert (fuer Animationen)
		BufferedImage[] anim = new BufferedImage[pics];
		BufferedImage source = null;
		
		//Die URL / Der Pfad der zu ladenden Bilddatei.
		//URL pic_url = ;
		File file = new File(path);
		
		try 
		{
			
			
			//Liest die Datei ein
			source = ImageIO.read(file);
		} catch (IOException e) {}
		
		for(int x=0;x<pics;x++)
		{
			//Aus einem Bild (Filmstreifen) werden mehrere (quadratische) Teilbilder herausgeschnitten
			//fuer moegliche Animationen
			anim[x] = source.getSubimage(x*source.getWidth()/pics, 0, source.getWidth()/pics, source.getHeight());
		}
		
		return anim;
	}

	//Prueft KeyEvents. 
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

		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			left = true;
		}

		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			right = true;
		}		
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE && !layBomb)
		{
			if(setAble)
				layBomb = true;
			
			setAble = false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
			up2 = true;
		}

		if(e.getKeyCode()==KeyEvent.VK_S)
		{
			down2 = true;
		}

		if(e.getKeyCode()==KeyEvent.VK_A)
		{
			left2 = true;
		}

		if(e.getKeyCode()==KeyEvent.VK_D)
		{
			right2 = true;
		}		
		
		if(e.getKeyCode()==KeyEvent.VK_E && !layBomb2)
		{
			if(setAble2)
				layBomb2 = true;
			
			setAble2 = false;
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
		
		if(e.getKeyCode()==KeyEvent.VK_E && !layBomb)
		{
			setAble = true;
			layBomb = false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
			up2 = false;
		}

		if(e.getKeyCode()==KeyEvent.VK_S)
		{
			down2 = false;
		}

		if(e.getKeyCode()==KeyEvent.VK_A)
		{
			left2 = false;
		}

		if(e.getKeyCode()==KeyEvent.VK_D)
		{
			right2 = false;
		}	
		
		if(e.getKeyCode()==KeyEvent.VK_E && !layBomb2)
		{
			setAble2 = true;
			layBomb2 = false;
		}

	}

	public void keyTyped(KeyEvent e)
	{
	}

	//Erzeugt den Spielfeldrand (solide Bloecke)
	private void createEdge()
	{	
		for(int i = 0; i < w; i = i + 40)
			for (int j = 0; j < h; j = j + 40)
			{
				if(i == 0 || i == w - 40)
				{
					SolidBlock solid = new SolidBlock(solidBlocks, i, j, 1000, this);
					actors.add(solid);
					bloecke[i / 40][j / 40] = 1;
				}
				else if(j == 0 || j == h - 40)
				{
					SolidBlock solid = new SolidBlock(solidBlocks, i, j, 1000, this);
					actors.add(solid);
					bloecke[i / 40][j / 40] = 1;
				}
				else
				{
					Road solid = new Road(road, i, j, 1000, this);
					actors.add(solid);
					bloecke[i / 40][j / 40] = 0;
				}
			}
	}
	
	//Erzeugt einen zufaellig gesetzten Ausgang Exit
	private void createExit()
	{
		
		int exit_x;
		int exit_y;
		//Der Ausgang soll zufaellig platziert werden. Dabei betraegt der Abstand zum Arena-Rand mind. 2 Bloecke.
		do
		{
		exit_x = (int)(Math.random() * (w/40 - 6) + 3);
		exit_y = (int)(Math.random() * (h/40 - 6) + 3);
		} while(bloecke[exit_x][exit_y] != 0);
		
		Exit solid = new Exit(exitBlock, exit_x * 40, exit_y * 40, 1000, this);
		actors.add(solid);		
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
	
	//Klassisches Bombermanfeld wird erzeugt.
	private void classicField()
	{
		for(int i = 80; i < (w - 80); i = i + 80)
			for(int j = 80; j < (h - 80); j = j + 80)
			{
				SolidBlock solid = new SolidBlock(solidBlocks, i, j, 1000, this);
				actors.add(solid);
				bloecke[i / 40][j / 40] = 1;
			}				
	}
}
