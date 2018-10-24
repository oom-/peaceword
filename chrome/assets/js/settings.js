$(document).ready(() => {

    var _f0e5dda435cbf772ce575fc53eefd5e4d62e1824 = null;

    function save_options() {
        if ($('#lensetting').val() <= 0)
        {
            showAlert('Min len of password is 8', 1200, 1);
        }
        else
        {
            var options = {
                minlen: $('#lensetting').val(),
                needupercase: $('#needupercasecheckbox').prop('checked'),
                needspecialchar: $('#needspecialcharcheckbox').prop('checked'),
                _834392b2e51d87ceaf1f532a275c2b824fed163e : _f0e5dda435cbf772ce575fc53eefd5e4d62e1824
            };
            console.log('options: ', options);
            chrome.storage.sync.set(options, () => {
                showAlert('The settings was successfully saved !')
            });
        }
    }

    function load_options(callback)
    {   
        console.log(chrome.storage);
        chrome.storage.sync.get({minlen: "16", needupercase: true, needspecialchar: true, _834392b2e51d87ceaf1f532a275c2b824fed163e: null}, 
        (options) => {
            $('#lensetting').val(options.minlen);
            $('#needupercasecheckbox').prop('checked', options.needupercase);
            $('#needspecialcharcheckbox').prop('checked', options.needspecialchar);
            _f0e5dda435cbf772ce575fc53eefd5e4d62e1824 = options._834392b2e51d87ceaf1f532a275c2b824fed163e;
            if (_f0e5dda435cbf772ce575fc53eefd5e4d62e1824 == null)
                {
                    showAlert("Error: your salt don't exist",2000, 1);
                    $('#qrcode').html('Can\'t generate your QRCode:<br>Salt not exist');
                }
            else
            {
                var qrcodeimg = new QRCode($('#qrcode')[0], {
                    text: _f0e5dda435cbf772ce575fc53eefd5e4d62e1824,
                    width: 128,
                    height: 128,
                    colorDark: "#000000",
                    colorLight: "#ffffff",
                    correctLevel: QRCode.CorrectLevel.H
                });
            }
        });
    }

    function copyinclipboard(text)
    {
        var tmpel = document.createElement('textarea');
		tmpel.value = text;
		tmpel.setAttribute('style', 'opacity:0;');
		document.body.appendChild(tmpel);
		tmpel.select();
		document.execCommand('copy');
		document.body.removeChild(tmpel);
    }

    function showAlert(text, waittime, type) { //success || alert
        waittime = waittime == null  ? 1200 : waittime;
        $("#popupmessage").removeClass('alert-success').removeClass('alert-danger');
        $("#popupmessage").addClass('alert-' + (type == null || type == undefined ? 'success' : 'danger'));
        $("#popupmessage").text(text);
        $("#popupmessage").slideDown(250, () => {
            setTimeout(() => { $("#popupmessage").slideUp(250); }, waittime);
        });
    }



    $("#savebutton").click(() => {
        save_options();
    });

    $("#copysalt").click(() => {
        if (_f0e5dda435cbf772ce575fc53eefd5e4d62e1824 != null)
        {
            copyinclipboard(_f0e5dda435cbf772ce575fc53eefd5e4d62e1824);
            showAlert('Salt successfully copied in your clipboard !');
        }
        else
            showAlert('Error: your salt don\'t exist', 1200, 1);
    });

    load_options();
   
});