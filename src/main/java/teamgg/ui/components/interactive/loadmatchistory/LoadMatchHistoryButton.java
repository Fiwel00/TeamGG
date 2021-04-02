package teamgg.ui.components.interactive.loadmatchistory;

import javax.swing.JButton;

public class LoadMatchHistoryButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoadMatchHistoryButton() {
		super("Load Match History");
		setVisible(true);
		addActionListener(new LoadMatchHistoryAL());
	}
}
