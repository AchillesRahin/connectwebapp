package updater;



import java.util.List;
import java.util.Map;

import main.java.com.db.DBUtils;
import main.java.com.dto.Match;
import main.java.com.dto.MatchSummoner;
import main.java.com.riotapi.RiotAPIController;


public class UpdateSummoner {
	
	DBUtils dao;
	RiotAPIController riotAPIController;
	
	public UpdateSummoner() {
		dao = new DBUtils();
		riotAPIController = new RiotAPIController();
	}

	public synchronized void updateSummoner(String accountID, String region, int queue) {
		long lastLook = dao.getLastLook(accountID, region);
		long currTime = System.currentTimeMillis();
		boolean isInDB = true;
		if (lastLook == -1) {
			lastLook = currTime - Long.valueOf("33113852000");
			isInDB = false;
		}
		else if (currTime - lastLook <= Long.valueOf("1800000")) {
			return;
		}
		List<Long> matchIds = riotAPIController.getMatchList(accountID, lastLook, region, currTime, queue);
		System.out.println("number of matches found=" + matchIds.size());
		addMatches(matchIds, accountID, region);
		if (isInDB) {
			dao.updateLastLook(accountID, region, currTime);
		}
		else {
			dao.addLastLook(accountID, region, currTime);
		}
	}
	
	public void addMatches(List<Long> matchIdList, String accountID, String region) {
		for (long id: matchIdList) {
			if (dao.getMatchByID(id) != null) System.out.println("duplicate found for id:" + id);
			if (dao.getMatchByID(id) != null) continue;
			Match match = riotAPIController.getMatchByMatchID(id, region);
			if (match == null) {
				System.out.println("could not add matchid: " + id);
				continue;
			}
			System.out.println("adding match " + id);
			Map<String, MatchSummoner> summoners = match.getSummoners();
			for (String sum: summoners.keySet()) {
				dao.addAccToMatchInDB(sum, match);
			}
			dao.addMatchToDB(match);
		}
	}
}
