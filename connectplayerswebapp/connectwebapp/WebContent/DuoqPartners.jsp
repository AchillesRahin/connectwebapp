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
  <li class="activetab"><a href="DuoqPartners">Duoq Partners</a></li>
  <li><a href="BanChamps">Champs To Ban</a></li>
</ul>

<br>

<form action = "DuoqPartnersEndpoint" method = "GET">
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
		 <button type="submit" form="nameform" value="Submit" onclick="this.disabled='disabled'">Submit</button>
      </form>
<%
      	if (session.getAttribute("errorDuoqResponse") != null){
      		out.println("<p> error occured while searching: " + session.getAttribute("errorResponse") + "</p>");
      	}
      %>
      <%@ page import="api.DuoqPartnerAPIResponse, java.util.List, api.DuoqPartner" %>
      
      <%
      DuoqPartnerAPIResponse apiRes = (DuoqPartnerAPIResponse) session.getAttribute("duoqData");
      if (apiRes != null){
      if (apiRes.getDuoqList().size() == 0){
    	  out.println("<p> no games > 2 found with another player</p>");
      }
      else {
    	  
    	  List<DuoqPartner> duoqList = apiRes.getDuoqList();
    	  /*
    	  
    	  <div class="progress">
  <div class="progress-bar" role="progressbar" style="width: 15%" aria-valuenow="15" aria-valuemin="0" aria-valuemax="100"></div>
  <div class="progress-bar bg-success" role="progressbar" style="width: 30%" aria-valuenow="30" aria-valuemin="0" aria-valuemax="100"></div>
</div>
    	  
    	  */
    	  %>
    	  <table align=center>
    	  <thead>
        	<tr>
            <th scope="col">Partner</th>
            <th scope="col">Wins With</th>
            <th scope="col">Losses With</th>
            <th scope="col">Winrate Together</th>
            <th scope="col">Wins Against</th>
            <th scope="col">Losses Against</th>
            <th scope="col">Winrate Against</th>
        	</tr>
    		</thead>
    	  <%
    	  for (DuoqPartner dp: duoqList){
    		  out.println("<tr>");
    		  out.println("<td>" + dp.getPartner() + "</td>");
    		  out.println("<td>" + dp.getWinsWith() + "</td>");
    		  out.println("<td>" + dp.getLossesWith() + "</td>");
    		  out.println("<td>");
    		  String bar = "<div class=\"progress\"><div class=\"progress-bar\" role=\"progressbar\" style=\"width: %WIDTH1%%\" aria-valuenow=\"15\" aria-valuemin=\"0\" aria-valuemax=\"100\"></div><div class=\"progress-bar bg-danger\" role=\"progressbar\" style=\"width: %WIDTH2%%\" aria-valuenow=\"30\" aria-valuemin=\"0\" aria-valuemax=\"100\"></div></div></td>";
        		int winRatePercent = 0;
        		int lossRatePercent = 0;
    		  if (dp.getWinsWith() + dp.getLossesWith() == 0){
    			  winRatePercent = 0;
    			  lossRatePercent = 0;
    		  }
    		  else{
    		  	winRatePercent = 100 * dp.getWinsWith() / (dp.getWinsWith() + dp.getLossesWith());
        		lossRatePercent = 100 - winRatePercent;
    		  }
        		bar = bar.replace("%WIDTH1%", String.valueOf(winRatePercent));
        		bar = bar.replace("%WIDTH2%", String.valueOf(lossRatePercent));
        		out.println(bar);
        		out.println("</td>");
    		  
    		  out.println("<td>" + dp.getWinsAgainst() + "</td>");
    		  out.println("<td>" + dp.getLossesAgainst() + "</td>");
    		  
    		  out.println("<td>");
      		bar = "<div class=\"progress\"><div class=\"progress-bar\" role=\"progressbar\" style=\"width: %WIDTH1%%\" aria-valuenow=\"15\" aria-valuemin=\"0\" aria-valuemax=\"100\"></div><div class=\"progress-bar bg-danger\" role=\"progressbar\" style=\"width: %WIDTH2%%\" aria-valuenow=\"30\" aria-valuemin=\"0\" aria-valuemax=\"100\"></div></div></td>";
      		if (dp.getWinsAgainst() + dp.getLossesAgainst() == 0){
      			winRatePercent = 0;
      			lossRatePercent = 0;
      		}
      		else{
      			winRatePercent = 100 * dp.getWinsAgainst() / (dp.getWinsAgainst() + dp.getLossesAgainst());
          		lossRatePercent = 100 - winRatePercent;
      		}
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