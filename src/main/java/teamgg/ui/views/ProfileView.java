package teamgg.ui.views;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import errorhandling.TeamGGException;
import teamgg.ui.components.interactive.loadprofile.LoadProfileButton;
import teamgg.ui.components.interactive.summonerinput.SummonerInput;
import teamgg.ui.components.staticelement.profileinfo.ProfileInfo;

public class ProfileView extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 247508097728075350L;

	private static LoadProfileButton profileButton;
	private static ProfileInfo profilInfolabel;
	private static SummonerInput summonerInput;
	
	
	public ProfileView() throws TeamGGException {
		setVisible(true);
		setBackground(Color.GRAY);

		setProfileButton(new LoadProfileButton());
		setSummonerInput(new SummonerInput());
		setProfilInfolabel(new ProfileInfo());
	
	}


	public static LoadProfileButton getProfileButton() {
		return profileButton;
	}


	public void setProfileButton(LoadProfileButton profileButton) {
		add(profileButton, BorderLayout.NORTH);
		setProfileButtonStatic(profileButton);
	}


	private static void setProfileButtonStatic(LoadProfileButton profileButton) {
		ProfileView.profileButton = profileButton;
	}


	public static ProfileInfo getProfilInfo() {
		return profilInfolabel;
	}


	public void setProfilInfolabel(ProfileInfo profilInfolabel) {
		add(profilInfolabel, BorderLayout.SOUTH);
		setProfilInfolabelStatic(profilInfolabel);
	}


	private static void setProfilInfolabelStatic(ProfileInfo profilInfolabel) {
		ProfileView.profilInfolabel = profilInfolabel;
	}


	public static SummonerInput getSummonerInput() {
		return summonerInput;
	}


	public void setSummonerInput(SummonerInput summonerInput) {
		add(summonerInput, BorderLayout.NORTH);
		setSummonerInputStatic(summonerInput);
	}


	private static void setSummonerInputStatic(SummonerInput summonerInput) {
		ProfileView.summonerInput = summonerInput;
	}
}
