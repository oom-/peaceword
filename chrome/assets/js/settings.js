$(document).ready(() => {

    var _f0e5dda435cbf772ce575fc53eefd5e4d62e1824 = null;

    function save_options() {
        if ($('#lensetting').val() < 8) {
            showAlert('Min len of password is 8', 1200, 1);
        }
        else {
            var options = {
                minlen: $('#lensetting').val(),
                needupercase: $('#needupercasecheckbox').prop('checked'),
                needspecialchar: $('#needspecialcharcheckbox').prop('checked'),
                _834392b2e51d87ceaf1f532a275c2b824fed163e: _f0e5dda435cbf772ce575fc53eefd5e4d62e1824._834392b2e51d87ceaf1f532a275c2b824fed163e
            };
            chrome.storage.sync.set(options, () => {
                showAlert('The settings was successfully saved !')
            });
        }
    }

    function save_only_salt(salt) {
        var options = {
            minlen: _f0e5dda435cbf772ce575fc53eefd5e4d62e1824.minlen,
            needupercase: _f0e5dda435cbf772ce575fc53eefd5e4d62e1824.needupercase,
            needspecialchar: _f0e5dda435cbf772ce575fc53eefd5e4d62e1824.needspecialchar,
            _834392b2e51d87ceaf1f532a275c2b824fed163e: salt
        };
        console.log('options: ', options);
        chrome.storage.sync.set(options, () => {
            showAlert('Salt successfully synchronized !')
        });
    }

    function load_options(callback) {
        console.log(chrome.storage);
        chrome.storage.sync.get({ minlen: "16", needupercase: true, needspecialchar: true, _834392b2e51d87ceaf1f532a275c2b824fed163e: null },
            (options) => {
                $('#lensetting').val(options.minlen);
                $('#needupercasecheckbox').prop('checked', options.needupercase);
                $('#needspecialcharcheckbox').prop('checked', options.needspecialchar);
                _f0e5dda435cbf772ce575fc53eefd5e4d62e1824 = options;
                if (_f0e5dda435cbf772ce575fc53eefd5e4d62e1824 == null) {
                    showAlert("Error: your salt don't exist", 2000, 1);
                    $('#qrcode').html('Can\'t generate your QRCode:<br>Salt not exist');
                }
                else {
                    var qrcodeimg = new QRCode($('#qrcode')[0], {
                        text: _f0e5dda435cbf772ce575fc53eefd5e4d62e1824._834392b2e51d87ceaf1f532a275c2b824fed163e,
                        width: 100,
                        height: 100,
                        colorDark: "#000000",
                        colorLight: "#ffffff",
                        correctLevel: QRCode.CorrectLevel.H
                    });
                }
            });
    }

    function copyinclipboard(text) {
        var tmpel = document.createElement('textarea');
        tmpel.value = text;
        tmpel.setAttribute('style', 'opacity:0;');
        document.body.appendChild(tmpel);
        tmpel.select();
        document.execCommand('copy');
        document.body.removeChild(tmpel);
    }

    function showAlert(text, waittime, type) { //success || alert
        waittime = waittime == null ? 1200 : waittime;
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
        if (_f0e5dda435cbf772ce575fc53eefd5e4d62e1824._834392b2e51d87ceaf1f532a275c2b824fed163e != null) {
            copyinclipboard(_f0e5dda435cbf772ce575fc53eefd5e4d62e1824._834392b2e51d87ceaf1f532a275c2b824fed163e);
            showAlert('Salt successfully copied!');
        }
        else
            showAlert('Error: your salt don\'t exist', 1200, 1);
    });

    $('#manualsalt').on("change paste keyup", () => {
        if ($('#manualsalt').val().trim().length > 0) {
            if ($("#syncbutton").hasClass("disabled")) {
                $("#syncbutton").removeClass("disabled");
            }
        }
        else {
            if (!$("#syncbutton").hasClass("disabled")) {
                $("#syncbutton").addClass("disabled");
            }
        }
    });

    $("#syncbutton").click(() => {
        if (!$("#syncbutton").hasClass("disabled")) {
            if ($('#manualsalt').val().trim().length > 0) {
                save_only_salt($('#manualsalt').val().trim());
            }
            else {
                showAlert('Error: your salt need at least 1 character', 1200, 1);
            }
        }
    });

    load_options();

});