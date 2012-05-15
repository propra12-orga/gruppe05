package Bomberman;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Heinz extends JPanel implements Runnable, KeyListener {

	//Setzen der Koordinaten
	private int x=2, xPos=0, xMoveR = 0, xMoveL = 0;
	private int y=0, yPos=0, yMoveU = 0, yMoveD = 0;
	
	//Variable Tastendruckabfrage
	boolean hoch;
	boolean runter;
	boolean rechts;
	boolean links;
	boolean fire;
	
	Image[] img;
	int width, height, image;
	
	//Konstruktor
	public Heinz(int width, int height)
	{
		//this.setPreferredSize(new Dimension(width, height));
		
		this.width = width;
		this.height = height;
		
		getImages();
	}
	
	public Heinz(Dimension dim)
	{
		//this.setPreferredSize(new Dimension(width, height));
		
		this.width = (int)dim.getWidth();
		this.height = (int)dim.getHeight();
	}	
	
	public Heinz(String dim)
	{
		//this.setPreferredSize(new Dimension(width, height));
		
		if(dim == "large")
		{
			this.width = 19 * 40;
			this.height = 13 * 40;
		}
	}
	
	public void getImages()

	{
		//Lade Images
		img = new Image[12];
		
		img[0] = Toolkit.getDefaultToolkit().getImage("images/player/p1/bomber1_left.png");
		img[1] = Toolkit.getDefaultToolkit().getImage("images/player/p1/bomber1_left_move.png");
		img[2] = Toolkit.getDefaultToolkit().getImage("images/player/p1/bomber1_left_move1.png");
		img[3] = Toolkit.getDefaultToolkit().getImage("images/player/p1/bomber1_right.png");
		img[4] = Toolkit.getDefaultToolkit().getImage("images/player/p1/bomber1_right_move.png");
		img[5] = Toolkit.getDefaultToolkit().getImage("images/player/p1/bomber1_right_move1.png");
		img[6] = Toolkit.getDefaultToolkit().getImage("images/player/p1/bomber1_front.png");
		img[7] = Toolkit.getDefaultToolkit().getImage("images/player/p1/bomber1_front_move.png");
		img[8] = Toolkit.getDefaultToolkit().getImage("images/player/p1/bomber1_front_move1.png");
		img[9] = Toolkit.getDefaultToolkit().getImage("images/player/p1/bomber1_back.png");
		img[10] = Toolkit.getDefaultToolkit().getImage("images/player/p1/bomber1_back_move.png");
		img[11] = Toolkit.getDefaultToolkit().getImage("images/player/p1/bomber1_back_move1.png");
	}
	
	private void animate()
	{

		if(xMoveR < 30 && x == 1)
			image = 0;
		else if((xMoveR > 30 && xMoveR < 61) && x == 1)
			image = 1;
		else if((xMoveR > 60 && xMoveR < 91) && x == 1)
			image = 0;
		else if((xMoveR > 90 && xMoveR < 121) && x == 1)
			image = 2;
		else if(xMoveR > 120)
			xMoveR = 0;
		
		if((xMoveL < 30 && x == -1))
			image = 3;
		else if((xMoveL > 30 && xMoveL < 61) && x == -1)
			image = 4;
		else if((xMoveL > 60 && xMoveL < 91) && x == -1)
			image = 3;
		else if((xMoveL > 90 && xMoveL < 121) && x == -1)
			image = 5;
		else if(xMoveL > 120)
			xMoveL = 0;
		
		if((yMoveD < 30 && y == 1) || (runter == false && links != true && rechts != true && hoch != true))
			image = 6;
		else if((yMoveD > 30 && yMoveD < 61) && y == 1)
			image = 7;
		else if((yMoveD > 60 && yMoveD < 91) && y == 1)
			image = 6;
		else if((yMoveD > 90 && yMoveD < 121) && y == 1)
			image = 8;
		else if(yMoveD > 120)
			yMoveD = 0;
		
		if(yMoveU < 30 && y == -1)
			image = 9;
		else if((yMoveU > 30 && yMoveU < 61) && y == -1)
			image = 10;
		else if((yMoveU > 60 && yMoveU < 91) && y == -1)
			image = 9;
		else if((yMoveU > 90 && yMoveU < 121) && y == -1)
			image = 11;
		else if(yMoveU > 120)
			yMoveU = 0;
	}
	//zeichenMethode
	//zeichnet Rechteck(hier quadrat)
	public void paint(Graphics g){
		super.paint( g );
		
		animate();
		
		g.drawImage(img[image], xPos, yPos, this);
		//g.fillRect(xPos, yPos, 40, 40);
	}
	
	//die breaks eventuell ueberfluessig
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
        	hoch=true;
//        	y=1;
//        	x=0;
//            System.out.println("UP");
            break;
        case KeyEvent.VK_DOWN:
            runter=true;
//        	y=-1;
//            x=0;
//        	System.out.println("DOWN");
            break;
        case KeyEvent.VK_LEFT:
            links=true;
//        	x=-1;
//            y=0;
//        	System.out.println("LEFT");
            break;
        case KeyEvent.VK_RIGHT:
            rechts=true;
//        	x=1;
//            y=0;
//        	System.out.println("RIGHT");
            break;
        case KeyEvent.VK_SPACE:
        	fire=true;
        }
//		xPos+=x;
//		yPos+=y;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
        	hoch=false;
//        	y=1;
//        	x=0;
//            System.out.println("UP");
            break;
        case KeyEvent.VK_DOWN:
            runter=false;
//        	y=-1;
//            x=0;
//        	System.out.println("DOWN");
            break;
        case KeyEvent.VK_LEFT:
            links=false;
//        	x=-1;
//            y=0;
//        	System.out.println("LEFT");
            break;
        case KeyEvent.VK_RIGHT:
            rechts=false;
//        	x=1;
//            y=0;
//        	System.out.println("RIGHT");
            break;
        case KeyEvent.VK_SPACE:
        	fire=false;
		}
		
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//die Start Methode für den Thread
	//ruft selber die run Methode auf
	public void start(){
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
	    while(true)
	    {
	      try
	      {
	    	x=0;
	    	y=0;
	        if(hoch==true && yPos>0){
	        	y=-1;
	        	x=0;
	            
	        	yMoveU++;
	        }
	        if(runter==true && yPos<height){
	        	y=1;
	            x=0;
	        	
	            yMoveD++;
	        }
	        if(links==true && xPos>0){
	        	x=-1;
	            y=0;

	            xMoveL++;
	        }
	        if(rechts==true && xPos<width){
	        	x=1;
	            y=0;
	            
	            xMoveR++;
	        	
	        }
//	        if(!rechts && !links && !hoch && !runter){
//	        	x=0;
//	        	y=0;
//	        }
	        if(fire==true){
	        	System.out.println("Bombe");
	        	Bombe b= new Bombe();
	        	b.start();
	        	
	        }
	        xPos+=x;
			yPos+=y;
			
			//nur zum Pruefen
			
	        //Threadpause in Millisek
	        Thread.sleep(5);
	       
	        //zeichnet allneu
	        repaint();
	        
	      }
	    
	      catch(InterruptedException ex)
	      {
	    	  
	      }
	      Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	    }
	}	
}
