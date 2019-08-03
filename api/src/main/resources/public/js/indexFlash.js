(function (_window) {

    var pageStart = 0;
    var flashlist = requestFlash();
    for (var i = 0; i < 5; i++) {
        // console.log(flashlist[i]);
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
            headers : {'x-auth-token': JSON.parse(localStorage['jwt'])},
            async: false,
            success: function (data) {
                flash = data;
            }
        });
        return flash;

    }


    //添加快讯
    function addFlash(flash) {
        var shareUrlWithUid = "" + window.location;
        $.ajax({
            type: "get",
            url: "/user/isLogin",
            async:false,
            success: function (result) {
                if (result.code === 0) {
                    if (result.data === "unLogin") {
                        shareUrlWithUid = "" + window.location;
                        console.log(shareUrlWithUid);
                    } else {

                        $.ajax({
                            url: "/share/genShareToken",
                            data: {
                                fid: flash.fid,
                                uid: result.data.id,
                                type: 2
                            },
                            async:false,
                            type: "get",
                            success: function (res) {

                                if (res.code === 0) {
                                    var token = res.data.share_token;

                                    console.log(token);
                                    shareUrlWithUid = window.location+"/shareFlashDetail?token=" + token;
                                }
                            }
                        })

                        // shareUrlWithUid = window.location+"/shareFlashDetail?token=" + result.data.id + "&fid=" + flash.fid;
                        console.log(shareUrlWithUid);

                    }
                }
                var date1 = new Date();
                var date2 = new Date(flash.addtime);
                var time = date1.getTime();
                var howMinute =  Math.floor((time - flash.addtime) / 1000) + "秒前";
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
                }else if (date1.getMinutes() - date2.getMinutes() > 0){
                    viewTime = date1.getMinutes() - date2.getMinutes() + "分钟前";
                }

                var flashItem = "<li>\n" +
                    "                                <div class=\"transitionBox\"><div class=\"transition\"></div></div>\n" +
                    "                                <div class=\"flashBox\" >\n" +
                    "                                    <a href=\"/views/front/flashDetail.html?id="+flash.fid+"\" class=\"flashBoxA\">\n" +
                    "                                    <p class=\"flash-title\">" + flash.title + "</p>\n" +
                    "                                    <div class=\"flash-detail\">\n" +
                    "                                        <p>" + flash.content + "</p>\n" +
                    "                                    </div>\n" +
                    "</a>" +
                    "                                    <div class=\"flash-footer\">\n" +
                    "                                        <div class=\"flash-footer-time\">" + viewTime + "</div>\n" +
                    "                                        <div class=\"flash-footer-look\">\n" +
                    "                                            <img class=\"flash-look\" src=\"../../public/imgs/k_03@3x.png\" alt=\"\">\n" +
                    "                                            <span class=\"flash-look-span\">" + flash.readnumber + "</span>\n" +
                    "                                        </div>\n" +
                    "                                        <div class=\"flash-footer-share\" onclick=\"showTheshare(this)\">\n" +
                    "                                            <img class=\"flash-share-img\" src=\"../../public/imgs/k_03.png\" alt=\"\">\n" +
                    "                                            <span class=\"flash-look-span\">分享</span>\n" +

                    "<div class=\"social-share share-component \" hidden>\n" +
                    "    <!--<a class=\"social-share-icon icon-qzone\" href=\"\"></a>-->\n" +
                    "<a class=\"social-share-icon icon-qq\" href=\"#\" onclick=\"shareToQq(' 可牛的分享','" + shareUrlWithUid + "' ,'" + flash.title + "');return false; \"></a>" +
                    // "    <a class=\"social-share-icon icon-qq\" href=\"#\" onclick='shareToQq(\"7cj\",\" + articleUrl + \",\"7cj\")'></a>\n" +
                    "    <a class=\"social-share-icon icon-weibo\" href=\"#\"\n" +
                    "       onclick=\"shareToXl('" + flash.title + " ','" + shareUrlWithUid + "' ,'" + window.location + "../../public/images/logo1.jpg');return false;\"></a>\n" +
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
                    "                                    location.href = \"/login\";\n" +
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

                    "                                    </div>\n" +
                    // "                                    </a>\n" +
                    "                                </div>\n" +
                    "                            </li>"


                // console.log(flash.addtime);
                $(".loadFlash").before(flashItem);
                $(".flashBoxA").click(
                    function () {
                        var fid = flash.fid;
                        $(this).setAttribute("href", "/flash/toReadFlash?fid=" + fid);
                    }
                )



//                 $(".flash-footer-share").click(function (event) {
//
//                     event.stopPropagation();
//
//                     console.log("flash-footer-share")
//
//                     var _this = this;
//
//                     $.ajax({
//                         type: "get",
//                         url: "/user/isLogin",
//                         success: function (result) {
//                             if (result.code === 0) {
//                                 if (result.data === "unLogin") {
//                                     location.href = "/login";
//
//                                 } else {
// console.log("share-component")
//                                     $(_this).siblings(".share-component").slideToggle();
//                                     //
//                                     //
//                                     // shareUrlWithUid = "" + window.location + "/credits/toReadFlashAddScore?uid=" + result.data.id + "&fid=" + flash.fid;
//                                     // console.log(shareUrlWithUid);
//
//                                 }
//                             }
//                         }
//                     });
//                 })


            }
        })

    }

    var transitionArr = document.getElementsByClassName("transition");
    console.log(transitionArr[transitionArr.length - 1]);
    for (var i = 0; i < transitionArr.length; i++) {
        transitionArr[i].isChange = false;
    }
    for (var j = 0; j < transitionArr.length; j++) {
        transitionArr[j].onclick = function () {
            if (this.isChange) {
                this.isChange = false;
            } else {
                this.isChange = true;
            }
            changeWind();
        }
    }
    // function isChangeFalse() {
    //
    // }
    //
    // isChangeFalse();
    changeWind();
    function changeWind() {
        for (var i = 0; i < transitionArr.length; i++) {
            if (!transitionArr[i].isChange) {
                transitionArr[i].style.transform = "rotate(-90deg)";
                transitionArr[i].parentNode.parentNode.getElementsByClassName("flash-detail")[0].style.display = "none";

            } else {
                transitionArr[i].style.transform = "rotate(0deg)";
                console.log(transitionArr[i].parentNode.parentNode.getElementsByClassName("flash-detail")[0]);
                transitionArr[i].parentNode.parentNode.getElementsByClassName("flash-detail")[0].style.display = "block";
            }
        }
    }

})(window);

$(function () {
    $("head").append($("    <link rel=\"stylesheet\" href=\"../../public/js/share/share.min.css\">" +
        "    <script src=\"../../public/js/qrcode.min.js\"></script>"));

});

