function sendRequest(method, theUrl, onGetResponse) {
    var request = new XMLHttpRequest();
    request.open(method, theUrl, true);
    request.setRequestHeader('Accept', 'application/json');
    request.send();
    request.onreadystatechange = function () {
        if (request.readyState == 4) {
            onGetResponse(request.status, request.responseText)
        }
    }
}