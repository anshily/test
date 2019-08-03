var http=require('http');
var querystring=require('querystring');

var postData="{args: {questionBankId: 44395},deviceinfo: 'Mozilla/5.0 (Linux; Android 8.0.0; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Mobile Safari/537.36',token: 'dreamtouch'}";
var options = {
  hostname: '47.97.189.68',
  port: 5102,
  path: '/front/api/errorQuestion/getQuestionDetail',
  method: 'POST',
  headers: {
    'Content-Type': 'content-type": "application/json',
    'Content-Length': Buffer.byteLength(postData)
  }
};

var req = http.request(options, (res) => {
  console.log(`状态码: ${res.statusCode}`);
  console.log(`响应头: ${JSON.stringify(res.headers)}`);
  res.setEncoding('utf8');
  res.on('data', (chunk) => {
    console.log(`响应主体: ${chunk}`);
  });
  res.on('end', () => {
    console.log('响应中已无数据。');
  });
});

req.on('error', (e) => {
  console.error(`请求遇到问题: ${e.message}`);
});
// 写入数据到请求主体
req.write(postData);
req.end();