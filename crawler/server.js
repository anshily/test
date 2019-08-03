var http = require('http');
var qs = require('querystring');

http.createServer(function (request, response) {

    // 发送 HTTP 头部 
    // HTTP 状态值: 200 : OK
    // 内容类型: text/plain
	
	// console.log(request.client)
	
	// if (hasBody(request)) {
    var buffers = [];
    request.on('data', function (chunk) {
      buffers.push(chunk);
    });
    request.on('end', function () {
      const POST = Buffer.concat(buffers).toString();
	  
	  console.log(POST);
    });
  //}
    response.writeHead(200, {'Content-Type': 'text/html'});

    // 发送响应数据 "Hello World"
    // response.end('Hello World\n');
	testReq(response);
}).listen(8888);

// 终端打印如下信息
console.log('Server running at http://127.0.0.1:8888/');

function testReq(resp){
	var postData=JSON.stringify({"args": {"questionBankId": "44395"},"deviceinfo": "Mozilla/5.0 (Linux; Android 8.0.0; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Mobile Safari/537.36","token": "dreamtouch"});
	var content = qs.stringify({
		args: {questionBankId: 44395},
		deviceinfo: "Mozilla/5.0 (Linux; Android 8.0.0; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Mobile Safari/537.36",
		token: "dreamtouch"
	});
	var options = {
	  hostname: '47.97.189.68',
	  port: 5102,
	  path: '/front/api/errorQuestion/getQuestionDetail',
	  method: 'POST',
	  headers: {
		'Content-Type': 'content-type": "application/json',
		'Content-Length': content.length
	  }
	};

	var req = http.request(options, (res) => {
	  console.log(`状态码: ${res.statusCode}`);
	  console.log(`响应头: ${JSON.stringify(res.headers)}`);
	  
	  var buffers1 = [];
	  var tmp = '';
	  res.setEncoding('utf8');
	  res.on('data', (chunk) => {
		// console.log(`响应主体: ${chunk}`);
		// return chunk;
		tmp += chunk;
		// buffers1.push(chunk);
		// resp.end(chunk);
	  });
	  res.on('end', () => {
		console.log('响应中已无数据。');
		// resp.end(Buffer.concat(buffers1).toString());
		
		resp.end(tmp);
	  });
	});

	req.on('error', (e) => {
	  console.error(`请求遇到问题: ${e.message}`);
	});
	// 写入数据到请求主体
	req.write(content);
	req.end();
}