package teamgg.ui.components.profileinfo;

import javax.swing.JLabel;

import teamgg.data.updateUI;

public class ProfileInfo extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String summonerName = "";
	private String accountId = "";
	private String summonerLevel = "";
	private String profileIconPath= "";
	private String profileIconId = "";
	
	private final String htmlTemplate = "<html>" +
			 								"<div>summoner name: <font color='white'>%s</font>" +
			 								"</div>" +
			 								"<div>account ID:  <font color='white'>%s</font>" +
			 								"</div>" +
			 								"<div>summoner level:  <font color='white'>%s</font>" +
			 								"</div>" +
			 								"<div>profile Icon:  <font color='white'>%s</font>" +
			 								"</div>" +
			 								"<image src=\"%s\"></font>" +
			 								"</div>" +
			 							"</html>";
	
	public ProfileInfo () {
		super();
		setVisible(true);
		updateProfilInfo();
		
	}
	
	
	public void updateProfilInfo() {
		String html = String.format(getHtmlTemplate(), getSummonerName(), getAccountId(), getSummonerLevel(), getProfileIconId(), getProfileIconPath());
		
		setText(html);
	}


	public String getSummonerName() {
		return summonerName;
	}


	public void setSummonerName(String summonerName, boolean update) {
		this.summonerName = summonerName;
		checkUpdate(update);
	}
	
	public void setSummonerName(Object summonerName, boolean update) {
		setSummonerName((String) summonerName, update);
	}


	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId, boolean update) {
		this.accountId = accountId;
		checkUpdate(update);
	}

	public void setAccountId(Object value, boolean update) {
		setAccountId((String) value, update);
	}

	public String getHtmlTemplate() {
		return htmlTemplate;
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


	public void setSummonerLevel(String summonerLevel, boolean update) {
		this.summonerLevel = summonerLevel;
		checkUpdate(update);
	}


	public String getProfileIconId() {
		return profileIconId;
	}


	public void setProfileIconId(String profileIconId, boolean update) {
		this.profileIconId = profileIconId;
		setProfileIconPath(profileIconId);

		checkUpdate(update);
	}
	
	private void checkUpdate(boolean update) {
		if (update) {
			updateProfilInfo();
		}
	}

	public void setSummonerLevel(Object value, boolean update) {
		setSummonerLevel(String.valueOf((long) value), false);
	}


	public void setProfileIconId(Object value, boolean update) {
		setProfileIconId(String.valueOf((int) value), false);
	}


	
	
}
