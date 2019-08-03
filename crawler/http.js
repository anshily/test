var request = require('request');

var requestData = {
          args: {questionBankId: 44395},
          deviceinfo: 'Mozilla/5.0 (Linux; Android 8.0.0; Pixel 2 XL Build/OPD1.170816.004) ' +
          'AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Mobile Safari/537.36',
          token: 'dreamtouch'}
request({
    url: "http://47.97.189.68:5102/front/api/errorQuestion/getQuestionDetail",
    method: "POST",
    json: true,
    headers: {
        "content-type": "application/json",
    },
    body: JSON.stringify({test: "request"})
}, function(error, response, body) {
    if (!error && response.statusCode == 200) {
		console.log("response" + response)
		console.log("body: " + body);
    }
	console.log("response" + response.statusCode);
	console.log("body: " + body);
	console.log("error: " + error);
}); 