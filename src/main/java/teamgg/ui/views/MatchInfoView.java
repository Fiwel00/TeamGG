package teamgg.ui.views;

import java.awt.Color;

import javax.swing.JPanel;

import errorhandling.TeamGGException;
import teamgg.ui.components.interactive.loadmatchistory.LoadMatchHistoryButton;
import teamgg.ui.components.matchInfo.MatchGrid;

public class MatchInfoView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MatchGrid matchRelationships;
	private static LoadMatchHistoryButton loadMatchHistoryButton;

	public MatchInfoView () throws TeamGGException {

		setVisible(true);
		setBackground(Color.CYAN);
	
		
		setMatchRelationships(new MatchGrid());
		setLoadMatchHistoryButton(new LoadMatchHistoryButton());
	}
	
	/**
	 * 
	 * @return
	 */
	public static MatchGrid getMatchGrid() {
		return matchRelationships;
	}

	/**
	 * @param matchInfo
	 */
	private void setMatchRelationships(MatchGrid matchInfo) {
		add(matchInfo);
		setMatchRelationshipsStatic(matchInfo);
	}

	/**
	 * 
	 * @param matchInfo
	 */
	private static void setMatchRelationshipsStatic(MatchGrid matchInfo) {
		MatchInfoView.matchRelationships = matchInfo;
	}


	/**
	 * 
	 * @return
	 */
	public static LoadMatchHistoryButton getLoadMatchHistoryButton() {
		return loadMatchHistoryButton;
	}

	/**
	 * 
	 * @param loadMatchHistoryButton
	 */
	private void setLoadMatchHistoryButton(LoadMatchHistoryButton loadMatchHistoryButton) {
		add(loadMatchHistoryButton);
		setLoadMatchHistoryButtonStatic(loadMatchHistoryButton);
	}
	
	/**
	 * 
	 * @param loadMatchHistoryButton
	 */
	private static void setLoadMatchHistoryButtonStatic(LoadMatchHistoryButton loadMatchHistoryButton) {
		MatchInfoView.loadMatchHistoryButton = loadMatchHistoryButton;
	}
}
