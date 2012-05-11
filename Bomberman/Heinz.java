package Bomberman;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Heinz extends JPanel implements Runnable, KeyListener {

	//Setzen der Koordinaten
	private int x=0, xPos=0;
	private int y=0, yPos=0;
	
	//Variable Tastendruckabfrage
	boolean hoch;
	boolean runter;
	boolean rechts;
	boolean links;
	boolean fire;
	
	//Konstruktor
	public Heinz(){

	}
	
	//zeichenMethode
	//zeichnet Rechteck(hier quadrat)
	public void paint(Graphics quadrat){
		super.paint( quadrat );
		quadrat.drawRect(xPos, yPos, 40, 40);
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
	            
	        }
	        if(runter==true && yPos<322){
	        	y=1;
	            x=0;
	        	
	        }
	        if(links==true && xPos>0){
	        	x=-1;
	            y=0;
	        	
	        }
	        if(rechts==true && xPos<343){
	        	x=1;
	            y=0;
	        	
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
	        System.out.println("x="+ xPos+" y="+yPos);
	        System.out.println(hoch + " " + runter + " "+ links + " " + rechts);
			
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
