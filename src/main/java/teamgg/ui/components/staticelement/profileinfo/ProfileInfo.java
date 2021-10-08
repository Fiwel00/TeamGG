package teamgg.ui.components.staticelement.profileinfo;

import javax.swing.JLabel;

import errorhandling.TeamGGException;
import errorhandling.customexception.FileNotFoundException;
import teamgg.ui.components.staticelement.StaticComponent;

public class ProfileInfo extends StaticComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String summonerName = "";
	private String accountId = "";
	private String summonerLevel = "";
	private String profileIconPath= "";
	private String profileIconId = "";
	
	
	public ProfileInfo () throws TeamGGException {
		super("resources/ui/profileInfo");
		setVisible(true);
		update();
		
	}
	

	public String getSummonerName() {
		return summonerName;
	}


	public void setSummonerName(String summonerName, boolean update) throws TeamGGException {
		this.summonerName = summonerName;
		checkUpdate(update);
	}
	
	public void setSummonerName(Object summonerName, boolean update) throws TeamGGException {
		setSummonerName((String) summonerName, update);
	}


	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId, boolean update) throws TeamGGException {
		this.accountId = accountId;
		checkUpdate(update);
	}

	public void setAccountId(Object value, boolean update) throws TeamGGException {
		setAccountId((String) value, update);
	}


	public String getProfileIconPath() {
		return profileIconPath;
	}


	/**
	 * 
	 * @param profileIconId
	 */
	private void setProfileIconPath(String profileIconId) {
		
		this.profileIconPath = String.format("https://raw.communitydragon.org/latest/game/assets/ux/summonericons/profileicon%s.png", profileIconId);
	}


	public String getSummonerLevel() {
		return summonerLevel;
	}


	public void setSummonerLevel(String summonerLevel, boolean update) throws TeamGGException {
		this.summonerLevel = summonerLevel;
		checkUpdate(update);
	}


	public String getProfileIconId() {
		return profileIconId;
	}


	public void setProfileIconId(String profileIconId, boolean update) throws TeamGGException {
		this.profileIconId = profileIconId;
		setProfileIconPath(profileIconId);

		checkUpdate(update);
	}
	
	private void checkUpdate(boolean update) throws TeamGGException {
		if (update) {
			update();
		}
	}

	public void setSummonerLevel(Object value, boolean update) throws TeamGGException {
		setSummonerLevel(String.valueOf((long) value), false);
	}


	public void setProfileIconId(Object value, boolean update) throws TeamGGException {
		setProfileIconId(String.valueOf((int) value), false);
	}


	@Override
	public void update() throws TeamGGException {
		setText(String.format(getComponenthtml(), getSummonerName(), getAccountId(), getSummonerLevel(), getProfileIconId(), getProfileIconPath()));
		
	}


	
	
}
