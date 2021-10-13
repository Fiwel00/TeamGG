package teamgg.api;


import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.core.match.Match;
import com.merakianalytics.orianna.types.core.match.MatchHistory;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import com.merakianalytics.orianna.types.core.summoner.Summoner.Builder;

import errorhandling.TeamGGException;
import teamgg.data.summonerinfo.SummonerInfoFactory;
import teamgg.data.summonerinfo.dto.SummonerInfo;

public class ApiLeagueOfLegends {

	/**
	 * 
	 * @param matchId
	 * @return
	 * @throws TeamGGException
	 */
	public static Match loadMatchRawOri(long matchId) throws TeamGGException {

		com.merakianalytics.orianna.types.core.match.Matches.Builder matchesWithIds = Orianna.matchesWithIds(matchId);

		return matchesWithIds.get().get(0);
	}

	/**
	 * 
	 * @param accountId
	 * @param beginIndex
	 * @param endIndex
	 * @return
	 * @throws TeamGGException
	 */
	public static MatchHistory loadPlayermatchHistoryOri(String accountId, int beginIndex, int endIndex) {

		Builder summonerBuilder = Orianna.summonerWithAccountId(accountId);
		Summoner summoner = summonerBuilder.get();

		com.merakianalytics.orianna.types.core.match.MatchHistory.Builder matchHistoryBuilder = Orianna
				.matchHistoryForSummoner(summoner);

		matchHistoryBuilder.withStartIndex(beginIndex);
		matchHistoryBuilder.withEndIndex(endIndex);
		matchHistoryBuilder.withQueues(Queue.RANKED_SOLO);

		return matchHistoryBuilder.get();
	}


	/***
	 * 
	 * @param summonerName
	 * @return
	 * @throws TeamGGException
	 */
	public static SummonerInfo loadPlayerOri(String summonerName) {

		Builder summonerNamed = Orianna.summonerNamed(summonerName);
		return SummonerInfoFactory.createSummmonerInfo(summonerNamed.get());

	}

	/***
	 * w
	 * @param summonerName
	 * @return
	 * @throws TeamGGException
	 */
	public static SummonerInfo loadPlayerOriOverAccId(String accountid) {

		Builder summonerNamed = Orianna.summonerWithAccountId(accountid);
		return SummonerInfoFactory.createSummmonerInfo(summonerNamed.get());
	}
}
