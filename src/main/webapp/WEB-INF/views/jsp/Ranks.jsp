<%@ page import="entities.user.User" %>
<%@ page import="java.text.DateFormatSymbols" %>
<%@ page import="java.util.Calendar" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%--
  Created by IntelliJ IDEA.
  User: ANykytenko
  Date: 8/16/2016
  Time: 10:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" language="java"%>
<html>
<head>
    <title>Football challenge</title>
    <link href="../css/ranks.css" rel="stylesheet" type="text/css">
    <link href="../css/common.css" rel="stylesheet" type="text/css">
    <link href="../css/chat.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="../js/libs/stomp.js"></script>
    <script type="text/javascript" src="../js/libs/sockjs-0.3.4.js"></script>
    <script type="text/javascript" src="../js/libs/httpRequest.js"></script>
    <script type="text/javascript" src="../js/libs/resize.js"></script>

    <script type="text/javascript" src="../js/ranks/ranksTable.js"></script>
    <script type="text/javascript" src="../js/ranks/ranksSocketCommunication.js"></script>

    <script type="text/javascript" src="../js/chat/chat.js"></script>
</head>
<%User user = (User) request.getAttribute("user");%>
<body onload="initRanksPage(<%=user.getId()%>)" onunload="destroyStompCommunication()">
<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h2>Welcome, ${pageContext.request.userPrincipal.name}
        (<%=user.getFirstName() + " " + user.getLastName() %>) |
        <%=user.getRole().toString()%> |
        <a href="/Logout">Logout</a>
    </h2>
</c:if>
Go to <a href="/Pages/Challenges">List of Challenges</a>
<a class="tournament-regulation" href="<%=request.getAttribute("tournamentRegulationsUrl")%>">Tournament Regulations</a>
<div class="page-title bold">
    Current Ranks (<%=new DateFormatSymbols().getMonths()[Calendar.getInstance().get(Calendar.MONTH)]%>)
</div>
<div id="rank-table" class="table"></div>
<div id="chat" class="chat"></div>
<audio id="notification" src="../audio/notification.mp3"></audio>
</body>
</html>
