var axios = require("axios");
var fs = require("fs");
var MongoClient = require('mongodb').MongoClient;

var mysql  = require('mysql');

var connection = mysql.createConnection({
    host     : '47.93.226.47',
    user     : 'hang',
    password : 'hang!@#',
    port: '3306',
    database: 'architect'
});
connection.connect();
function saveToMongo(data){
	MongoClient.connect("mongodb://47.93.226.47:27017/questions", { useNewUrlParser: true }, function(err, db) {
	  if (err) throw err;
	  // console.log("数据库已创建!");
	  
		var dbo = db.db("questions");
		dbo.collection("type_c").insertOne(data, function(err, res) {
			if (err) throw err;
			console.log("文档插入成功");
			db.close();
		});
	});
}

// 异步读取
fs.readFile('questionA.json', function (err, data) {
   if (err) {
       return console.error(err);
   }
   // console.log("异步读取: " + data.toString());
   questionList = JSON.parse(data.toString())
   
   // console.log(questionList['obj']['questionBankId']);
/*   questionList['obj']['questionBankId'].forEach(item => {
	   console.log(item['questionBankId']);
	  
	   
	   setTimeout(() => {
		   getQuestionDetail(item['questionBankId']);
	   },1000);
   })
*/
   
   for(var i = 0; i < questionList['obj']['questionBankId'].length; i++){
	   var item = questionList['obj']['questionBankId'][i];
	   // setTimeout(() => {
       initDetail(item['questionBankId'],i);
	   // },1000*i);
   }
});

function initDetail(qid,i) {
    setTimeout(() =>{
        getQuestionDetail(qid);
    },i*1000)
}
 // 44395
 function getQuestionDetail(qid){
	 axios.post('http://47.97.189.68:5102/front/api/errorQuestion/getQuestionDetail', {
		args: {questionBankId: qid},
		deviceinfo: "Mozilla/5.0 (Linux; Android 8.0.0; Pixel 2 XL Build/OPD1.170816.004) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Mobile Safari/537.36",
		token: "dreamtouch"
	})
  .then(function (response) {
    console.log(qid);
    saveToMysql(response['data']['obj'])
	// saveToMongo(response['data']);
	// appendTofile(JSON.stringify(response['data']));
	// saveToFile(JSON.stringify(response['data']));
  })
  .catch(function (error) {
    console.log(error);
  });
 }
 function saveToMysql(data) {
    // console.log(data)
     console.log(data['questionTypeId'])
     // console.log(data['questionTypeId'] == 3)
 //   1类题且 父id为0 直接存储题目 取出答案遍历存储
     if (data['questionTypeId'] == 1){
         data['answer'].forEach(item => {
             saveAnswer(item);
         })
         saveQuestion(data)
     }

 //   2类题且 父id为0 直接存储题目 取出答案遍历存储
     if (data['questionTypeId'] == 2){
         data['answer'].forEach(item => {
             saveAnswer(item);
         })
         saveQuestion(data)
     }

     //  3类题且 父id为0 直接存储题目 取出答案遍历存储
     if (data['questionTypeId'] == 3){
         data['answer'].forEach(item => {
             saveAnswer(item);
         })
         saveQuestion(data)
     }

     //  6类题无答案 直接存储题目 取出子问题存储  并将子问题答案遍历存储
     if (data['questionTypeId'] == 6) {
         data['childQuestion'].forEach(item => {
             item['answer'].forEach(item1 => {
                 saveAnswer(item1);
             })
             saveChildQuestion(item);
         })
         saveQuestion(data);
     }

 }
 
 function saveQuestion(data) {
    // console.log(data)
    //  connection.connect();

     var  addSql = 'INSERT INTO sw_question(question_bank_id,question_type_id,question_title,' +
         'explanation,parent_id,question_bank_category_id,name,difficulty_degree,isclose,isdelete) ' +
         'VALUES(?,?,?,?,?,?,?,?,?,?)';
     var  addSqlParams = [data['questionBankId'], data['questionTypeId'],data['questionTitle'], data['explanation'],
         data['parentId'], data['questionBankCategoryId'],data['name'], data['difficultyDegree'],data['isClose'], data['isDelete']];
//增
     connection.query(addSql,addSqlParams,function (err, result) {
         if(err){
             console.log('[INSERT ERROR] - ',err.message);
             return;
         }

         console.log('--------------------------INSERT----------------------------');
         console.log('INSERT Question ID:',result.insertId);
         // console.log('INSERT ID:',result);
         console.log('-----------------------------------------------------------------\n\n');
     });

     // connection.end();
 }

function saveChildQuestion(data) {
    // connection.connect();

    var  addSql = 'INSERT INTO sw_question(question_bank_id,question_type_id,question_title,' +
        'explanation,parent_id) ' +
        'VALUES(?,?,?,?,?)';
    var  addSqlParams = [data['questionBankId'], data['questionTypeId'],data['questionTitle'], data['explanation'],
        data['parentId']];
//增
    connection.query(addSql,addSqlParams,function (err, result) {
        if(err){
            console.log('[INSERT ERROR] - ',err.message);
            return;
        }

        console.log('--------------------------INSERT----------------------------');
        console.log('INSERT CHILDQUESTION ID:',result.insertId);
        // console.log('INSERT ID:',result);
        console.log('-----------------------------------------------------------------\n\n');
    });

    // connection.end();
}
 
 function saveAnswer(data) {
     // connection.connect();

     var  addSql = 'INSERT INTO sw_answer(question_item_id,question_bank_id,name,' +
         'result,index_number,index_letter) ' +
         'VALUES(?,?,?,?,?,?)';
     var  addSqlParams = [data['questionItemId'], data['questionBankId'],data['name'],
         data['result'],data['index'], data['indexLetter']];
//增
     connection.query(addSql,addSqlParams,function (err, result) {
         if(err){
             console.log('[INSERT ERROR] - ',err.message);
             return;
         }

         console.log('--------------------------INSERT----------------------------');
         console.log('INSERT ANWSER ID:',result.insertId);
         // console.log('INSERT ID:',result);
         console.log('-----------------------------------------------------------------\n\n');
     });

     // connection.end();
 }
 function saveToFile(data){
	 fs.writeFile('questions.txt', data + '\r\n',  function(err) {
       if (err) {
          return console.error(err);
       }
       console.log("数据写入成功！");
     });
 }

 function appendTofile(data) {

     fs.open('questions.txt', 'a', function(err, fd) {
         if (err) {
             return console.error(err);
         }

         fs.write(fd, data + '\n', function(err){
             if (err){
                 console.log(err);
             }
             console.log('文件write成功！');
         });
// 关闭文件
         fs.close(fd, function(err){
             if (err){
                 console.log(err);
             }
             console.log('文件关闭成功！');
         });
     });

 }