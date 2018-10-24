// Copyright (c) 2014 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

var password = "nopass";
var _e6515f433f2a7e6bb65db3981545413c;

function showAlert(text, waittime, type) { //success || alert
	waittime = waittime == null ? 1200 : waittime;
	$("#popupmessage").removeClass('alert-success').removeClass('alert-danger');
	$("#popupmessage").addClass('alert-' + (type == null || type == undefined ? 'success' : 'danger'));
	$("#popupmessage").text(text);
	$("#popupmessage").slideDown(250, () => {
		setTimeout(() => { $("#popupmessage").slideUp(250); }, waittime);
	});
}

function getCurrentTabUrl(callback) {
	var queryInfo = {
		active: true,
		currentWindow: true
	};

	chrome.tabs.query(queryInfo, function (tabs) {
		var tab = tabs[0];
		var url = tab.url;
		console.assert(typeof url == 'string', 'tab.url should be a string');
		callback(url);
	});
}

function load_options(callback) {
	chrome.storage.sync.get({ minlen: "16", needupercase: true, needspecialchar: true, _834392b2e51d87ceaf1f532a275c2b824fed163e: null },
		(options) => {
			_e6515f433f2a7e6bb65db3981545413c = options;
			console.log('OPTIONS:', options);
			callback();
		});
}
function save_options() {
	var options = _e6515f433f2a7e6bb65db3981545413c
	chrome.storage.sync.set(options, () => {
		showAlert('The salt was successfully saved !')
	});
}

function genSalt() {
	_e6515f433f2a7e6bb65db3981545413c._834392b2e51d87ceaf1f532a275c2b824fed163e = sha256(Math.random());
	save_options();
}



function genPassword(input) {
	var text = input;
	if (_e6515f433f2a7e6bb65db3981545413c._834392b2e51d87ceaf1f532a275c2b824fed163e == null) {
		genSalt();
	}
	password = sha256(_e6515f433f2a7e6bb65db3981545413c._834392b2e51d87ceaf1f532a275c2b824fed163e + text);
	

	/*TODO: 
		- Gérer les longueurs paramétrables (resalt le salt pour illimité longueur)
		- Gérer ajout d'une majuscule
		- Gérer ajout d'un caractère spécial
	*/
	password = password.substring(0, 16); //16 len
	document.getElementById('website').textContent = input;
	document.getElementById('password').textContent = password;

}

function geturl(url, callback) {
	var regex = /(www\.)*([\w\.\-]+)\//;
	var regex2 = /(www\.)*([\w\.\-]+)$/;
	var result = regex.exec(url);
	result = result == null ? regex2.exec(url) : result;
	callback(result[2]);
}

document.addEventListener('DOMContentLoaded', function () {
	getCurrentTabUrl(function (url) {

		var object = document.getElementById('autofill');
		object.addEventListener("click", function () {
			var mycode = "var research = document.getElementsByTagName('input'); \
		for(var i= 0; i < research.length; i++) \
			{	 								\
				if (research[i].type == \"password\") \
				{									\
					research[i].value = \"" + password.replace("\\", "\\\\").replace("\"", "\\\"") + "\" \
				;} 										\
			}										\
		";
			chrome.tabs.getSelected(null, function (tab) {
				chrome.tabs.executeScript(tab.id, { code: mycode }, function (response) {
					window.close();
					console.log(mycode);
				});
			});
		});

		var copypass = document.getElementById('copypass');
		copypass.addEventListener("click", (e) => {
			var password = document.getElementById('password').innerText.trim();
			var tmpel = document.createElement('textarea');
			tmpel.value = password;
			tmpel.setAttribute('style', 'opacity:0;');
			document.body.appendChild(tmpel);
			tmpel.select();
			document.execCommand('copy');
			document.body.removeChild(tmpel);
			e.preventDefault();
			showAlert('Password successfully copied in your clipboard !');
		});

		$('#manualinput').on("change keyup", () => {
			var a = $('#manualinput').val().trim();
			if (a.length > 0)
				genPassword(a);
		});

		$('#manualinput').on("paste", () => {
			setTimeout(() =>{
				var a = $('#manualinput').val().trim();
				if (a.length > 0)
					genPassword(a);
			}, 100);
		});

		load_options(() => {
			geturl(url, (url) => {
				genPassword(url);
			});
		});
	});
});


var sha256 = function sha256(r) { function o(r, o) { return r >>> o | r << 32 - o } for (var f, a, t = Math.pow, h = t(2, 32), n = "length", c = "", e = [], i = 8 * r[n], s = sha256.h = sha256.h || [], u = sha256.k = sha256.k || [], v = u[n], l = {}, g = 2; v < 64; g++)if (!l[g]) { for (f = 0; f < 313; f += g)l[f] = g; s[v] = t(g, .5) * h | 0, u[v++] = t(g, 1 / 3) * h | 0 } for (r += ""; r[n] % 64 - 56;)r += "\0"; for (f = 0; f < r[n]; f++) { if ((a = r.charCodeAt(f)) >> 8) return; e[f >> 2] |= a << (3 - f) % 4 * 8 } for (e[e[n]] = i / h | 0, e[e[n]] = i, a = 0; a < e[n];) { var k = e.slice(a, a += 16), d = s; for (s = s.slice(0, 8), f = 0; f < 64; f++) { var p = k[f - 15], w = k[f - 2], A = s[0], C = s[4], M = s[7] + (o(C, 6) ^ o(C, 11) ^ o(C, 25)) + (C & s[5] ^ ~C & s[6]) + u[f] + (k[f] = f < 16 ? k[f] : k[f - 16] + (o(p, 7) ^ o(p, 18) ^ p >>> 3) + k[f - 7] + (o(w, 17) ^ o(w, 19) ^ w >>> 10) | 0); (s = [M + ((o(A, 2) ^ o(A, 13) ^ o(A, 22)) + (A & s[1] ^ A & s[2] ^ s[1] & s[2])) | 0].concat(s))[4] = s[4] + M | 0 } for (f = 0; f < 8; f++)s[f] = s[f] + d[f] | 0 } for (f = 0; f < 8; f++)for (a = 3; a + 1; a--) { var S = s[f] >> 8 * a & 255; c += (S < 16 ? 0 : "") + S.toString(16) } return c };