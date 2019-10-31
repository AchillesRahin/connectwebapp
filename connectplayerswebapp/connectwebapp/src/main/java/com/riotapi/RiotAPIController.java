package main.java.com.riotapi;

import java.util.List;

import main.java.com.constants.MillisecondTime;
import main.java.com.db.DBUtils;
import main.java.com.dto.AccountLookup;
import main.java.com.dto.Match;



public class RiotAPIController {

	SummonerConverter summonerConverter;
	MatchAPICalls matchCalls;
	DBUtils dao;
	
	public RiotAPIController() {
		summonerConverter = new SummonerConverter();
		matchCalls = new MatchAPICalls();
		dao = new DBUtils();
	}
	public String convertSummonerToAccountID(String summonerName, String region) throws Exception {
		summonerName = summonerName.replaceAll(" ", "");
		String converted = getFromDB(summonerName, region, 0);
		if (converted != null && converted.length() > 0) return converted;
		String accID = summonerConverter.convertNameToAccount(summonerName, region);
		if (converted == null) {
			dao.addAccountLastLook(accID, summonerName, region, System.currentTimeMillis());
		}
		else {
			dao.updateAccountLastLook(accID, summonerName, region, System.currentTimeMillis());
		}
		return accID;
	}
	public String convertAccountIDToSummoner(String accountID, String region) {
		accountID = accountID.replaceAll(" ", "");
		String converted = getFromDB(accountID, region, 1);
		if (converted != null && converted.length() > 0) return converted;
		String ign = summonerConverter.convertAccountToName(accountID, region);
		if (converted == null) {
			dao.addAccountLastLook(accountID, ign, region, System.currentTimeMillis());
		}
		else {
			dao.updateAccountLastLook(accountID, ign, region, System.currentTimeMillis());
		}
		return ign;
	}
	public List<Long> getMatchList(String accountID, long lastLook, String region, long currTime, int queue) {
		return matchCalls.getMatchList(accountID, lastLook, region, currTime, queue);
	}
	public Match getMatchByMatchID(long matchID, String region) {
		return matchCalls.getMachByID(matchID, region);
	}
	
	private String getFromDB(String str, String region, int val) {
		AccountLookup acc = null;
		if (val == 0) {
			acc = dao.getAccountByIgn(str, region);
		}
		else {
			acc = dao.getAccountByAccountId(str, region);
		}
		
		if (acc == null) return null;
		
		long lastLook = acc.getLastLook();
		long currTime = System.currentTimeMillis();
		
		if (currTime - lastLook > MillisecondTime.TWO_WEEKS) {
			return "";
		}
		else {
			if (val == 0) {
				return acc.getAccountId();
			}
			else {
				return acc.getIgn();
			}
		}
	}
}
