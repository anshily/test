var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://47.93.226.47:27017/questions";
 
MongoClient.connect(url, { useNewUrlParser: true }, function(err, db) {
  if (err) throw err;
  console.log("数据库已创建!");
  
  
  
    var dbo = db.db("questions");
    var myobj = { name: "菜鸟教程", url: "www.runoob" };
    dbo.collection("type").insertOne(myobj, function(err, res) {
        if (err) throw err;
        console.log("文档插入成功");
        db.close();
    });
/*  var dbase = db.db("questions");
    dbase.createCollection('type', function (err, res) {
        if (err) throw err;
        console.log("创建集合!");
        db.close();
    });
*/
});