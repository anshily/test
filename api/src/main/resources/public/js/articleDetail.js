

(function (_window) {
    var articleTitle = document.getElementById("articleTitle");
    var articleTime = document.getElementById("articleTime");
    var articleAuthor = document.getElementById("articleAuthor");
    var articleLook = document.getElementById("articleLook");
    var articleShare = document.getElementById("articleShare");
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

    // var titleHtml = "title";
    var shareUserId;
    // 获取URL中的ID
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        console.log(r);
        if (r != null)
            return unescape(r[2]);
        return null;
    }
    console.log(getQueryString("id"));
    var aid = getQueryString("id");
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
    //请求文章浏览量
    $.ajax({
        url:"/share/getShareNumber",
        dataType:"json",
        type:"get",
        async:false,
        data:{
            id:aid,
            type:1
        },
        success:function(e){
            console.log(e);
            //设置分享量
            articleShare.innerText = e.data.shareNumber;
        }
    });



    function putData(map) {
        //设置文章标题
        articleTitle.innerText = map.article.title;

        // titleHtml = map.article.title;

        //设置上传时间
        console.log(map.article.addtime);
        var date = new Date(map.article.addtime);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        if(month >= 1 && month <= 9){
            month = "0" + month;
        }
        if(day >= 1 && day <= 9){
            day = "0" + day;
        }
        var viewTime = year +"-"+ month +"-"+ day;

        if(document.body.clientWidth < 860){
            articleTime.innerText = viewTime;
            // articleSource.innerText = ellipsis(map.article.source,13) ;
        }
        else {
            articleTime.innerText = viewTime;
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
                                goodBox.style.borderColor = "#C6C6C6";
                                goodNumber.style.color = "#888888";
                                goodNumber.style.borderColor = "#C6C6C6";
                                goodimg.setAttribute("src","../../public/images/like.png");
                            }
                            if(message.data == "liked"){
                                console.log("渲染点赞页面");
                                goodBox.style.borderColor = "#2979FF";
                                goodNumber.style.color = "#2979FF";
                                goodNumber.style.borderColor = "#2979FF";

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
                                goodBox.style.borderColor = "#2979FF";
                                goodNumber.style.color = "#2979FF";
                                goodNumber.style.borderColor = "#2979FF";
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
                                goodBox.style.borderColor = "#C6C6C6";
                                goodNumber.style.color = "#888888";
                                goodNumber.style.borderColor = "#C6C6C6";
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
    //
    // var shareUrlWithUid = "http://localhost:8080/toReadArticleAddScoreByToken?aid="+aid+"&uid=";
    //
    // var shareHtml = "<div class=\"social-share share-component \" hidden>\n" +
    //     "    <!--<a class=\"social-share-icon icon-qzone\" href=\"\"></a>-->\n" +
    //     "<a class=\"social-share-icon icon-qq\" href=\"#\" onclick=\"shareToQq(' 可牛的分享','" + shareUrlWithUid + "' ,'" + titleHtml + "');return false; \"></a>" +
    //     // "    <a class=\"social-share-icon icon-qq\" href=\"#\" onclick='shareToQq(\"7cj\",\" + articleUrl + \",\"7cj\")'></a>\n" +
    //     "    <a class=\"social-share-icon icon-weibo\" href=\"#\"\n" +
    //     "       onclick=\"shareToXl('" + titleHtml + " ','" + shareUrlWithUid + "' ,'" + window.location + "../../public/images/logo1.jpg');return false;\"></a>\n" +
    //     "    <a class=\"social-share-icon icon-wechat\" href=\"#\" onclick =\"createQrCode('" + shareUrlWithUid + "');return false;\"></a>\n" +
    //     // "<div id='qrcode'></div>" +
    //     "    <!--<div id=\"shareToWx\"></div>-->\n" +
    //     "    <!--<div id=\"shareToWx\" style=\"width:100px; height:100px; margin-top:15px;\"></div>-->\n" +
    //     // "    <div id=\"qrcode\" ></div>\n" +
    //     "</div>" +
    //
    //     "                                        </div>\n" +
    //
    //
    //
    //     " <script>" +
    //     "var uid;" +
    //     "                function showTheshare(_this) {\n" +
    //     "\n" +
    //     "                    $.ajax({\n" +
    //     "                        type: \"get\",\n" +
    //     "                        url: \"/user/isLogin\",\n" +
    //     "                        success: function (result) {\n" +
    //     "                            if (result.code === 0) {\n" +
    //     "                                if (result.data === \"unLogin\") {\n" +
    //     "                                    location.href = \"/login?url="+window.location+"\";\n" +
    //     "\n" +
    //     "                                } else {\n" +
    //     "                                    console.log(\"share-component\")\n" +
    //     "$(_this).children(\".share-component\");" +
    //     "                                    $(_this).children(\".share-component\").slideToggle();\n" +
    //     "\n" +
    //     "                                }\n" +
    //     "                            }\n" +
    //     "                        }\n" +
    //     "                    });\n" +
    //     "                }" +
    //     "    function shareToXl(title, url, picurl) {\n" +
    //     "url = url+uid;" +
    //     "        var sharesinastring = 'http://v.t.sina.com.cn/share/share.php?title=' + title + '&url=' + url + '&content=utf-8&sourceUrl=' + url;//+'&pic='+picurl;\n" +
    //     "        window.open(sharesinastring, 'newwindow');\n" +
    //     "    }\n" +
    //     "\n" +
    //     "    function shareToQq(title, url, desc) {\n" +
    //     "url = url+uid;" +
    //     "        // var shareString = 'http://connect.qq.com/widget/shareqq/index.html?url=你的分享网址&sharesource=qzone&title=你的分享标题&pics=你的分享图片地址&summary=你的分享描述&desc=你的分享简述';\n" +
    //     "        var shareString = \"http://connect.qq.com/widget/shareqq/index.html?url=\" + url + \"&sharesource=qzone&title=\" + title + \"&pics=你的分享图片地址&summary=来自7财经的分享&desc=\" + desc;\n" +
    //     "        window.open(shareString, 'newwindow');\n" +
    //     "    }" +
    //     "    function createQrCode(articleUrl){\n" +
    //     "articleUrl = articleUrl+uid" +
    //     "        \n" +
    //     // "        qrcode.clear();" +
    //     // "          qrcode.makeCode(articleUrl);" +
    //     "        layer.open({\n" +
    //     "            type: 1,\n" +
    //     "title: '扫描二维码分享到微信'," +
    //     "            skin: 'layui-layer-demo', //样式类名\n" +
    //     "            closeBtn: 0, //不显示关闭按钮\n" +
    //     "            anim: 2,\n" +
    //     "            shadeClose: true, //开启遮罩关闭\n" +
    //     "            content: \"<div id='qrcode'></div>\"\n" +
    //     "        });\n" +
    //     "        qrcode = new QRCode(document.getElementById(\"qrcode\"),{\n" +
    //     "            text: articleUrl,\n" +
    //     "            width: 160,\n" +
    //     "            height: 160,\n" +
    //     "            colorDark : \"#2C323E\",\n" +
    //     "            colorLight : \"#FFF\",\n" +
    //     "            correctLevel : QRCode.CorrectLevel.H\n" +
    //     "        });  // 设置要生成二维码的链接\n" +
    //     "    }" +
    //     "\n" +
    //
    //     "</script>";
    //
    // $(".matter-foot-share").append(shareHtml);




})(window);

// $(function () {
//     $("head").append($("    <link rel=\"stylesheet\" href=\"../../public/js/share/share.min.css\">" +
//         "    <script src=\"../../public/js/qrcode.min.js\"></script>"));
//
//
// });