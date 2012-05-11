package Bomberman;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Bombe extends JPanel implements Runnable {

	//variable für Thread
	boolean ticking;
	
	//Koordinaten festlegen
	private int xPos;
	private int yPos;
	
	//fester Radius der Bombe(Wert noch besprechen)
//	private final int radius=2;
	
	public Bombe(int xPos, int yPos){
		this.xPos=xPos;
		this.yPos=yPos;
	}
	
	public Bombe(){
		
	}
	
	public void paint(Graphics kreis){
		super.paint( kreis );
		kreis.drawOval(xPos, yPos, 20, 20);
		kreis.setColor(Color.RED);
	}
	
	public void start(){
		Thread t = new Thread(this);
		t.start();
		ticking = true;
	}
	
	public void stop(){
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
	    while(ticking)
	    {
	      try
	      {
	    	System.out.println("boooooooooooooooooooooooooooom");		
	    	Thread.sleep(400);
	        repaint();
	        ticking=false;
	      }
	    
	      catch(InterruptedException ex)
	      {
	    	  //nichts
	      }
	      Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	    }
	}
}