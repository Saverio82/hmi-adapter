var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#visualization").show();
    }
    else {
        $("#visualization").hide();
    }
    $("#message").html("");
}

function connect() {
    var socket = new SockJS('/hmi-adapter/hmi-adapter-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({'username': 'demo', 'password': 'password'}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendTopic() {
        stompClient.subscribe($("#topic").val(), function (response) {
            showMessage(response.body);
        });
}

function showMessage(message) {
    $("#message").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendTopic(); });
});