package teamgg.ui.components.interactive.summonerinput;

import javax.swing.JTextField;

public class SummonerInput extends JTextField{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SummonerInput () {
		setSize(200, 80);
		
		getDocument().addDocumentListener(new SummonerInputAL());
	}

}
