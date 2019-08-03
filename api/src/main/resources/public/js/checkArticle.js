

(function (_window) {
    var articleTitle = document.getElementById("articleTitle");
    var ifCheck = document.getElementById("ifCheck");
    var articleTime = document.getElementById("articleTime");
    var articleAuthor = document.getElementById("articleAuthor");
    var articleLook = document.getElementById("articleLook");
    var articleContent = document.getElementById("articleContent");
    var share = document.getElementsByClassName("matter-foot-share")[0];
    var reditor = document.getElementsByClassName("reditor")[0];

    // var goodNumber = document.getElementById("goodNumber");
    var article = document.getElementsByClassName('articleContent');
    // var goodBox = document.getElementsByClassName('goodBox')[0];
    // var goodimg = document.getElementsByClassName('goodimg')[0];
    // var PageStart = 0;
    // console.log(1111);

    var titleHtml = "title";
    // var shareUserId;

    var aid = $("#movAid").html();
    reditor.setAttribute("href","/reedit?id="+aid);
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
        reditor.style.display = "inline-block";
        share.style.display = "inline-block";
        articleTitle.innerText = map.article.title;
        //设置审核是否通过
        if (map.article.state == 1) {
            ifCheck.style.color = "#7bc549";
            ifCheck.innerText = "审核通过";
            reditor.style.display = "none";
        }
        if (map.article.state == 2) {
            ifCheck.style.color = "#7bc549";
            ifCheck.innerText = "待审核";
            reditor.style.display = "none";
            share.style.display = "none";
        }
        if (map.article.state == 3) {
            ifCheck.style.color = "#FF0000";
            ifCheck.innerText = "审核未通过";
            share.style.display = "none";

        }


        titleHtml = map.article.title;

        //设置上传时间
        console.log(map.article.addtime);
        if (document.body.clientWidth < 860) {
            var d = new Date(map.article.addtime);
            articleTime.innerText = d.getFullYear() + "-" + d.getMonth() + "-" + d.getDate();
            // articleSource.innerText = ellipsis(map.article.source,13) ;
        }
        else {
            var d = new Date(map.article.addtime);
            articleTime.innerText = d.getFullYear() + "-" + d.getMonth() + "-" + d.getDate();
            // articleSource.innerText = "来源：" + map.article.source;
        }

        //设置作者
        console.log(map.article.author);
        if (map.article.author == null || map.article.author == "") {
            articleAuthor.innerText = "";
        }
        else {
            if (document.body.clientWidth < 860) {
                articleAuthor.innerText = map.article.author.substring(0, 10);
                // articleAuthor.innerText = ellipsis(map.article.author,13);
            }
            else {
                articleAuthor.innerText = map.article.author;
            }

        }
        //设置浏览量
        console.log(map.article.readnumber);
        articleLook.innerText = map.article.readnumber;

        // 设置文章内容
        articleContent.innerHTML = map.article.content;

    }


    // //省略文字
    // function ellipsis(str,length) {
    //     // console.log(element);
    //     // var str = element.innerText;
    //     if (str.length > length) {
    //         str = str.substring(0,length) + "...";
    //     }
    //     return str;
    // }




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