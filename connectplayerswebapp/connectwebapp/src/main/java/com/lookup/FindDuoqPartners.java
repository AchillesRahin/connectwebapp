package main.java.com.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import api.DuoqPartner;
import api.DuoqPartnerAPIResponse;
import main.java.com.constants.ChampList;
import main.java.com.db.DBUtils;
import main.java.com.dto.Match;
import main.java.com.dto.MatchSummoner;
import main.java.com.riotapi.RiotAPIController;
import updater.UpdateSummoner;


public class FindDuoqPartners {
	
	DBUtils dao;
	ChampList champs;
	RiotAPIController riotAPIController;
	
	public FindDuoqPartners() {
		dao = new DBUtils();
		champs = new ChampList();
		riotAPIController = new RiotAPIController();
	}

	public DuoqPartnerAPIResponse search(String summonerOne, String region, int queue) {
		String accountID1 = null;
		
		try {
			accountID1 = riotAPIController.convertSummonerToAccountID(summonerOne, region);
		}
		catch(Exception e) {
			return new DuoqPartnerAPIResponse(500, e.getMessage());
		}
		UpdateSummoner us = new UpdateSummoner();
		us.updateSummoner(accountID1, region, queue);
		
		Set<Long> matchIDList = dao.getMatchIDsByAccountID(accountID1, region, queue);
		List<Match> matchList = new ArrayList<>();
		for (long id: matchIDList) {
			Match curr = dao.getMatchByID(id);
			if (curr != null) {
				matchList.add(curr);
			}
		}
		Map<String, DuoqPartner> teammateCount = new HashMap<>();
		for (Match m: matchList) {
			Map<String, MatchSummoner> summonerMap = m.getSummoners();
			
			MatchSummoner msUser = summonerMap.get(accountID1);
			
			if (msUser == null) {
				System.out.println("wtf why even here line 55 class duoqpartners.java");
			}
			
			int teamID = msUser.getTeamID();
			boolean win = teamID == m.getWinningTeam();
			
			for (String summoner: summonerMap.keySet()) {
				if (summoner.equals(accountID1)) continue;
				
				MatchSummoner ms = summonerMap.get(summoner);
				
				if (!teammateCount.containsKey(summoner)) {
					teammateCount.put(summoner, new DuoqPartner(summoner));
				}
				
				DuoqPartner partner = teammateCount.get(summoner);
				
				if (ms.getTeamID() == teamID) {
					if (win) {
						partner.addWinWith();
					}
					else {
						partner.addLossWith();
					}
				}
				else {
					if (win) {
						partner.addWinAgainst();
					}
					else {
						partner.addLossesAgainst();
					}
				}
			}
		}
		List<String> removeList = new ArrayList<>();
		for (String s: teammateCount.keySet()) {
			DuoqPartner dp = teammateCount.get(s);
			if (dp.getTotal() <= 3) {
				removeList.add(s);
			}
		}
		
		for (String s: removeList) {
			teammateCount.remove(s);
		}
		
		List<String> summonerAccountList = new ArrayList<>();
		summonerAccountList.addAll(teammateCount.keySet());
		
		Collections.sort(summonerAccountList, new Comparator<String>() {
			public int compare(String a, String b) {
				return teammateCount.get(b).getTotal() - teammateCount.get(a).getTotal();
			}
		});
		
		int minReturned = Math.min(20, summonerAccountList.size());
		List<DuoqPartner> duoqAccounts = new ArrayList<>();
		for (int i = 0; i < minReturned;i++) {
			String accID = summonerAccountList.get(i);
			String ign = riotAPIController.convertAccountIDToSummoner(accID, region);
			teammateCount.get(accID).setPartner(ign);
			if (ign != null) {
				duoqAccounts.add(teammateCount.get(accID));
			}
			else {
				System.out.println("wtf what now. convertaccid to summoner for " + accID + " returned null");
			}
		}
		return new DuoqPartnerAPIResponse(200, duoqAccounts);
	}

}
