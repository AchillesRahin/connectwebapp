<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="bootstrap.min.css">
<link rel="stylesheet" href="styles.css">
<title> find player connections</title>
</head>
<body>

<ul>
  <li class="activetab"><a href="/connectwebapp">Home</a></li>
  <li><a href="ConnectTwo">Connect Two</a></li>
  <li><a href="DuoqPartners">Duoq Partners</a></li>
  <li><a href="BanChamps">Champs To Ban</a></li>
</ul>

<br>

<h1 style="color:green">Lol analysis</h1>

<p style="color:green">We offer primarily three different types of information. We use data for the last year since the request is made
so expect it to take some time if it's your first time searching (Especially if you play a lot). 
Any consecutive ones will be faster</p>

<p style="color:green"> 1. Connect Two: 
		This essentially allows you to search two summoners and see any games they've played together. 
		Also you can click on individual games and it will open the match history link for you. </p>
<p style="color:green">	2. Duoq Partners: 
		You can enter a summoner and see the top 20 players that summoner has played with or against and see the 
		winrates with or against the summoner. </p>
<p style="color:green">	3. Champs to Ban:
		This should just be taken as raw data so you can make more informed decisions. What it does is look through
		the champs you've played against and see your personal winrates vs each champ. You can also filter by
		which champ you play. This was made to help one tricks or someone with many games on a single champ see
		what champs are really giving them issues. </p>
<script src="jquery-3.4.1.js" type="text/javascript"></script>
<script src="scripts.js" type="text/javascript"></script>

</body>
</html>