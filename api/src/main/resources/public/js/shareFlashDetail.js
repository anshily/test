(function (){
    var time = document.getElementsByClassName('time')[0];
    var flashTitle = document.getElementById("title");
    var flashDetail = document.getElementById("flashDetail");
    var lookspan = document.getElementById("lookspan");
    var article = document.getElementsByClassName('articleContent')[0];

    var fid = $("#movFid").html();
    // var uid = $("#uid").html();
    var token = $("#movToken").html();
    // console.log(aid);
    // console.log("/credits/toReadFlashAddScore?fid="+aid+"&uid="+uid);
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
// 分享者增加积分
    $.ajax({
        type: "get",
        url: "/user/isLogin",
        success: function (result){
            if (result.data === "unLogin") {

                $.ajax({
                    url: "/credits/toReadFlashAddScoreByToken",
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
                    url: "/credits/toReadFlashAddScoreByToken",
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

    $.ajax({
        url: "/flash/toReadFlash?id="+fid,
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

}(window));


