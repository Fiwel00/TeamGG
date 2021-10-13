package teamgg.data.staticmodel;

import java.util.ArrayList;
import java.util.List;

import errorhandling.TeamGGException;
import teamgg.data.FieldIdValues;
import teamgg.data.FieldIdsEnum;
import teamgg.data.InvalidUpdatePipelineException;
import teamgg.data.updateUI;
import teamgg.data.summonerinfo.dto.SummonerInfo;

/**
 * purpose of this class is to store the information regarding the profil of the
 * user when the values changes {@link updateUI} can be triggered to make the
 * necessary changes throughout the application
 * 
 * @author Mehdi Ayadi
 *
 */
public class ProfileInformation {

	private static String summonerNameInput;

	private static String accountId;
	private static String summonerName;
	private static long summonerLevel;
	private static int profileIconId;

	private static List<FieldIdValues> updateValues = new ArrayList<>();

	public static void setSummonerInfo(SummonerInfo summonerInfo, boolean update) throws TeamGGException {

		setAccountId(summonerInfo.getAccountId(), false);
		setSummonerName(summonerInfo.getName(), false);
		setSummonerLevel(summonerInfo.getSummonerLevel(), false);
		setProfileIconId(summonerInfo.getProfileIconId(), false);

		checkUpdate(update);
	}

	private static void checkUpdate(boolean update) throws TeamGGException {
		if (update && updateValues.isEmpty() == false) {
			updateUI.updateSummoner(updateValues);
			updateValues = new ArrayList<>();
		}
		
	}

	public static int getProfileIconId() {
		return profileIconId;
	}

	public static void setProfileIconId(int profileIconId, boolean update) throws TeamGGException {
		ProfileInformation.profileIconId = profileIconId;
		updateValues.add(new FieldIdValues(FieldIdsEnum.PROFILE_ICON_ID, getProfileIconId()));

		checkUpdate(update);
	}

	public static long getSummonerLevel() {
		return summonerLevel;
	}

	public static void setSummonerLevel(long summonerLevel, boolean update) throws TeamGGException {
		ProfileInformation.summonerLevel = summonerLevel;
		updateValues.add(new FieldIdValues(FieldIdsEnum.SUMMONER_LEVEL, getSummonerLevel()));

		checkUpdate(update);
	}

	public static String getSummonerName() {
		return summonerName;
	}

	public static void setSummonerName(String name, boolean update) throws TeamGGException {
		ProfileInformation.summonerName = name;
		updateValues.add(new FieldIdValues(FieldIdsEnum.SUMMONER_NAME, getSummonerName()));

		checkUpdate(update);
	}

	public static String getAccountId() {
		return accountId;
	}

	public static void setAccountId(String accountId, boolean update) throws TeamGGException {
		ProfileInformation.accountId = accountId;
		updateValues.add(new FieldIdValues(FieldIdsEnum.ACCOUNT_ID, getAccountId()));

		checkUpdate(update);

	}

	public static String getSummonerNameInput() {
		return summonerNameInput;
	}

	public static void setSummonerNameInput(String summonerNameInput, boolean update) throws TeamGGException {
		ProfileInformation.summonerNameInput = summonerNameInput;
		updateValues.add(new FieldIdValues(FieldIdsEnum.SUMMONER_NAME_INPUT, getSummonerName()));

		checkUpdate(update);
	}

}
