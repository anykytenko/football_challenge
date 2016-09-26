function createChat(updateEvent) {
    sendRequest('GET', '/Reports/MessagesList', buildChat);
    if (updateEvent != null && !iSendMessage) {
        document.getElementById("notification").play();
    }
}

var input;
var userId;
var counter;
var iSendMessage = false;

function buildChat(status, response) {
    iSendMessage = false;
    var chatElement = document.getElementById("chat");
    chatElement.innerHTML = "";
    var responseDto = JSON.parse(response);
    userId = responseDto.user.id;
    var title = document.createElement("div");
    title.className = "title";
    title.innerText = "Communicator";
    chatElement.appendChild(title);
    var history = createMessagesHistoryBlock(responseDto);
    chatElement.appendChild(history);
    history.scrollTop = history.scrollHeight;
    chatElement.appendChild(createChatInputBlock());
    input.focus();
}

function createMessagesHistoryBlock(responseDto) {
    var history = document.createElement("div");
    history.className = "message-history";
    for (var i = 0; i < responseDto.messages.length; i++) {
        var style = "";
        var align = "left";
        if (responseDto.messages[i].user.id == responseDto.user.id) {
            style = "bold me";
            align = "right";
        }
        var text = responseDto.messages[i].text;
        var messageContent = "<div align='" + align + "' class='text'>" + text.replace(/\n/g, "<br>") +
            "</div><br/><br/>" + history.innerHTML;
        history.innerHTML =  "<span class='date-time " + style + "'>" + responseDto.messages[i].date + "</span> <span class='name " +
            style +"'>" + responseDto.messages[i].user.userName + "</span>" + (style.length == 0 ? ": " : "<br>") + messageContent;
    }
    return history;
}

function createChatInputBlock() {
    var block = document.createElement("div");
    block.className = "input-block";
    var sendButton = document.createElement("div");
    sendButton.innerHTML = "<span>Send</span>";
    sendButton.className = "send-button";
    if (input == null) {
        input = document.createElement("textarea");
        input.className = "message-input";
        input.setAttribute("maxlength", "500");
        input.onkeypress = inputOnKeyPress;
        input.oninput = function () {
            counter.innerText = (500 - input.value.length) + " symbols left";
        };
    }
    block.appendChild(input);
    block.appendChild(sendButton);
    counter = document.createElement("span");
    counter.className = "counter";
    counter.innerText = 500 + " symbols left";
    block.appendChild(counter);
    sendButton.onclick = function() {
        sendMessage(userId);
    };
    input.removeAttribute("readonly");
    return block;
}

function inputOnKeyPress(event) {
    if (event.keyCode == 13) {
        sendMessage();
    } else if (event.keyCode == 10 && event.ctrlKey == true) {
        input.value = input.value + "\n";
    }
}

function sendMessage() {
    iSendMessage = true;
    input.setAttribute("readonly", "true");
    var text = input.value.replace(/</g, "&lt;").replace(/>/g, "&gt;");
    stompClient.send("/Message/Create", {}, JSON.stringify({userId: userId, text: text}));
    input.value = "";
}