(function (_window) {
    var pageStart = 0;
    var loadBox = document.getElementById('loadBox');
    var flashlist = requestFlash();
    for (var i = 0; i < flashlist.length; i++) {
        console.log(flashlist[i]);
        addFlash(flashlist[i]);
    }

    //请求快讯
    function requestFlash() {
        var flash;
        var URL = "/flash/getFlashList" + "?pageStart=" + pageStart;
        console.log(URL);
        $.ajax({
            url: URL,
            type: "get",
            dataType: "json",
            async: false,
            success: function (data) {
                flash = data;
            }
        });
        return flash;

    }

    addCurrenttime();

    function addCurrenttime() {
        var show_day = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
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
        $(".main-top-login")[0].innerText = year + "年" + month + "月" + strDate + "日 " + weekToday;

    }

    //加载更多快讯
    loadBox.onclick = function (e) {
        pageStart++;
        //加载更多快讯
        var flash = requestFlash();
        if (flash.length <= 0) {
            layer.msg("没有更多了");
        } else {
            for (var i = 0; i < flash.length; i++) {
                addFlash(flash[i]);
            }
        }

    };

    //添加快讯
    function addFlash(list) {

        var shareUrlWithUid = "" + window.location;

        $.ajax({
            type: "get",
            url: "/user/isLogin",
            async: false,
            success: function (result) {

                if (result.code === 0) {
                    if (result.data === "unLogin") {
                        shareUrlWithUid = "" + window.location;
                        console.log(shareUrlWithUid);
                    } else {

                        $.ajax({
                            url: "/share/genShareToken",
                            data: {
                                fid: list.fid,
                                uid: result.data.id,
                                type: 2
                            },
                            async: false,
                            type: "get",
                            success: function (res) {

                                if (res.code === 0) {
                                    var token = res.data.share_token;

                                    console.log(token);
                                    shareUrlWithUid = window.location + "/../shareFlashDetail?token=" + token;
                                }
                            }
                        })

                        // shareUrlWithUid = window.location+"/shareFlashDetail?token=" + result.data.id + "&fid=" + flash.fid;
                        console.log(shareUrlWithUid);

                    }
                }

                var date1 = new Date();
                var date2 = new Date(list.addtime);
                var time = date1.getTime();
                var howMinute = Math.floor((time - list.addtime) / 1000) + "秒前";
                var viewTime = howMinute;
                //设置显示格式
                if (date1.getMonth() - date2.getMonth() > 0) {
                    viewTime = date1.getMonth() - date2.getMonth() + "月前";
                } else if (date1.getDate() - date2.getDate() > 7) {
                    viewTime = parseInt((date1.getDate() - date2.getDate()) / 7) + "周前";
                } else if (date1.getDate() - date2.getDate() > 0) {
                    viewTime = date1.getDate() - date2.getDate() + "天前";
                } else if (date1.getHours() - date2.getHours() > 0) {
                    viewTime = date1.getHours() - date2.getHours() + "小时前";
                } else if (date1.getMinutes() - date2.getMinutes() > 0) {
                    viewTime = date1.getMinutes() - date2.getMinutes() + "分钟前";
                }

                var articleUrl = window.location + "/views/front/flashDetail.html?id=" + list.fid;
                console.log(articleUrl);

                var flash = "<div class=\"flashBox\">\n" +
                    "                <div class=\"jsBox\">\n" +
                    "                    \n" +
                    "                    <div class=\"grade\"></div>\n" +
                    "                    \n" +
                    "                    <div class=\"intro\">\n" +
                    "                        <div class=\"content\">\n" +
                    "                            <a target=\"_blank\" class=\"livesb\"  href=\"/views/front/flashDetail.html?id=" + list.fid + "\">" + list.title + "</a>\n" +
                    "                            <a target=\"_blank\" class=\"livesbx\" href=\"/views/front/flashDetail.html?id=" + list.fid + "\">" + list.content + "</a><br/>\n" +
                    "                        </div>\n" +
                    "\n" +
                    "                        <div class=\"flash-footer\">\n" +
                    "                            <div class=\"flash-footer-time\">" + viewTime + "</div>\n" +
                    "                            <div class=\"flash-footer-look\">\n" +
                    "                                <img class=\"flash-look\" src=\"../../public/images/浏览.png\" alt=\"\">\n" +
                    "                                <span>" + list.readnumber + "</span>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"flash-footer-share\" onclick=\"showTheshare(this)\">\n" +
                    "                                <img  class=\"flash-look\" src=\"../../public/images/分享.png\" alt=\"\">\n" +
                    "                                <span>分享</span>\n" +

                    "<div class=\"social-share share-component share\" style='display: none'>\n" +
                    "    <!--<a class=\"social-share-icon icon-qzone\" href=\"\"></a>-->\n" +
                    "<a class=\"social-share-icon icon-qq\" href=\"#\" onclick=\"shareToQq(' 可牛的分享','" + shareUrlWithUid + "' ,'" + list.title + "');return false; \"></a>" +
                    // "    <a class=\"social-share-icon icon-qq\" href=\"#\" onclick='shareToQq(\"7cj\",\" + articleUrl + \",\"7cj\")'></a>\n" +
                    "    <a class=\"social-share-icon icon-weibo\" href=\"#\"\n" +
                    "       onclick=\"shareToXl('" + list.title + " ','" + shareUrlWithUid + "' ,'" + window.location + "../../public/images/logo1.jpg');return false;\"></a>\n" +
                    "    <a class=\"social-share-icon icon-wechat\" href=\"#\" onclick =\"createQrCode('" + shareUrlWithUid + "');return false;\"></a>\n" +
                    // "<div id='qrcode'></div>" +
                    "    <!--<div id=\"shareToWx\"></div>-->\n" +
                    "    <!--<div id=\"shareToWx\" style=\"width:100px; height:100px; margin-top:15px;\"></div>-->\n" +
                    // "    <div id=\"qrcode\" ></div>\n" +
                    "</div>" +

                    "                                        </div>\n" +


                    " <script>" +
                    "                function showTheshare(_this) {\n" +
                    "\n" +
                    "                    $.ajax({\n" +
                    "                        type: \"get\",\n" +
                    "                        url: \"/user/isLogin\",\n" +
                    "                        success: function (result) {\n" +
                    "                            if (result.code === 0) {\n" +
                    "                                if (result.data === \"unLogin\") {\n" +
                    "                                    location.href = \"/login?url=\"+\""+window.location+"\";\n" +
                    "\n" +
                    "                                } else {\n" +
                    "                                    console.log(\"share-component\")\n" +
                    "                                    $(_this).children(\".share-component\").slideToggle();\n" +
                    "\n" +
                    "                                }\n" +
                    "                            }\n" +
                    "                        }\n" +
                    "                    });\n" +
                    "                }" +
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
                    "            width: 160,\n" +
                    "            height: 160,\n" +
                    "            colorDark : \"#2C323E\",\n" +
                    "            colorLight : \"#FFF\",\n" +
                    "            correctLevel : QRCode.CorrectLevel.H\n" +
                    "        });  // 设置要生成二维码的链接\n" +
                    "    }" +
                    "\n" +

                    "</script>" +


                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n"

                $(".load").before(flash);

            }
        })
    }


    requestHotNews();

    //设置热点文章
    function requestHotNews() {
        var hotImgs = document.querySelectorAll(".hotUl img");
        var hotAs = document.querySelectorAll(".hotUl a");
        var hotNewsList = requestNews("hot");
        for (var i = 0; i < hotImgs.length; i++) {
            hotImgs[i].setAttribute("src", hotNewsList[i % hotImgs.length].coverimg);
            hotAs[i].setAttribute("href", "/views/front/articleDetail.html?id=" + hotNewsList[i % hotImgs.length].aid);
            hotAs[i].innerHTML = "<p>" + hotNewsList[i % hotImgs.length].title + "</p>" + "<p class='hotUl-times'>" + "2018-01-01" + "</p>";
        }
    }

    function requestNews() {
        var list;
        $.ajax({
            url: "/article/getHotArticle?pageStart=" + 0,
            type: "get",
            dateType: "json",
            async: false,
            success: function (map) {
                list = map;
            },
        });
        return list;
    }
})(window)

// $(function () {
//     $("head").append($("    <link rel=\"stylesheet\" href=\"../../public/js/share/share.min.css\">" +
//         "    <script src=\"../../public/js/qrcode.min.js\"></script>"));
//
//     function shareToXl(title, url, picurl) {
//         var sharesinastring = 'http://v.t.sina.com.cn/share/share.php?title=' + title + '&url=' + url + '&content=utf-8&sourceUrl=' + url;//+'&pic='+picurl;
//         window.open(sharesinastring, 'newwindow');
//     }
//
//     function shareToQq(title, url, desc) {
//         // var shareString = 'http://connect.qq.com/widget/shareqq/index.html?url=你的分享网址&sharesource=qzone&title=你的分享标题&pics=你的分享图片地址&summary=你的分享描述&desc=你的分享简述';
//         var shareString = "http://connect.qq.com/widget/shareqq/index.html?url=" + url + "&sharesource=qzone&title=" + title + "&pics=你的分享图片地址&summary=来自7财经的分享&desc=" + desc;
//         window.open(shareString, 'newwindow');
//     }
//
//     // shareToQq("7cj",window.location + "/front/toReadFlash?id=" + list.aid,"7cj")
// // var url = window.location + "/front/toReadFlash?id=" + list.aid;
//     // qrBox = $("<div id='qrcode'></div>")
//     //
//     // $("body").append(qrBox)
//     // qrcode = new QRCode(document.getElementById("qrcode"),{
//     //     text: window.location,
//     //     width: 128,
//     //     height: 128,
//     //     colorDark : "#000000",
//     //     colorLight : "#ffffff",
//     //     correctLevel : QRCode.CorrectLevel.H
//     // });  // 设置要生成二维码的链接
//
//     function createQrCode(articleUrl) {
//
//         layer.open({
//             type: 1,
//             skin: 'layui-layer-demo', //样式类名
//             closeBtn: 0, //不显示关闭按钮
//             anim: 2,
//             shadeClose: true, //开启遮罩关闭
//             content: "<div id='qrcode'></div>"
//         });
//         qrcode = new QRCode(document.getElementById("qrcode"), {
//             text: window.location,
//             width: 128,
//             height: 128,
//             colorDark: "#FF9D11",
//             colorLight: "#ffffff",
//             correctLevel: QRCode.CorrectLevel.H
//         });  // 设置要生成二维码的链接
//     }
//
//     // layer.open({
//     //     type: 1,
//     //     skin: 'layui-layer-demo', //样式类名
//     //     closeBtn: 0, //不显示关闭按钮
//     //     anim: 2,
//     //     shadeClose: true, //开启遮罩关闭
//     //     content: '内容'
//     // });
//     $(".icon-wechat").hover(function () {
//         qrcode.makeCode(window.location);
//         $("#qrcode").show()
//     }, function () {
//         qrcode.clear(); // 清除代码
//         $("#qrcode").hide()
//     })
// })

$(function () {
    $("head").append($("    <link rel=\"stylesheet\" href=\"../../public/js/share/share.min.css\">" +
        "    <script src=\"../../public/js/qrcode.min.js\"></script>"));

});