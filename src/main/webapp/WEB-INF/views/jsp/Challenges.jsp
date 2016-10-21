<%@ page import="entities.user.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%--
  Created by IntelliJ IDEA.
  User: ANykytenko
  Date: 8/16/2016
  Time: 10:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Football challenge</title>
    <link href="../css/challenges.css" rel="stylesheet" type="text/css">
    <link href="../css/common.css" rel="stylesheet" type="text/css">
    <link href="../css/chat.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="../js/libs/stomp.js"></script>
    <script type="text/javascript" src="../js/libs/sockjs-0.3.4.js"></script>
    <script type="text/javascript" src="../js/libs/httpRequest.js"></script>
    <script type="text/javascript" src="../js/libs/resize.js"></script>
    <script type="text/javascript" src="../js/libs/updateFromDb.js"></script>

    <script type="text/javascript" src="../js/challenges/challengesList.js"></script>
    <script type="text/javascript" src="../js/challenges/challengesSocketCommunication.js"></script>

    <script type="text/javascript" src="../js/chat/chat.js"></script>
</head>
<%User user = (User) request.getAttribute("user");%>
<body class="<%=user.getRole().toString().toLowerCase()%>" onload="initChallengesPage(<%=user.getId()%>)" onunload="destroyStompCommunication()">
<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h2>Welcome, ${pageContext.request.userPrincipal.name}
        (<%=user.getFirstName() + " " + user.getLastName() %>) |
        <%=user.getRole().toString()%> |
        <a href="/Logout">Logout</a>
    </h2>
</c:if>
<span>Go to</span>
<a href="/Pages/Ranks">Current Ranks</a>
<a class="tournament-regulation" href="<%=request.getAttribute("tournamentRegulationsUrl")%>">Tournament Regulations</a>
<div class="page-title bold">List of Challenges</div>
<div id="challenges-list" class="challenges-list"></div>
<div id="chat" class="chat"></div>
<%if (user.getRole().equals(User.Role.ADMIN)) {
    %><span id="add-challenge" class="btn-default">+Add</span>
    <span id="update-from-db" class="btn-default">Update</span><%
}%>
<audio id="notification" src="../audio/notification.mp3"></audio>
</body>
</html>
