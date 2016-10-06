var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

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