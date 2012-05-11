package Bomberman;
import java.awt.Component;

import javax.swing.JFrame;


public class Start{

	/**
	 * @param args
	 */
			
	public static void main(String[] args) {
		
//		Spieler sp = new Spieler();
		Heinz h = new Heinz();
		Spielfeld feld = new Spielfeld();
		feld.add(h);
		h.start();
		feld.addKeyListener(h);
		
		}
}
