(function($)
{
    var cometd = $.cometd;

    $(document).ready(function()
    {
        function _connectionEstablished()
        {
            $('#body').append('<div>CometD Connection Established</div>');
        }

        function _connectionBroken()
        {
            $('#body').append('<div>CometD Connection Broken</div>');
        }

        function _connectionClosed()
        {
            $('#body').append('<div>CometD Connection Closed</div>');
        }

        // Function that manages the connection status with the Bayeux server
        var _connected = false;
        function _metaConnect(message)
        {
            if (cometd.isDisconnected())
            {
                _connected = false;
                _connectionClosed();
                return;
            }

            var wasConnected = _connected;
            _connected = message.successful === true;
            if (!wasConnected && _connected)
            {
                _connectionEstablished();
            }
            else if (wasConnected && !_connected)
            {
                _connectionBroken();
            }
        }

        // Function invoked when first contacting the server and
        // when the server has lost the state of this client
        function _metaHandshake(handshake)
        {
            if (handshake.successful === true)
            {
                cometd.batch(function()
                {
                    cometd.subscribe('/hello', function(message)
                    {
                    	//实际编码当中，这里就是你接收到的消息，然后动态修改HTML,让数据动态显示在前台页面。
                        $('#body').append('<div>Server Says: ' + message.data.greeting + '</div>');
                    });
                    // Publish on a service channel since the message is for the server only
                    cometd.publish('/service/hello', { name: 'World' });
                });
            }
        }

        // Disconnect when the page unloads
        $(window).unload(function()
        {
            cometd.disconnect(true);
        });

        var cometURL = 'http' + "//" + 'localhost:8080' + '/relationship' + "/rest/cometd";
        cometd.configure({
            url: cometURL,
            logLevel: 'debug'
        });

        cometd.addListener('/meta/handshake', _metaHandshake);
        cometd.addListener('/meta/connect', _metaConnect);

        cometd.handshake();
    });
})(jQuery);
