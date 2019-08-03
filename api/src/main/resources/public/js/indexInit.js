
//初始化分类以及新闻按钮
(function (_window) {

    var byTime = document.getElementById("byTime");
    var loadBox = document.getElementsByClassName("loadBox")[0];
    var slideUl = document.getElementsByClassName("slideUl")[0];
    var currentCatrgory = 1;
    var cs = requestCategory();
    var pageStart = 0;
    var as = requestArtilceByCategory(1);
    for (var i = 0; i < as.length; i++){
        addArticle(as[i]);
    }
    //最新按钮点击事件
    byTime.onclick = function (ev) {
        isFalse();
        this.isSeleced = true;
        changeColor(this);
        currentCatrgory = 1;
        pageStart = 0;
        hiddenJs();
        hiddenArticles();
        var articles = requestArtilceByCategory(1);
        for (var i = 0; i < articles.length; i++) {
            addArticle(articles[i]);
        }
    };
    for (var i = 0; i < cs.length; i++) {
        addCategory(cs[i]);
    }

    function addCategory(cs) {
        var button = document.createElement("button");
        button.innerText = cs.name;
        button.value = cs.id;
        button.setAttribute("class", "modules");
        //为每个分类添加点击事件
        button.onclick = function (e) {
            isFalse();
            this.isSeleced = true;
            changeColor(this);
            currentCatrgory = cs.cid;
            // console.log(cs.cid);
            pageStart = 0;
            hiddenArticles();
            hiddenJs();

            var articles = requestArtilceByCategory(cs.cid);
            // console.log(articles);
            if (currentCatrgory != 7) {
                for (var i = 0; i < articles.length; i++) {
                    addArticle(articles[i]);
                }
            } else {
                for (var i = 0; i < articles.length; i++) {
                    addJs(articles[i]);
                }
            }
        };
        slideUl.appendChild(button);

    }

    function requestCategory() {
        var cs;
        $.ajax({
            url: "/category/getAllCategory",
            type: "get",
            async: false,
            success: function (data) {
                console.log("/category/getAllCategory")
                cs = data;
            }
        });
        return cs;
    }

    function requestArtilceByCategory(category) {
        var articles;
        var URL;
        if (currentCatrgory == 1) {
            URL = "/front/article/findAllArticleByDESC?pageStart=" + pageStart;
        } else if (currentCatrgory == 7) {
            URL = "/front/findJs?pageStart=" + pageStart;
        } else {
            URL = "/front/findArticleByCategory?category=" + category + "&pageStart=" + pageStart;
        }
        // console.log(URL);
        $.ajax({
            url: URL,
            type: "get",
            dateType: "json",
            async: false,
            success: function (data) {
                articles = data;
            }
        });
        return articles;
    }

    function addArticle(e) {
        var allNews = document.getElementById("allNews");
        var loadBox = document.getElementsByClassName("loadBox")[0];
        var contentBox = document.createElement('div');
        contentBox.setAttribute("class", "contentBox");
        var span = document.createElement('span');//略缩图span
        var img = document.createElement('img');
        img.setAttribute("class", "newsPic");
        span.appendChild(img);
        var contentBrief = document.createElement('span');
        contentBrief.setAttribute("class", "contentBrief");
        var a = document.createElement('a');//标题a标签
        a.style.color = "black";
        var h2Title = document.createElement('h2');//标题标签
        a.appendChild(h2Title);
        var contentP = document.createElement('p');
        var smallSpan = document.createElement('span');
        smallSpan.setAttribute("class", "smallSpan");
        var contentUserSpan = document.createElement('span');
        contentUserSpan.setAttribute("class", "contentUserSpan");
        // var contentHeadPic = document.createElement('img');
        // contentHeadPic.setAttribute("class","contentHeadPic");
        var p = document.createElement('p');//name的p标签
        var timeSpan = document.createElement('span');
        var pageViewSpan = document.createElement('span');
        var commentSpan = document.createElement('span');
        contentBox.onclick = function (ev) {
            a.click();
        };
        contentBrief.appendChild(a);
        contentBrief.appendChild(contentP);
        contentBrief.appendChild(smallSpan);
        smallSpan.appendChild(contentUserSpan);
        smallSpan.appendChild(timeSpan);
        smallSpan.appendChild(pageViewSpan);
        // smallSpan.appendChild(commentSpan);
        // contentUserSpan.appendChild(contentHeadPic);
        contentUserSpan.appendChild(p);
        contentBox.appendChild(span);
        contentBox.appendChild(contentBrief);
        allNews.insertBefore(contentBox, loadBox);
        //设置新闻图片

        img.setAttribute("src", e.coverimg);
        //点击跳转到详情
        a.setAttribute("href", "/readArticle?id=" + e.aid);
        //标题
        h2Title.innerHTML = e.atitle;
        //内容简要
        if (document.body.clientWidth > 650) {
            contentP.innerHTML = e.brief;
        }
        //用户名字
        // var user = findUserByAId(e.uid);
        if (document.body.clientWidth > 650) {
            if (testphone(e.username)) {
                p.innerHTML = e.username.substring(0, 3) + "***" + e.username.substring(8, 11);
            } else {
                p.innerHTML = ellipsis("来源:" + e.source, 15);
            }
        } else {
            if (testphone(e.username)) {
                p.innerHTML = e.username.substring(0, 3) + "***" + e.username.substring(8, 11);
            } else {
                p.innerHTML = ellipsis(e.source,5);
            }
        }

        //发表时间
        if (document.body.clientWidth > 650) {
            timeSpan.innerHTML = e.addtime;
        } else {
            timeSpan.innerHTML = e.addtime.substring(0, 10);
        }

        //浏览次数
        pageViewSpan.innerHTML = e.readnumber + "次";
        //评价按钮
        commentSpan.innerHTML = "";
        //头像
        // contentHeadPic.setAttribute("src",e.header);
    }

    //检查手机号码
    function testphone(num) {

        // console.log("绑定成功")
        if (num.length == 11 && (checkPhone(num) == 1)) {
            return true;
        } else {
            return false;
        }
        // else if ($("#phone").val().length > 11){
        //     layer.msg("请输入正确的手机号码!");
        // }
    }

    function checkPhone(num) {
        var filter = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
        if (filter.test(num)) {
            return 1;
        }
    }

    //隐藏新闻

    function hiddenArticles() {
        var contentBoxArr = document.getElementsByClassName("contentBox");
        pageStart = 0;//重置分页数
        for (var i = 0; i < contentBoxArr.length; i++) {
            contentBoxArr[i].style.display = "none";//隐藏原有新闻
        }
    };
    loadBox.onclick = function (e) {
        // console.log(pageStart);
        pageStart++;
        var articles = requestArtilceByCategory(currentCatrgory);
        if (articles.length <= 0) {
            layer.msg("没有更多了");
        }
        if (currentCatrgory != 7) {
            for (var i = 0; i < articles.length; i++) {
                addArticle(articles[i]);
            }
        } else {
            for (var i = 0; i < articles.length; i++) {
                addJs(articles[i]);
            }
        }

    };

    function ellipsis(str, length) {
        // console.log(element);
        // var str = element.innerText;
        // console.log(str.length);
        if (str.length > length) {
            str = str.substring(0, length) + "...";
        }
        return str;
    }


    //初始化滑条颜色
    byTime.setAttribute("style", "border-bottom:2px solid #ff9d11;" +
        "color:#ff9d11;");

//添加是否被选中属性
    function isFalse() {
        var slideUlLiArr = document.querySelectorAll(".slideUl button");
        for (var i = 0; i < slideUlLiArr.length; i++) {
            slideUlLiArr[i].isSeleced = false;
        }
    }

    isFalse();
    byTime.isSeleced = true;


    //改变颜色
    function changeColor(_this) {
        var slideUlLiArr = document.querySelectorAll(".slideUl button");
        for (var j = 0; j < slideUlLiArr.length; j++) {
            slideUlLiArr[j].setAttribute("style", "border:none;");
            // slideUlLiArr[j].isSeleced = false;
        }
        _this.setAttribute("style", "border-bottom:2px solid #ff9d11;" +
            "color:#ff9d11;");
    }


    function addJs(list) {
        // console.log(list);
        // console.log(orderBy);
        var articleUrl = window.location + "/front/toReadFlash?id=" + list.aid;

        var js = "<div class=\"flashBox\"><div class=\"jsBox\">\n" +
            "        <div class=\"grade\"></div>\n" +
            "        <div class=\"time\">" + list.addtime.substring(11, 16) + "</div>\n" +
            "        <div class=\"intro\">\n" +
            "            <div class=\"contentJs\">\n" +
            "                <a target=\'_self\' class=\"livesb\" href=\"/front/toReadFlash?id="+list.aid+"\">"+list.atitle+"</a>\n" +
            "                <a target=\'_self\' class=\"livesbx\"  href=\"/front/toReadFlash?id="+list.aid+"\">"+list.acontent+"</a><br/>\n" +
            "            </div>\n" +
            "            <div class=\"font\">\n" +
            "                <ul class=\"fontUl\">\n" +
            "                    <li onclick=\"up(this," + list.udid + ")\"> <img class='upImg' src=\"../../public/imgs/up.svg\"> 利好<i>" + list.up + "</i></li>\n" +
            "                    <li onclick=\"down(this," + list.udid + ")\"><img class='downImg' src=\"../../public/imgs/down.svg\"> 利空 <i>" + list.down + "</i></li>\n" +
            "                    <li class=\'shareLi\' onclick=\"share(this," + list.aid + ")\"> <img  src=\"../../public/imgs/share.svg\"> 分享" +


            "<div class=\"share-component social-share\">\n" +
            "    <!--<a class=\"social-share-icon icon-qzone\" href=\"\"></a>-->\n" +
            "<a class=\"social-share-icon icon-qq\" href=\"#\" onclick=\"shareToQq('7cj的分享','" +articleUrl +"' ,'"+ list.atitle +"');return false; \"></a>" +
            // "    <a class=\"social-share-icon icon-qq\" href=\"#\" onclick='shareToQq(\"7cj\",\" + articleUrl + \",\"7cj\")'></a>\n" +
            "    <a class=\"social-share-icon icon-weibo\" href=\"#\"\n" +
            "       onclick=\"shareToXl('"+list.atitle+" ','" + articleUrl +"' ,'"+ window.location +"../../public/images/logo1.jpg');return false;\"></a>\n" +
            "    <a class=\"social-share-icon icon-wechat\" href=\"#\" onclick =\"createQrCode('"+articleUrl+"');return false;\"></a>\n" +
            // "<div id='qrcode'></div>" +
            "    <!--<div id=\"shareToWx\"></div>-->\n" +
            "    <!--<div id=\"shareToWx\" style=\"width:100px; height:100px; margin-top:15px;\"></div>-->\n" +
            // "    <div id=\"qrcode\" ></div>\n" +
            "</div>" +
            " <script>" +
            "    function shareToXl(title, url, picurl) {\n" +
            "        var sharesinastring = 'http://v.t.sina.com.cn/share/share.php?title=' + title + '&url=' + url + '&content=utf-8&sourceUrl=' + url;//+'&pic='+picurl;\n" +
            "        window.open(sharesinastring, 'newwindow');\n" +
            "    }\n" +
            "\n" +
            "    function shareToQq(title, url, desc) {\n" +
            "        // var shareString = 'http://connect.qq.com/widget/shareqq/index.html?url=你的分享网址&sharesource=qzone&title=你的分享标题&pics=你的分享图片地址&summary=你的分享描述&desc=你的分享简述';\n" +
            "        var shareString = \"http://connect.qq.com/widget/shareqq/index.html?url=\" + url + \"&sharesource=qzone&title=\" + title + \"&pics=你的分享图片地址&summary=来自7财经的分享&desc=\" + desc;\n" +
            "        window.open(shareString, 'newwindow');\n" +
            "    }" +
            "    function createQrCode(articleUrl){\n" +
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
            "            width: 128,\n" +
            "            height: 128,\n" +
            "            colorDark : \"#FF9D11\",\n" +
            "            colorLight : \"#FFF\",\n" +
            "            correctLevel : QRCode.CorrectLevel.H\n" +
            "        });  // 设置要生成二维码的链接\n" +
            "    }" +

            "</script>"


        // "<div class=\"bdsharebuttonbox\"><a href=\"#\" class=\"bds_more\" data-cmd=\"more\"></a><a href=\"#\" class=\"bds_qzone\" data-cmd=\"qzone\" title=\"分享到QQ空间\"></a><a href=\"#\" class=\"bds_tsina\" data-cmd=\"tsina\" title=\"分享到新浪微博\"></a><a href=\"#\" class=\"bds_tqq\" data-cmd=\"tqq\" title=\"分享到腾讯微博\"></a><a href=\"#\" class=\"bds_renren\" data-cmd=\"renren\" title=\"分享到人人网\"></a><a href=\"#\" class=\"bds_weixin\" data-cmd=\"weixin\" title=\"分享到微信\"></a></div>"+
        // "  <script>" +
        // " window._bd_share_config={\"common\":{\"bdSnsKey\":{},\n" +
        // "            \"bdText\":\"来自7财经的分享\",\n" +
        // "            \"bdMini\":\"2\",\n" +
        // "            \"bdMiniList\":false,\n" +
        // "            \"bdPic\":\"\",\n" +
        // "            \"bdStyle\":\"0\",\n" +
        // "            \"bdSize\":\"16\"},\n" +
        // "        \"share\":{},\n" +
        // "        \"image\":{\"viewList\":[\"qzone\",\"tsina\",\"tqq\",\"renren\",\"weixin\"],\"viewText\":\"分享到：\",\"viewSize\":\"16\"},\n" +
        // "        \"selectShare\":{\"bdContainerClass\":null,\"bdSelectMiniList\":[\"qzone\",\"tsina\",\"tqq\",\"renren\",\"weixin\"]}};\n" +
        // "    with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>";+


        "</li>\n" +

        "                </ul>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div></div>";
        $(".loadBox").before(js);
    }

    function hiddenJs() {
        var jsBoxs = document.getElementsByClassName("flashBox");
        pageStart = 0;
        // console.log(jsBoxs)
        if (jsBoxs == "" || jsBoxs == null || jsBoxs.length == 0) {
            return;
        }
        for (var i = 0; i < jsBoxs.length; i++) {
            jsBoxs[i].style.display = "none";
        }
    }
})(window);
$(function () {
    $("head").append($("    <link rel=\"stylesheet\" href=\"../../public/js/share/share.min.css\">" +
        "    <script src=\"../../public/js/qrcode.min.js\"></script>"));

    function shareToXl(title, url, picurl) {
        var sharesinastring = 'http://v.t.sina.com.cn/share/share.php?title=' + title + '&url=' + url + '&content=utf-8&sourceUrl=' + url;//+'&pic='+picurl;
        window.open(sharesinastring, 'newwindow');
    }

    function shareToQq(title, url, desc) {
        // var shareString = 'http://connect.qq.com/widget/shareqq/index.html?url=你的分享网址&sharesource=qzone&title=你的分享标题&pics=你的分享图片地址&summary=你的分享描述&desc=你的分享简述';
        var shareString = "http://connect.qq.com/widget/shareqq/index.html?url=" + url + "&sharesource=qzone&title=" + title + "&pics=你的分享图片地址&summary=来自7财经的分享&desc=" + desc;
        window.open(shareString, 'newwindow');
    }

    // shareToQq("7cj",window.location + "/front/toReadFlash?id=" + list.aid,"7cj")
// var url = window.location + "/front/toReadFlash?id=" + list.aid;
    // qrBox = $("<div id='qrcode'></div>")
    //
    // $("body").append(qrBox)
    // qrcode = new QRCode(document.getElementById("qrcode"),{
    //     text: window.location,
    //     width: 128,
    //     height: 128,
    //     colorDark : "#000000",
    //     colorLight : "#ffffff",
    //     correctLevel : QRCode.CorrectLevel.H
    // });  // 设置要生成二维码的链接

    function createQrCode(articleUrl) {

        layer.open({
            type: 1,
            skin: 'layui-layer-demo', //样式类名
            closeBtn: 0, //不显示关闭按钮
            anim: 2,
            shadeClose: true, //开启遮罩关闭
            content: "<div id='qrcode'></div>"
        });
        qrcode = new QRCode(document.getElementById("qrcode"), {
            text: window.location,
            width: 128,
            height: 128,
            colorDark: "#FF9D11",
            colorLight: "#ffffff",
            correctLevel: QRCode.CorrectLevel.H
        });  // 设置要生成二维码的链接
    }

    // layer.open({
    //     type: 1,
    //     skin: 'layui-layer-demo', //样式类名
    //     closeBtn: 0, //不显示关闭按钮
    //     anim: 2,
    //     shadeClose: true, //开启遮罩关闭
    //     content: '内容'
    // });
    $(".icon-wechat").hover(function () {
        qrcode.makeCode(window.location);
        $("#qrcode").show()
    }, function () {
        qrcode.clear(); // 清除代码
        $("#qrcode").hide()
    })
})