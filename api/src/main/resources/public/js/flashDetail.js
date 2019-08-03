(function (){

    var titleHtml = "title";
    var shareUserId;

    var time = document.getElementsByClassName('time')[0];
    var flashTitle = document.getElementById("title");
    var flashDetail = document.getElementById("flashDetail");
    var lookspan = document.getElementById("lookspan");
    var article = document.getElementsByClassName('articleContent')[0];
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
    $.ajax({
        url:"/flash/toReadFlash?id="+aid,
        type:"get",
        dataType:"json",
        success:function (data) {
            console.log(data);
            putData(data);
            article.style.opacity = "1";
        },
        error:function (e) {
            console.log(e);
        }
    })


    //添加快讯时间 标题 详情 浏览量
    function putData(map) {
        var d = new Date(map.addtime);
        var month = d.getMonth() + 1;
        var strDate = d.getDate();
        var hour = d.getHours();//获取小时
        var minutes = d.getMinutes();//获取分钟
        var addtime = month + "月" +strDate +"日 "+hour + ":" + minutes;
        //设置时间
        time.innerText = addtime;
        //设置快讯标题
        flashTitle.innerText = map.title;
        //设置快讯详情
        flashDetail.innerHTML = map.content;
        //设置浏览量
        lookspan.innerText = map.readnumber;



        $.ajax({
            url: "/user/isLogin",
            type:"post",
            dataType: "json",
            // async: false,
            success:function (message) {
                console.log("mmmmm");
                // console.log(message);
                if (message.data != "unLogin") {
                    shareUserId = message.data.id;

                }
            }})
    }

    //添加头部当前时间
    addCurrenttime();
    function addCurrenttime() {
        var show_day = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        var day = date.getDay();
        var weekToday = show_day[day];
        // console.log(day);
        // console.log(weekToday);
        // console.log(year);
        // console.log(month);
        // console.log(strDate);
        $(".main-top-login")[0].innerText = year + "年" + month + "月" +strDate +"日 " +weekToday;

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

    var shareUrlWithUid = "http://localhost:8080/credits/toReadFlashAddScore?fid=" + aid + "&uid=";

    console.log(shareUrlWithUid);


    var shareHtml = "<div class=\"social-share share-component \" hidden>\n" +
        "    <!--<a class=\"social-share-icon icon-qzone\" href=\"\"></a>-->\n" +
        "<a class=\"social-share-icon icon-qq\" href=\"#\" onclick=\"shareToQq(' 可牛的分享','" + shareUrlWithUid + "' ,'" + titleHtml + "');return false; \"></a>" +
        // "    <a class=\"social-share-icon icon-qq\" href=\"#\" onclick='shareToQq(\"7cj\",\" + articleUrl + \",\"7cj\")'></a>\n" +
        "    <a class=\"social-share-icon icon-weibo\" href=\"#\"\n" +
        "       onclick=\"shareToXl('可牛的分享','" + shareUrlWithUid + "' ,'" + window.location + "../../public/images/logo1.jpg');return false;\"></a>\n" +
        "    <a class=\"social-share-icon icon-wechat\" href=\"#\" onclick =\"createQrCode('" + shareUrlWithUid + "');return false;\"></a>\n" +
        // "<div id='qrcode'></div>" +
        "    <!--<div id=\"shareToWx\"></div>-->\n" +
        "    <!--<div id=\"shareToWx\" style=\"width:100px; height:100px; margin-top:15px;\"></div>-->\n" +
        // "    <div id=\"qrcode\" ></div>\n" +
        "</div>" +

        "                                        </div>\n" +



        " <script>" +
        "var uid;" +
        "                function showTheshare(_this) {\n" +
        "\n" +
        "                    $.ajax({\n" +
        "                        type: \"get\",\n" +
        "                        url: \"/user/isLogin\",\n" +
        "                        success: function (result) {\n" +
        "                            if (result.code === 0) {\n" +
        "                                if (result.data === \"unLogin\") {\n" +
        "                                    location.href = \"/login?url="+window.location+"\";\n" +
        "\n" +
        "                                } else {\n" +
        "uid = result.data.id;" +
        "                                    console.log(\"share-component\")\n" +
        "$(_this).children(\".share-component\");" +
        "                                    $(_this).children(\".share-component\").slideToggle();\n" +
        "\n" +
        "                                }\n" +
        "                            }\n" +
        "                        }\n" +
        "                    });\n" +
        "                }" +
        "    function shareToXl(title, url, picurl) {\n" +
        "        url = url+uid;console.log(url)\n" +
        "        var sharesinastring = 'http://v.t.sina.com.cn/share/share.php?title=' + title + '&url=\"' + url + '\"&content=utf-8&sourceUrl=' + url;//+'&pic='+picurl;\n" +
        "        window.open(sharesinastring, 'newwindow');\n" +
        "    }\n" +
        "\n" +
        "    function shareToQq(title, url, desc) {\n" +
        "url = url+uid;" +
        "        // var shareString = 'http://connect.qq.com/widget/shareqq/index.html?url=你的分享网址&sharesource=qzone&title=你的分享标题&pics=你的分享图片地址&summary=你的分享描述&desc=你的分享简述';\n" +
        "        var shareString = \"http://connect.qq.com/widget/shareqq/index.html?url=\" + url + \"&sharesource=qzone&title=\" + title + \"&pics=你的分享图片地址&summary=来自7财经的分享&desc=\" + desc;\n" +
        "        window.open(shareString, 'newwindow');\n" +
        "    }" +
        "    function createQrCode(articleUrl){\n" +
        "articleUrl = articleUrl+uid" +
        "        \n" +
        // "        qrcode.clear();" +
        // "          qrcode.makeCode(articleUrl);" +
        "        layer.open({\n" +
        "            type: 1,\n" +
        "title: '扫描二维码分享到微信'," +
        "            skin: 'layui-layer-demo', //样式类名\n" +
        "            closeBtn: 0, //不显示关闭按钮\n" +
        "            anim: 2,\n" +
        "            shadeClose: true, //开启遮罩关闭\n" +
        "            content: \"<div id='qrcode'></div>\"\n" +
        "        });\n" +
        "        qrcode = new QRCode(document.getElementById(\"qrcode\"),{\n" +
        "            text: articleUrl,\n" +
        "            width: 160,\n" +
        "            height: 160,\n" +
        "            colorDark : \"#2C323E\",\n" +
        "            colorLight : \"#FFF\",\n" +
        "            correctLevel : QRCode.CorrectLevel.H\n" +
        "        });  // 设置要生成二维码的链接\n" +
        "    }" +
        "\n" +

        "</script>";

    // $(".flash-footer-share").append(shareHtml);



}(window));
// $(function () {
//     $("head").append($("    <link rel=\"stylesheet\" href=\"../../public/js/share/share.min.css\">" +
//         "    <script src=\"../../public/js/qrcode.min.js\"></script>"));
//
//
// });







