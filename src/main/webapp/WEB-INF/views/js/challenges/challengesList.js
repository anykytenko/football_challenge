var myId; // user id
var users;

function initChallengesPage(userId) {
    createChallengesList();
    initStompCommunication(userId);
    createChat();
    initAddChallengeButton();
    myId = userId;
}

function initAddChallengeButton() {
    fillUsers();
    var addChallengeElement = document.getElementById("add-challenge");
    if (addChallengeElement != null) {
        addChallengeElement.onclick = function () {
            openEditPopup(null, "", "", -1, -1, -1, -1);
        }
    }
}

function fillUsers() {
    var location = window.location.protocol + "//" + window.location.host;
    sendRequest("GET", location + "/REST/User", function(status, responseText) {
        users = JSON.parse(responseText);
    });
}

function createChallengesList() {
    var location = window.location.protocol + "//" + window.location.host;
    sendRequest('GET', location + '/Reports/ChallengesList', buildList);
}

function createList(title, className, challenges) {
    var blockElement = document.createElement("div");
    blockElement.className = "block " + className;
    blockElement.appendChild(createSimpleSpan("title", title));
    var rowsContainer = document.createElement("div");
    rowsContainer.className = "rows";
    blockElement.appendChild(rowsContainer);
    if (challenges.length == 0) {
        blockElement.appendChild(createSimpleSpan("no-challenges", "There is no items in this list"));
    } else {
        for (var i = 0; i < challenges.length; i++) {
            rowsContainer.appendChild(createRowElement(i + 1, challenges[i]));
        }
    }
    document.body.onkeydown = checkKeyForClosingPopup;
    return blockElement;
}

function checkKeyForClosingPopup(event) {
    if (event.keyCode == 27) {
        closeEditPopup();
    }
}

function createRowElement(number, challengeRow) {
    var divElement = document.createElement("div");
    divElement.className = "row";
    var myChallengeStyle = "";
    if (challengeRow.hostUser.id == myId || challengeRow.receivingUser.id == myId) {
        myChallengeStyle = "my-challenge";
    }
    divElement.appendChild(createSimpleSpan("host-user", challengeRow.hostUser.userName));
    var team1Goals = "";
    var team2Goals = "";
    if (challengeRow.team1Goals != 0 || challengeRow.team2Goals != 0) {
        team1Goals = challengeRow.team1Goals;
        team2Goals = challengeRow.team2Goals;
    }
    divElement.appendChild(createInput("team1-goals", team1Goals));
    divElement.appendChild(createInput("team2-goals", team2Goals));
    divElement.appendChild(createSimpleSpan("receiving-user", challengeRow.receivingUser.userName));
    var editButton = createSimpleSpan("edit-button btn-default", "<span>Edit</span>");
    editButton.onclick = function() {
        openEditPopup(challengeRow.challengeId, challengeRow.team1Goals, challengeRow.team2Goals,
            challengeRow.hostUser.id, challengeRow.otherUser1.id, challengeRow.receivingUser.id,
            challengeRow.otherUser2.id);
    };
    divElement.appendChild(editButton);
    var otherUser1Name = "unassigned";
    var otherUser2Name = "unassigned";
    if (challengeRow.otherUser1 != null) {
        otherUser1Name = challengeRow.otherUser1.userName;
        if (challengeRow.otherUser1.id == myId) {
            myChallengeStyle = "my-challenge";
        }
    }
    divElement.appendChild(createSimpleSpan("other-user1", otherUser1Name));
    divElement.appendChild(createAssignButton(challengeRow));
    if (challengeRow.otherUser2 != null) {
        otherUser2Name = challengeRow.otherUser2.userName;
        if (challengeRow.otherUser2.id == myId) {
            myChallengeStyle = "my-challenge";
        }
    }
    divElement.appendChild(createSimpleSpan("other-user2", otherUser2Name));
    if (challengeRow.otherUser1 != null && challengeRow.otherUser2 != null) {
        divElement.className = divElement.className + " ready";
        if (challengeRow.otherUser1.id == myId || challengeRow.otherUser2.id == myId) {
            myChallengeStyle = "my-challenge";
        }
    }
    divElement.className = divElement.className + " " + myChallengeStyle;
    return divElement;
}

function createAssignButton(challengeRow) {
    var assignButton = createSimpleSpan("btn-default assign-me", "Assign me");
        if (challengeRow.receivingUser.id == myId || challengeRow.hostUser.id == myId ||
        (challengeRow.otherUser1 != null && challengeRow.otherUser1.id == myId) ||
        (challengeRow.otherUser2 != null && challengeRow.otherUser2.id == myId)) {
        assignButton.className = assignButton.className + " transparent inactive";
    }
    if (challengeRow.otherUser1 != null && challengeRow.otherUser2 != null) {
        assignButton.className = assignButton.className + " transparent inactive";
    }
    assignButton.onclick = function () {
        assignButton.parentNode.removeChild(assignButton);
        assignMe(challengeRow.challengeId, myId);
    };
    return assignButton;
}

function assignMe(challengeId, userId) {
    var location = window.location.protocol + "//" + window.location.host;
    sendRequest("GET", location + "/Challenge/Assign/" + challengeId + "/" + userId, function (){});
}

function createPopupContent(challengeRow, team1GoalsInput, team2GoalsInput) {
    var divElement = document.createElement("div");
    divElement.className = "content";
    var userId = challengeRow.hostUser != null ? challengeRow.hostUser.id : -1;
    divElement.appendChild(createCombobox("host-user", userId, challengeRow));
    divElement.appendChild(team1GoalsInput);
    divElement.appendChild(team2GoalsInput);
    var editButton = createSimpleSpan("edit-button btn-default", "<span>Edit</span>");
    editButton.onclick = function() {
        openEditPopup(challengeRow.challengeId, challengeRow.team1Goals, challengeRow.team2Goals,
                        challengeRow.hostUser.id, challengeRow.otherUser1.id, challengeRow.receivingUser.id,
                        challengeRow.otherUser2.id);
    };
    divElement.appendChild(editButton);
    userId = challengeRow.receivingUser != null ? challengeRow.receivingUser.id : -1;
    divElement.appendChild(createCombobox("receiving-user", userId, challengeRow));
    userId = challengeRow.otherUser1 != null ? challengeRow.otherUser1.id : -1;
    divElement.appendChild(createCombobox("other-user1", userId, challengeRow));
    userId = challengeRow.otherUser2 != null ? challengeRow.otherUser2.id : -1;
    divElement.appendChild(createCombobox("other-user2", userId, challengeRow));
    return divElement;
}

function createCombobox(className, userId, challengeRow) {
    var comboboxWrapper = document.createElement("div");
    var select = document.createElement("select");
    select.className = className;
    select.id = className + "-select";
    var option = document.createElement("option");
    option.innerText = "-";
    option.value = -1;
    option.setAttribute("selected", "");
    select.appendChild(option);
    for (var i = 0; i < users.length; i++) {
        option = document.createElement("option");
        option.innerText = users[i].userName;
        option.value = users[i].id;
        if (userId == users[i].id) {
            option.setAttribute("selected", "");
        }
        select.appendChild(option);
    }
    comboboxWrapper.appendChild(select);
    return comboboxWrapper;
}

var team1GoalsInput;
var team2GoalsInput;

function getUser(id) {
    for (var i = 0; i < users.length; i++) {
        if (users[i].id == id) {
            return users[i];
        }
    }
    return null;
}

function openEditPopup(challengeId, team1Goals, team2Goals, hostUserId, otherUser1Id, receivingUserId, otherUser2Id) {
    var hostUser = getUser(hostUserId);
    var receivingUser = getUser(receivingUserId);
    var otherUser1 = getUser(otherUser1Id);
    var otherUser2 = getUser(otherUser2Id);
    var challengeRow = {challengeId: challengeId, team1Goals: team1Goals, team2Goals: team2Goals,
                        hostUser: hostUser, otherUser1: otherUser1, receivingUser: receivingUser, otherUser2: otherUser2};
    var bodyElement = document.body;
    var popup = document.createElement("div");
    popup.id = "edit-challenge-popup";
    popup.className = "popup";
    if (challengeRow.team1Goals != 0 || challengeRow.team2Goals != 0) {
        team1GoalsInput = createInput("team1-goals", challengeRow.team1Goals);
        team2GoalsInput = createInput("team2-goals", challengeRow.team2Goals);
    } else {
        team1GoalsInput = createInput("team1-goals", "");
        team2GoalsInput = createInput("team2-goals", "");
    }
    popup.appendChild(createPopupContent(challengeRow, team1GoalsInput, team2GoalsInput));
    var saveButton = document.createElement("div");
    saveButton.className = "save-button btn-default";
    saveButton.innerHTML = "<span>Save</span>";
    saveButton.onclick = function() {
        var hostUserSelect = document.getElementById("host-user-select");
        var hostUserId = hostUserSelect.options[hostUserSelect.selectedIndex].value;
        var receivingUserSelect = document.getElementById("receiving-user-select");
        var receivingUserId = receivingUserSelect.options[receivingUserSelect.selectedIndex].value;
        var otherUser1Select = document.getElementById("other-user1-select");
        var otherUser1Id = otherUser1Select.options[otherUser1Select.selectedIndex].value;
        var otherUser2Select = document.getElementById("other-user2-select");
        var otherUser2Id = otherUser2Select.options[otherUser2Select.selectedIndex].value;
        var team1Goals = team1GoalsInput.value;
        var team2Goals = team2GoalsInput.value;
        saveChallenge(challengeId, hostUserId, otherUser1Id, receivingUserId, otherUser2Id, team1Goals, team2Goals);
    };
    popup.appendChild(saveButton);
    var cancelButton = document.createElement("div");
    cancelButton.className = "cancel-button btn-default";
    cancelButton.innerHTML = "<span>Cancel</span>";
    cancelButton.onclick = closeEditPopup;
    popup.appendChild(cancelButton);
    bodyElement.appendChild(popup);
}

function closeEditPopup() {
    var popup = document.getElementById("edit-challenge-popup");
    if (popup != null) {
        document.body.removeChild(popup);
    }
}

function saveChallenge(challengeId, hostUserId, otherUser1Id, receivingUserId, otherUser2Id, team1Goals, team2Goals) {
    var location = window.location.protocol + "//" + window.location.host;
    if (challengeId != null) {
        sendRequest("GET", location + "/Challenge/Close/" + challengeId + "/" + team1Goals + "/" + team2Goals, closeEditPopup);
    } else {
        sendRequest("GET", location + "/Challenge/Create/" + hostUserId + "/" + otherUser1Id + "/" + receivingUserId +
                            "/" + otherUser2Id + "/" + team1Goals + "/" + team2Goals, closeEditPopup);
    }
}

function createInput(className, text) {
    var spanElement = document.createElement("input");
    spanElement.className = className;
    spanElement.value = text;
    return spanElement;
}

function createSimpleSpan(className, text) {
    var spanElement = document.createElement("span");
    spanElement.className = className;
    spanElement.innerHTML = text;
    return spanElement;
}

function buildList(status, response) {
    var challengesListElement = document.getElementById("challenges-list");
    challengesListElement.innerHTML = "";
    var challengesListObject = JSON.parse(response);
    var activeChallenges = createList("Active Challenges", "active-challenges", challengesListObject.activeChallengeRows);
    var closedChallenges = createList("Closed Challenges", "closed-challenges", challengesListObject.closedChallengeRows);
    challengesListElement.appendChild(activeChallenges);
    challengesListElement.appendChild(closedChallenges);
    activeChallenges.scrollTop = activeChallenges.scrollHeight;
    closedChallenges.scrollTop = closedChallenges.scrollHeight;
}