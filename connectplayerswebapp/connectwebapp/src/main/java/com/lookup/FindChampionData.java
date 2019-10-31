package main.java.com.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import api.BanChampsAPIResponse;
import api.Champion;
import main.java.com.constants.ChampList;
import main.java.com.db.DBUtils;
import main.java.com.dto.Match;
import main.java.com.dto.MatchSummoner;
import main.java.com.riotapi.RiotAPIController;
import updater.UpdateSummoner;

public class FindChampionData {
	
	DBUtils dao;
	ChampList champs;
	RiotAPIController riotAPIController;
	public FindChampionData() {
		dao = new DBUtils();
		champs = new ChampList();
		riotAPIController = new RiotAPIController();
	}
	public BanChampsAPIResponse find(String summonerOne, String region, String champion, int queue) {
		String accountID1 = null;
		
		try {
			accountID1 = riotAPIController.convertSummonerToAccountID(summonerOne, region);
		}
		catch(Exception e) {
			return new BanChampsAPIResponse(500, e.getMessage());
		}
		UpdateSummoner us = new UpdateSummoner();
		us.updateSummoner(accountID1, region, queue);
		
		Map<Integer, Champion> championMap = new HashMap<>();
		
		Set<Long> matchIDList = dao.getMatchIDsByAccountID(accountID1, region, queue);
		
		List<Match> matchList = new ArrayList<>();
		for (long id: matchIDList) {
			Match curr = dao.getMatchByID(id);
			if (curr != null) {
				matchList.add(curr);
			}
		}
		int champIDFilter = Integer.parseInt(champion);
		for (Match m: matchList) {
			int winningTeam = m.getWinningTeam();
			Map<String, MatchSummoner> msMap = m.getSummoners();
			
			int summonerTeam = msMap.get(accountID1).getTeamID();
			if (champIDFilter != -1) {
				if (msMap.get(accountID1).getChampID() != champIDFilter) continue;
			}
			boolean win = winningTeam == summonerTeam;
			
			for (String s: msMap.keySet()) {
				MatchSummoner ms = msMap.get(s);
				if (ms.getTeamID() != summonerTeam) {
					if (!championMap.containsKey(ms.getChampID())){
						String champName = champs.getChampList()[ms.getChampID()];
						championMap.put(ms.getChampID(), new Champion(champName, ms.getChampID(), 0, 0));
					}
					
					if (win) {
						championMap.get(ms.getChampID()).addWin();
					}
					else {
						championMap.get(ms.getChampID()).addLoss();
					}
				}
			}
		}
		List<Champion> sortedChampList = new ArrayList<>();
		sortedChampList.addAll(championMap.values());
		
		Collections.sort(sortedChampList, new Comparator<Champion>() {
			public int compare(Champion a, Champion b) {
				return b.getTotal() - a.getTotal();
			}
		});
		return new BanChampsAPIResponse(200, sortedChampList);
		
	}

}

