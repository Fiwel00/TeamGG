package teamgg.ui.components.interactive.summonerinput;

import java.awt.Dimension;

import javax.swing.JTextField;

public class SummonerInput extends JTextField{

	private static final int WIDTH = 140;
	private static final int HEIGHT = 24;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SummonerInput () {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		getDocument().addDocumentListener(new SummonerInputAL());
	}

}
