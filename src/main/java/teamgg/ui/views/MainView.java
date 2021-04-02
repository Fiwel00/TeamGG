package teamgg.ui.views;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import errorhandling.TeamGGException;

public class MainView extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 330958157071004001L;
	
	public MainView(int width, int height) throws TeamGGException {
		setSize(width, height);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new ProfileView(), BorderLayout.NORTH);
		add(new MatchInfoView(), BorderLayout.CENTER);
		
	}
}
