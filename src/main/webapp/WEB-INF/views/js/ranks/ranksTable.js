function initRanksPage(userId) {
    initResizeWindowHandler();
    var dateNow = new Date();
    createRanksTable(dateNow.getFullYear(), dateNow.getMonth() + 1);
    initStompCommunication(userId);
    createChat();
}

function createRanksTable(year, month) {
    initPreviousAndNextListeners(year, month);
    document.getElementById("title-value").innerText = "Ranks (" + months[month - 1] + ", " + year + ")";
    var location = window.location.protocol + "//" + window.location.host;
    sendRequest('GET', location + '/Reports/RanksTable/' + year + '/' + month, buildTable);
}

function buildTable(status, response) {
    if (status != 404) {
        var tableElement = document.getElementById('rank-table');
        tableElement.innerHTML = "";
        var rowsElement = document.createElement("div");
        rowsElement.className = "rows";
        tableElement.appendChild(createHeadersElement());
        tableElement.appendChild(rowsElement);
        var ranksTable = JSON.parse(response);
        for (var i = 0; i < ranksTable.rows.length; i++) {
            rowsElement.appendChild(createRowElement(i + 1, ranksTable.rows[i], ranksTable.user));
        }
    }
    resize();
}

function initPreviousAndNextListeners(year, month) {
    var previousElement = document.getElementById("previous-rank");
    var nextElement = document.getElementById("next-rank");
    previousElement.onclick = function() {
        var newYear = year;
        var newMonth = month - 1;
        if (month == 1) {
            newYear = year - 1;
            newMonth = 12;
        }
        createRanksTable(newYear, newMonth);
    };
    nextElement.onclick = function() {
        var newYear = year;
        var newMonth = month + 1;
        if (month == 12) {
            newYear = year + 1;
            newMonth = 1;
        }
        createRanksTable(newYear, newMonth);
    };
}

function createHeadersElement() {
    var headersElement = document.createElement("div");
    headersElement.className = "headers";
    headersElement.appendChild(createSimpleSpan("number", "#"));
    headersElement.appendChild(createSimpleSpan("first-name", "First name"));
    headersElement.appendChild(createSimpleSpan("last-name", "Last name"));
    headersElement.appendChild(createSimpleSpan("user-name", "User name"));
    headersElement.appendChild(createSimpleSpan("challenges-count", "Games"));
    headersElement.appendChild(createSimpleSpan("wins-count", "Wins"));
    headersElement.appendChild(createSimpleSpan("draws-count", "Draws"));
    headersElement.appendChild(createSimpleSpan("losses-count", "Losses"));
    headersElement.appendChild(createSimpleSpan("scored-count", "Scored"));
    headersElement.appendChild(createSimpleSpan("conceded-count", "Conceded"));
    headersElement.appendChild(createSimpleSpan("difference-count", "Diff"));
    headersElement.appendChild(createSimpleSpan("points", "Points"));
    headersElement.appendChild(createSimpleSpan("state", "State"));
    return headersElement;
}

function createRowElement(number, row, user) {
    var rowElement = document.createElement("div");
    if (row.active) {
        rowElement.className = "row active";
        if (row.userId == user.id) {
            rowElement.className = rowElement.className + " me";
        }
    } else {
        rowElement.className = "row inactive";
    }
    rowElement.appendChild(createSimpleSpan("number", number + "."));
    rowElement.appendChild(createSimpleSpan("first-name", row.firstName));
    rowElement.appendChild(createSimpleSpan("last-name", row.lastName));
    rowElement.appendChild(createSimpleSpan("user-name", row.userName));
    rowElement.appendChild(createSimpleSpan("challenges-count", row.challengesCount));
    rowElement.appendChild(createSimpleSpan("wins-count", row.winsCount));
    rowElement.appendChild(createSimpleSpan("draws-count", row.drawsCount));
    rowElement.appendChild(createSimpleSpan("losses-count", row.lossesCount));
    rowElement.appendChild(createSimpleSpan("scored-count", row.scored));
    rowElement.appendChild(createSimpleSpan("conceded-count", row.conceded));
    rowElement.appendChild(createSimpleSpan("difference-count", row.scored - row.conceded));
    var pointsAverage = row.pointsAverage != -1 ? row.pointsAverage.toFixed(2) : "-";
    rowElement.appendChild(createSimpleSpan("points", pointsAverage + " (" + row.pointsCount + ")"));
    rowElement.appendChild(createRowStateSpan(row, user));
    return rowElement;
}

function createSimpleSpan(className, text) {
    var spanElement = document.createElement("span");
    spanElement.className = className;
    spanElement.innerText = text;
    return spanElement;
}

function createChallenge(hostUserId, receivingUserId) {
    var location = window.location.protocol + "//" + window.location.host;
    sendRequest("GET", location + "/Challenge/Create/" + hostUserId + "/" + receivingUserId, function (){});
}

function approveChallenge(hostUserId, receivingUserId) {
    var location = window.location.protocol + "//" + window.location.host;
    sendRequest("GET", location + "/Challenge/Approve/" + hostUserId + "/" + receivingUserId, function (){});
}

function rejectChallenge(hostUserId, receivingUserId) {
    var location = window.location.protocol + "//" + window.location.host;
    sendRequest("GET", location + "/Challenge/Reject/" + hostUserId + "/" + receivingUserId, function (){});
}

function createRowStateSpan(row, user) {
    var rowState = row.rowState;
    var spanElement = document.createElement("span");
    spanElement.className = "state";
    if (rowState == "ME") {
        spanElement.className = spanElement.className + " me";
        spanElement.innerText = "me";
    } else if (rowState == "CHALLENGE") {
        spanElement = createSimpleSpan("state", "challenge");
        spanElement.className = spanElement.className + " challenge";
        spanElement.setAttribute("onclick", "createChallenge(" + user.id + ", " + row.userId + ")")
    } else if (rowState == "APPROVE_OR_REJECT") {
        var span1Element = createSimpleSpan("approve", "approve");
        span1Element.setAttribute("onclick", "approveChallenge(" + user.id + ", " + row.userId + ")")
        spanElement.appendChild(span1Element);
        var span2Element = createSimpleSpan("reject", "reject");
        span2Element.setAttribute("onclick", "rejectChallenge(" + user.id + ", " + row.userId + ")")
        spanElement.appendChild(span2Element);
    } else if (rowState == "APPROVED") {
        spanElement.className = spanElement.className + " approved";
        spanElement.innerText = "approved, go!!!";
    } else if (rowState == "REJECTED") {
        spanElement.className = spanElement.className + " rejected";
        spanElement.innerText = "rejected";
    } else if (rowState == "WAITING_FOR_RESPONSE") {
        spanElement.className = spanElement.className + " waiting-for-response";
        spanElement.innerText = "waiting for response...";
    }
    return spanElement;
}