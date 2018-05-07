var webSocket;


function openWebSocket()
{
    webSocket = new WebSocket("ws://localhost:8080/stringConverter");
    webSocket.onopen = function()
    {
        console.log("Opened the Connection");
    };


    webSocket.onmessage = function(event)
    {
        console.log(event.data);
    }
}

function sendString()
{
    var input = document.getElementById("input").value;
    webSocket.send(input);
}

function closeWebSocket()
{
    WebSocket.close;
    webSocket.onclose = function(event)
    {
        console.log("Closed The Connection");
    };
}