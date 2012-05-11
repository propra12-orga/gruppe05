package Bomberman;

import javax.swing.JFrame;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame frame = new JFrame();
		
		Arena arena = new Arena("verylarge", 1, 70);
		
		frame.setResizable(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(arena);
		
		System.out.print(arena.getSize());
		frame.pack();
		
		frame.setVisible(true);
	}

}
