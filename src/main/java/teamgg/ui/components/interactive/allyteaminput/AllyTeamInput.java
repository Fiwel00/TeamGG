package teamgg.ui.components.interactive.allyteaminput;

import java.awt.Dimension;
import javax.swing.JTextField;

public class AllyTeamInput extends JTextField {
    
    
    private static final int WIDTH = 140;
	private static final int HEIGHT = 80;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AllyTeamInput () {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		getDocument().addDocumentListener(new AllyTeamInputAL());
	}
}
