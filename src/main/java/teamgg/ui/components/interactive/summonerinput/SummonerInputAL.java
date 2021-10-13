package teamgg.ui.components.interactive.summonerinput;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import errorhandling.TeamGGException;
import teamgg.data.staticmodel.ProfileInformation;
import teamgg.ui.views.ProfileView;

public class SummonerInputAL implements DocumentListener {

	//TODO throw errors to Error view / message handler

	@Override
	public void insertUpdate(DocumentEvent e) {
		try {
			updatedSummonerInput();
		} catch (TeamGGException e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		try {
			updatedSummonerInput();
		} catch (TeamGGException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		try {
			updatedSummonerInput();
		} catch (TeamGGException e1) {
			e1.printStackTrace();
		}
	}

	

	private void updatedSummonerInput() throws TeamGGException {
		String summonerName = ProfileView.getSummonerInput().getText();
		
		if (summonerName == null) {
			summonerName = "";
		}
		
		ProfileInformation.setSummonerNameInput(summonerName, true);
	}
}
