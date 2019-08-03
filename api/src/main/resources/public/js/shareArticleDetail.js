// var OrderArry = document.getElementsByClassName('mattterOrder');
// function changeStyle(y) {
//     for(var i = 0;i < OrderArry.length;i++){
//         OrderArry[i].style.color ='#505050';
//         OrderArry[i].style.borderBottom = 0;
//     }
//     y.style.color = '#FF9933';
//     y.style.borderBottom = 'solid 1px  #FF9933';
// }

(function (_window) {
    var articleTitle = document.getElementById("articleTitle");
    var articleTime = document.getElementById("articleTime");
    var articleAuthor = document.getElementById("articleAuthor");
    var articleLook = document.getElementById("articleLook");
    var articleContent = document.getElementById("articleContent");
    var goodNumber = document.getElementById("goodNumber");
    var nextA = document.getElementById("nextA");
    var preA = document.getElementById("preA");
    var preSpan = document.getElementById("preSpan");
    var nextSPan = document.getElementById("nextSpan");
    var article = document.getElementsByClassName('articleContent');
    var goodBox = document.getElementsByClassName('goodBox')[0];
    var goodimg = document.getElementsByClassName('goodimg')[0];
    var PageStart = 0;
    // console.log(1111);

    var titleHtml = "title";
    var shareUserId;
    // // 获取URL中的ID
    // function getQueryString(name) {
    //     var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    //     var r = window.location.search.substr(1).match(reg);
    //     console.log(r);
    //     if (r != null)
    //         return unescape(r[2]);
    //     return null;
    // }
    // console.log(getQueryString("id"));
    // var aid = getQueryString("id");

    var aid = $("#movAid").html();
    var token = $("#movToken").html();

    $.ajax({
        type: "get",
        url: "/user/isLogin",
        success: function (result){
            if (result.data === "unLogin") {

                $.ajax({
                    url: "/credits/toReadArticleAddScoreByToken",
                    type:"get",
                    data: {
                        token: token
                    },
                    // dataType:"json",
                    success:function (data) {
                        console.log(data);
                    },
                    error:function (e) {
                        console.log(e);
                    }
                })
            }else {

                $.ajax({
                    url: "/credits/toReadArticleAddScoreByToken",
                    type:"get",
                    data: {
                        token: token,
                        readUid: result.data.id
                    },
                    // dataType:"json",
                    success:function (data) {
                        console.log(data);
                    },
                    error:function (e) {
                        console.log(e);
                    }
                })
            }
        }
    })

    console.log(aid);
    //请求文章详情
    $.ajax({
        url:"/article/readArticle?id="+aid,
        dataType:"json",
        type:"get",
        async: false,
        success:function (data) {
            console.log("dddddd");
            console.log(data);
            putData(data);
            article[0].style.opacity = "1";
        },
        error:function (e) {
            console.log(e);
        }
    });



    function putData(map) {
        //设置文章标题
        articleTitle.innerText = map.article.title;

        titleHtml = map.article.title;

        //设置上传时间
        console.log(map.article.addtime);
        if(document.body.clientWidth < 860){
            var d = new Date(map.article.addtime);
            articleTime.innerText = d.getFullYear()+"-"+d.getMonth()+"-"+d.getDate();
            // articleSource.innerText = ellipsis(map.article.source,13) ;
        }
        else {
            var d = new Date(map.article.addtime);
            articleTime.innerText = d.getFullYear()+"-"+d.getMonth()+"-"+d.getDate();
            // articleSource.innerText = "来源：" + map.article.source;
        }

        //设置作者
        console.log(map.article.author);
        if(map.article.author == null || map.article.author == ""){
            articleAuthor.innerText = "";
        }
        else{
            if(document.body.clientWidth < 860){
                articleAuthor.innerText = map.article.author.substring(0,10);
                // articleAuthor.innerText = ellipsis(map.article.author,13);
            }
            else{
                articleAuthor.innerText = map.article.author;
            }

        }
        //设置浏览量
        console.log(map.article.readnumber);
        articleLook.innerText = map.article.readnumber;

        // 设置文章内容
        articleContent.innerHTML = map.article.content;



        $.ajax({
            url: "/user/isLogin",
            type:"post",
            dataType: "json",
            // async: false,
            success:function (message) {
                console.log("mmmmm");
                // console.log(message);
                if(message.data != "unLogin"){

                    shareUserId = message.data.id;
                    //判断是否点过赞
                    $.ajax({
                        url:"/liker/isLike?aid="+aid,
                        type:"post",
                        dataType: "json",
                        async: false,
                        success:function (message) {
                            console.log(message);
                            if(message.data == "unlike"){
                                console.log("渲染未点赞页面");
                                goodimg.setAttribute("src","../../public/images/like.png");

                            }
                            if(message.data == "liked"){
                                console.log("渲染点赞页面");
                                goodimg.setAttribute("src","../../public/images/likeshow.png");
                            }
                        }
                    })
                }
            }
        })

        //设置赞的数量
        goodNumber.innerText = map.article.likenum;


    }

    //省略文字
    function ellipsis(str,length) {
        // console.log(element);
        // var str = element.innerText;
        if (str.length > length) {
            str = str.substring(0,length) + "...";
        }
        return str;
    }

    function testphone(num){

        // console.log("绑定成功")
        if (num.length == 11 && (checkPhone(num) == 1)){
            return true;
        }else {
            return false;
        }
        // else if ($("#phone").val().length > 11){
        //     layer.msg("请输入正确的手机号码!");
        // }
    }

    function checkPhone(num){
        var filter = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
        if (filter.test(num)) {
            return 1;
        }
    }

    requestHotNews();
    //设置热点文章
    function requestHotNews() {
        var hotImgs = document.querySelectorAll(".hotUl img");
        var hotAs = document.querySelectorAll(".hotUl a");
        var hotNewsList = requestNews("hot");
        for (var i = 0; i < hotImgs.length; i++){
            hotImgs[i].setAttribute("src",hotNewsList[i%hotImgs.length].coverimg);
            hotAs[i].setAttribute("href","/views/front/articleDetail.html?id="+ hotNewsList[i%hotImgs.length].aid);
            hotAs[i].innerHTML = "<p>" + hotNewsList[i%hotImgs.length].title+ "</p>"+"<p class='hotUl-times'>" + "2018-01-01" + "</p>";
        }
    }

    function requestNews() {
        var list;
        $.ajax({
            url:"/article/getHotArticle?pageStart=" + 0,
            type:"get",
            dateType:"json",
            async:false,
            success:function (map) {
                list = map;
            },
        });
        return list;
    }

    //设置点赞
    goodBox.onclick = function like() {
        // console.log("hhhhhh");
        //判断是否登录
        $.ajax({
            url: "/user/isLogin",
            type:"post",
            dataType: "json",
            async: false,
            success:function (message) {
                // console.log("mmmmm");
                // console.log(message);
                if(message.data != "unLogin"){
                    //判断是否点过赞
                    $.ajax({
                        url:"/liker/isLike?aid="+aid,
                        type:"post",
                        dataType: "json",
                        async: false,
                        success:function (message) {
                            // console.log(message);
                            if(message.data == "unlike"){
                                console.log("success");
                                goodNumber.innerText = parseInt(goodNumber.innerText) + 1;
                                goodimg.setAttribute("src","../../public/images/likeshow.png");
                                //点赞操作成功
                                $.ajax({
                                    url:"/liker/clickLike?id="+aid,
                                    type:"post",
                                    dataType: "json",
                                    async: false,
                                    success:function (message) {
                                        console.log(message);
                                    }
                                });
                            }
                            if(message.data == "liked"){
                                // console.log("fail");
                                goodNumber.innerText = parseInt(goodNumber.innerText) - 1;
                                goodimg.setAttribute("src","../../public/images/like.png");
                                //取消点赞操作成功
                                $.ajax({
                                    url:"/liker/cancelLike?aid="+aid,
                                    type:"post",
                                    dataType: "json",
                                    async: false,
                                    success:function (message) {
                                        console.log(message);
                                    }
                                });
                            }
                        }
                    })
                }
                else{
                    // console.log("eeee");
                    location.href = "/login?url="+window.location;
                }
            }
        })

    }




})(window);
