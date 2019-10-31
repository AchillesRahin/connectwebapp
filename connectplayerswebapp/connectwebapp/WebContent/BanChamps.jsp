<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="bootstrap.min.css">
<link rel="stylesheet" href="styles.css">
<title>Insert title here</title>
</head>
<body>

<ul>
  <li><a href="/connectwebapp">Home</a></li>
  <li><a href="ConnectTwo">Connect Two</a></li>
  <li><a href="DuoqPartners">Duoq Partners</a></li>
  <li class="activetab"><a href="BanChamps">Champs To Ban</a></li>
</ul>

<br>
<%@ page import="api.BanChampsAPIResponse, java.util.List,java.util.Map, api.Champion, main.java.com.constants.ChampList" %>

<form action = "BanMatchEndpoint" method = "GET">
         <input type = "text" name = "summonerOne">
         <select name="region">
			<option value="NA1">NA</option>
			<option value="EUW">EUW</option>
			<option value="LAN">LAN</option>
		 </select>
		 <select name="queue">
			<option value="solo/duo">Solo/Duo</option>
			<option value="flex">Flex 5v5</option>
			<option value="normal">5v5 normal</option>
		 </select>
		 <select name="champID">
		 	<option value="-1">(none)</option>
		 	<%
		 	ChampList cList = new ChampList();
		 	Map<String, Integer> champMap = cList.getChampMap();
		 	for (String champName: champMap.keySet()){
		 		out.println("<option value=\"" + champMap.get(champName) + "\">" + champName + "</option>");
		 	}
		 	%>
		 </select>
		 <button type="submit" form="nameform" value="Submit" onclick="this.disabled='disabled'">Submit</button>
      </form>
      
      <%
      	if (session.getAttribute("errorBanChamps") != null){
      		out.println("<p> error occured while searching: " + session.getAttribute("errorResponse") + "</p>");
      	}
      %>
      
      
      
      <%
      BanChampsAPIResponse banChampsData = (BanChampsAPIResponse) session.getAttribute("banChampsData");
      
      if (banChampsData != null){
          if (banChampsData.getBanList().size() == 0){
        	  out.println("<p> no games found</p>");
          }
          
          else{
        	  List<Champion> banChampsList = banChampsData.getBanList();
          
      %>
      
      <table align=center>
    	  <thead>
        	<tr>
            <th scope="col">Champion</th>
            <th scope="col">Wins</th>
            <th scope="col">Losses</th>
            <th scope="col">Winrate</th>
        	</tr>
    		</thead>
    	<%
    	for (Champion champ: banChampsList){
    		out.println("<tr>");
    		out.println("<td>" + champ.getName() + "</td>");
    		out.println("<td>" + champ.getWins() + "</td>");
    		out.println("<td>" + champ.getLosses() + "</td>");
    		out.println("<td>");
    		String bar = "<div class=\"progress\"><div class=\"progress-bar\" role=\"progressbar\" style=\"width: %WIDTH1%%\" aria-valuenow=\"15\" aria-valuemin=\"0\" aria-valuemax=\"100\"></div><div class=\"progress-bar bg-danger\" role=\"progressbar\" style=\"width: %WIDTH2%%\" aria-valuenow=\"30\" aria-valuemin=\"0\" aria-valuemax=\"100\"></div></div></td>";
    		int winRatePercent = 100 * champ.getWins() / champ.getTotal();
    		int lossRatePercent = 100 - winRatePercent;
    		bar = bar.replace("%WIDTH1%", String.valueOf(winRatePercent));
    		bar = bar.replace("%WIDTH2%", String.valueOf(lossRatePercent));
    		out.println(bar);
    		out.println("</td>");
    		out.println("</tr>");
    	}
      }
      }
    	
    	%>
    	</table>

</body>
</html>