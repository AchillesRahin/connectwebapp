<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<title> find player connections</title>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="bootstrap.min.css">
<link rel="stylesheet" href="styles.css">
<title>ConnectTwo</title>
</head>
<body>

<ul>
  <li><a href="/connectwebapp">Home</a></li>
  <li class="activetab"><a href="ConnectTwo">Connect Two</a></li>
  <li><a href="DuoqPartners">Duoq Partners</a></li>
  <li><a href="BanChamps">Champs To Ban</a></li>
</ul>
	<br>
      <form action = "Summoners" method = "GET">
         <input type = "text" name = "summonerOne">
         <input type = "text" name = "summonerTwo" />
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
      <br>
      <br>
      <%
      	if (session.getAttribute("errorResponse") != null){
      		out.println("<div class=\"alert alert-danger\" role=\"alert\">" + session.getAttribute("errorResponse") + "</div>");
      	}
      %>
      <%@ page import="api.APIResponse, java.util.List, api.DuoMatch"
      %>
      <% if (session.getAttribute("connectData") != null) { 
      	APIResponse apiRes = (APIResponse) session.getAttribute("connectData");
      	if (apiRes.getTogetherCount() == 0 && apiRes.getAgainstCount() == 0){
      %>
      		<p> no games played together</p>
      <%
      	}
      	else {
      		%>
       		<table align="center">
      			<tr>
      				<td>Played Together</td>
      				<% out.println("<td>" + apiRes.getTogetherCount() + "</td>"); %>
      			</tr>
      			<tr>
      				<td>Played Against</td>
      				<% out.println("<td>" + apiRes.getAgainstCount() + "</td>"); %>
      			</tr>
      			<tr>
      				<td>Won Together</td>
      				<% out.println("<td>" + apiRes.getTogetherWins() + "</td>"); %>
      			</tr>
      			<tr>
      				<% out.println("<td>" + apiRes.getPlayerOne() + " Wins</td>"); %>
      				<% out.println("<td>" + (apiRes.getAgainstWins() + apiRes.getTogetherWins()) + "</td>"); %>
      			</tr>
      			<tr>
      				<% out.println("<td>" + apiRes.getPlayerTwo() + " Wins</td>"); %>
      				<% out.println("<td>" + (apiRes.getAgainstCount() - apiRes.getAgainstWins() + apiRes.getTogetherWins()) + "</td>"); %>
      			</tr>
      			
      		</table>
      		
      		<table align="center">
      			<%
      				List<DuoMatch> matchListData = apiRes.getList();
      				for (DuoMatch dm: matchListData){
      					out.println("<tr class=\"contwo\" linkdata=\"" + dm.getMatchURL() + "\">");
      				    out.println("<td>" + dm.getSummonerOne() + "</td>");
      				  	out.println("<td>" + dm.getChampionOne() + "</td>");
      				  	out.println("<td>" + dm.getTimestamp() + "</td>");
      				  	out.println("<td>" + dm.getChampionTwo() + "</td>");
      				  	out.println("<td>" + dm.getSummonerTwo() + "</td>");
      					out.println("</tr>");
      					
      				}
      				
      			%>
      		</table>
      		<%
      	}
      }
      %>
      
    <script src="jquery-3.4.1.js" type="text/javascript"></script>
	<script src="scripts.js" type="text/javascript"></script>

</body>

</html>