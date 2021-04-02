package teamgg.ui.components.interactive.loadprofile;

import javax.swing.JButton;

public class LoadProfileButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6854227976519662996L;

	public LoadProfileButton() {
		super("Load Profile");
		setVisible(true);
		addActionListener(new LoadProfileAL());
		
	}

}
