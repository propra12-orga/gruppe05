package Bomberman;
import java.awt.Graphics;

import javax.swing.JFrame;


public class Spielfeld extends JFrame {
	
	public Spielfeld(){
		
		setTitle("BomberHeinz");
		
		//Funktion zum beenden und schliessen des Fensters
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//Groesse des Fensters
		setSize(400, 400);
		
		
		//Sichtbarkeit des Fensters
		setVisible(true);
		
		
		
	}
	
	
}
