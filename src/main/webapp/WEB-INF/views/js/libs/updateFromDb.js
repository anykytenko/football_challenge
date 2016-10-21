function initUpdateFromDbButton() {
    var updateFromDbElement = document.getElementById("update-from-db");
    if (updateFromDbElement != null) {
        updateFromDbElement.onclick = updateFromDb;
    }
}

function updateFromDb() {
    var location = window.location.protocol + "//" + window.location.host;
    sendRequest("GET", location + "/Support/Ui/Update", function(status, responseText) {});
}