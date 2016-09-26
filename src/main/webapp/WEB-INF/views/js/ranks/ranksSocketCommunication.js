var stompClient = null;

function initStompCommunication(userId) {
    var socket = new SockJS('/SockJS');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/UpdateEvent/' + userId, function(response){
            createRanksTable();
        });
        stompClient.subscribe('/UpdateEvent/All', function(response){
            createRanksTable();
        });
        stompClient.subscribe('/UpdateEvent/Chat', function(response){
            createChat(response);
        });
    });
}

function destroyStompCommunication() {
    stompClient.disconnect();
}