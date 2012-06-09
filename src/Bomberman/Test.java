package Bomberman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test {

	public static void main(String[] args) 
	{
		final JFrame frame = new JFrame();
		frame.setSize(500, 500);
		
		JPanel pan = new JPanel();

		
		frame.add(pan);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		final JButton startButton = new JButton("Spiel starten");
		final JButton endButton = new JButton("Beenden");
		
		pan.add(startButton);
		pan.add(endButton);
		
		frame.setVisible(true);
		
		
		
		ActionListener act = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==startButton){
					frame.setVisible(false);
					Arena arena = new Arena(760,600);
					
				}
				
				
				if(e.getSource()==endButton){
					System.exit(0);
				}
			}
		};
		startButton.addActionListener(act);
		endButton.addActionListener(act);
		

	}

}
