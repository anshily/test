(function () {
    //初始化轮播结构
    var list = requestNews("0");
    function requestNews(orderBy) {
        var newsList;
        var URL="/getBanner";
        $.ajax({
            url:URL,
            type:"get",
            dateType:"json",
            async:false,
            success:function (map) {
                newsList = map;
            },
        });
        return newsList;
    }

    var swiperContainer = document.getElementsByClassName("swiper-wrapper")[0];
    var fornum = 0;
    if (list.length >= 5){
        fornum = 5;
    } else {
        fornum = list.length;
    }
    for (var i = 0; i < fornum; i++){
        var bannerBox = document.createElement("div");
        bannerBox.setAttribute("class","swiper-slide");
        var bannerA = document.createElement("a");
        bannerA.setAttribute("href","/views/front/articleDetail.html?id="+list[i].aid);
        bannerA.setAttribute("class","bannerA");
        var bannerImg = document.createElement("img");
        bannerImg.setAttribute("class","swiper-img");
        // bannerImg.setAttribute("class","banner");
        bannerA.appendChild(bannerImg);
        bannerImg.setAttribute("src",list[i].coverimg);
        bannerBox.appendChild(bannerA);
        swiperContainer.appendChild(bannerBox);
    }
    //获取另外两张
    var adImageBox = document.querySelectorAll(".adImageBox img");
    for (var i = 0; i < adImageBox.length; i++){
        var index = (list.length-(i+1))%list.length;

        adImageBox[i].setAttribute("src",list[index].coverimg);
        adImageBox[i].parentNode.setAttribute("href","/views/front/articleDetail.html?id="+list[index].aid);
    }
    console.log("ddddddddddd");
    $(".appBox").css("display","block");
    //省略文字
    function ellipsis(str,length) {
        if (str.length > length) {
            str = str.substring(0,length) + "...";
        }
        return str;
    }
})();