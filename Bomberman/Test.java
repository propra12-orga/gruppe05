package Bomberman;

import javax.swing.JFrame;

public class Test {

	public static void main(String[] args) 
	{
		
		JFrame frame = new JFrame();
		
		Arena arena = new Arena("large", 1, 70);
		
		Heinz heinz = new Heinz();
		
		frame.setResizable(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(heinz);
		
		heinz.add(arena);
		
		frame.addKeyListener(heinz);
		
		System.out.print(arena.getSize());
		frame.pack();
		
		frame.setVisible(true);
		
		heinz.start();
	}

}
