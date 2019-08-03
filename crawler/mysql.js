var mysql  = require('mysql');

var connection = mysql.createConnection({
    host     : '47.93.226.47',
    user     : 'hang',
    password : 'hang!@#',
    port: '3306',
    database: 'architect'
});
connection.connect();

   var  addSql = 'INSERT INTO sw_answer(question_item_id,question_bank_id,name,' +
         'result,index_letter) ' +
         'VALUES(?,?,?,?,?)';
    var  addSqlParams = [1,2,3,4,5];
//增
    connection.query(addSql,addSqlParams,function (err, result) {
        if(err){
            console.log('[INSERT ERROR] - ',err.message);
            return;
        }

        console.log('--------------------------INSERT----------------------------');
        //console.log('INSERT ID:',result.insertId);
        console.log('INSERT ID:',result);
        console.log('-----------------------------------------------------------------\n\n');
    });

connection.end();