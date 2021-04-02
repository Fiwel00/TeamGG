package teamgg.ui.components.interactive.summonerinput;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import errorhandling.TeamGGException;
import teamgg.data.InvalidUpdatePipelineException;
import teamgg.data.staticmodel.ProfileInformation;
import teamgg.ui.views.ProfileView;

public class SummonerInputAL implements DocumentListener {

	@Override
	public void insertUpdate(DocumentEvent e) {
		try {
			updatedSummonerInput();
		} catch (TeamGGException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		try {
			updatedSummonerInput();
		} catch (TeamGGException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		try {
			updatedSummonerInput();
		} catch (TeamGGException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	

	private void updatedSummonerInput() throws InvalidUpdatePipelineException {
		String summonerName = ProfileView.getSummonerInput().getText();
		
		if (summonerName == null) {
			summonerName = "";
		}
		
		ProfileInformation.setSummonerNameInput(summonerName, true);
	}
}
