(function (_window) {
    var pageStart = 0;
    var currentCatrgory;
    var cs = requestCategory();
    var loadBox = document.getElementById('loadBox');
    for (var i = 0; i < cs.length; i++) {
        addCategory(cs[i]);
    }
    if (document.body.clientWidth < 650){
        var f = {
            cid:-1,
            name:"快讯",
        };
        addCategory(f);
    }
    hideBlackNode();
    var spanArr = document.getElementsByClassName("categoryButton");
    for (var i = 0; i < spanArr.length; i++){
       if (spanArr[i].innerText == "观点"){
           spanArr[i].click();
       }
    }

    //请求分类列表
    function requestCategory() {
        var cs;
        $.ajax({
            url: "/category/getCategory",
            type: "get",
            async: false,
            success: function (data) {
                cs = data;
            }
        });
        return cs;
    }
    //请求文章列表通过不同的类型
    function requestArtilceByCategory(category) {
        var articles;
        var URL = "/article/getArticleByCategory?category=" + category + "&pageStart=" + pageStart;
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
    //请求快讯
    function requestJs(){
        var Js;
        var URL = "/flash/getFlashList"+"?pageStart=" + pageStart;
        $.ajax({
            url:URL,
            type:"get",
            dataType:"json",
            async:false,
            success: function (data) {
                Js = data;
            }
        });
        return Js;
    }
    //触发加载按钮加载更多文章
    loadBox.onclick = function (e) {
        pageStart++;
        //加载更多快讯
        if (currentCatrgory === -1) {
            var flash = requestJs();
            if(flash.length <= 0){
                layer.msg("没有更多了");
            }else {
                for (var i = 0; i < flash.length; i++) {
                    addJs(flash[i]);
                }
            }
        } else {
            //加载更多文章
            var articles = requestArtilceByCategory(currentCatrgory);
            if (articles.length <= 0) {
                layer.msg("没有更多了");
            }else{
                for (var j = 0; j < articles.length; j++) {
                    addArticles(articles[j]);
                }
            }

        }

    };

    //添加分类列表
    function addCategory(cs) {
        var li = document.createElement('li');
        var selectedSpan = document.createElement('span');
        selectedSpan.setAttribute("class","selectedSpan");
        var categoryButton = document.createElement('button');
        categoryButton.setAttribute("class","categoryButton");
        li.appendChild(selectedSpan);
        li.appendChild(categoryButton);
        categoryButton.innerText = cs.name;
        categoryButton.value = cs.cid;
        //分类类型被点击
        categoryButton.onclick = function (e) {
            hideBlackNode();
            changeAll();
            categoryButton.parentNode.firstChild.style.opacity = 1;
            categoryButton.style.color = '#2C323E';
            categoryButton.style.background = 'white';
            categoryButton.style.backgroundColor = 'white';
            categoryButton.style.fontFamily = "PingFangSC-Semibold";
            // isFalse();
            // this.isSeleced = true;
            currentCatrgory = cs.cid;
            pageStart = 0;
            hiddenArticles();
            hiddenJs();
            var articles;
            //请求快讯
            if (currentCatrgory == -1){
                //初始请求快讯
                articles = requestJs();
                for (var i = 0; i < articles.length; i++) {
                    addJs(articles[i]);
                }
            }else{
                //初始加载文章
                articles = requestArtilceByCategory(cs.cid);
                for (var j = 0; j < articles.length; j++) {
                    addArticles(articles[j]);
                }
            }

        };
        $(".categoryUl")[0].append(li);
    }
    //添加是否被选中属性
    // var slideUlLiArr = document.querySelectorAll(".categoryUl li");
    // function isFalse() {
    //
    //     for (var i = 0; i < slideUlLiArr.length; i++) {
    //         slideUlLiArr[i].isSeleced = false;
    //     }
    // }
    // isFalse();
    // slideUlLiArr[0].isSeleced = true;


    //改变所有分类按钮的颜色
    function changeAll() {
        var allArray = document.getElementsByClassName("categoryButton");
        for(var i = 0; i < allArray.length;i++){
            allArray[i].style.fontFamily = "PingFangSC-Regular";
            allArray[i].style.color = "#5D5F62";
        }
    }
    //隐藏黑点
    function  hideBlackNode() {
        var allSelect = document.querySelectorAll(".selectedSpan");
        for(var i = 0;i < allSelect.length;i++){
            allSelect[i].style.opacity = "0";
        }
    }
    //添加新闻
    function addNewsList(list){
        for (var i = 0; i < list.length; i++){
            addArticles(list[i]);
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
//增加新闻newsImg,newsDetailsLink,title,content,time,number标签
    function addArticles(e) {
        // console.log(e);
        var allNews = document.getElementById("allNews");
        var loadBox = document.getElementsByClassName("loadBox")[0];
        var contentBox = document.createElement('div');
        contentBox.setAttribute("class","contentBox");
        var span = document.createElement('span');//略缩图span
        span.setAttribute("class","artical-pic");
        var img = document.createElement('img');
        img.setAttribute("class","newsPic");
        span.appendChild(img);
        var contentBrief = document.createElement('span');
        contentBrief.setAttribute("class","contentBrief");
        var a = document.createElement('a');//标题a标签
        a.style.color = "black";
        var h2Title = document.createElement('h2');//标题标签
        a.appendChild(h2Title);
        var contentP = document.createElement('p');
        var smallSpan = document.createElement('div');
        smallSpan.setAttribute("class","smallSpan");//昵称 日期 浏览量的标签
        var contentUserSpan = document.createElement('span');
        contentUserSpan.setAttribute("class","contentUserSpan");
        var p = document.createElement('p');//name的p标签
        var spanBorder = document.createElement('span');//黑色边框的标签
        var timeSpan = document.createElement('span');//时间标签
        var lookSpanBox = document.createElement('span');//浏览量标签的盒子
        lookSpanBox.setAttribute("class","lookSpan");
        var lookImg = document.createElement('img');//浏览量图标
        lookImg.setAttribute("src","../../public/images/浏览.png");
        var lookspan = document.createElement('span');//浏览量标签
        contentBox.onclick = function (ev) {
            a.click();
        };
        contentBrief.appendChild(a);
        contentBrief.appendChild(contentP);
        contentBrief.appendChild(smallSpan);
        smallSpan.appendChild(contentUserSpan);
        smallSpan.appendChild(lookSpanBox);
        if (document.body.clientWidth > 650){
            contentUserSpan.appendChild(p);
            contentUserSpan.appendChild(spanBorder);
            spanBorder.setAttribute("class","spanBorder");
        }else {

        }
        contentUserSpan.appendChild(timeSpan);
        lookSpanBox.appendChild(lookImg);
        lookSpanBox.appendChild(lookspan);
        contentBox.appendChild(span);
        contentBox.appendChild(contentBrief);
        // allNews.insertBefore(contentBox,loadBox);
        //设置新闻图片

        img.setAttribute("src",e.coverimg);
        //点击跳转到详情
        a.setAttribute("href","/views/front/articleDetail.html?id="+e.aid);
        //标题
        h2Title.innerHTML = e.title;
        //内容简要
        if (document.body.clientWidth > 650){
            contentP.innerHTML = e.brief.substring(0,50);
        }
        //用户名字
        // var user = findUserByAId(e.uid);
        // var user = findUserByAId(e.uid);
        if (document.body.clientWidth > 650) {
            if (false){
                p.innerText = e.author.substring(0,3) + "***" + e.substring(8,11);
            } else {
                p.innerText = e.author;
            }
        }else {
            // if (false){
            //     p.innerText = e.nickname.substring(0,3) + "***" + e.nickname.substring(8,11);
            // } else {
            //     p.innerText = e.nickname;
            // }
        }
        //发表时间
        if (document.body.clientWidth > 650)
        {
            var d = new Date(e.addtime);
            timeSpan.innerHTML = d.getFullYear()+"-"+d.getMonth()+"-"+d.getDate();
        }else {
            var d = new Date(e.addtime);
            timeSpan.innerHTML = d.getFullYear()+"-"+d.getMonth()+"-"+d.getDate();
        }

        //浏览次数
        lookspan.innerHTML = e.readnumber;
        // //评价按钮
        // commentSpan.innerHTML = "";
        //头像
        // contentHeadPic.setAttribute("src",e.header);
        $(".load")[0].before(contentBox);
    }
    //添加快讯
    function addJs(list) {
        var articleUrl = window.location + "/front/toReadFlash?id=" + list.aid;
        var date1 = new Date();
        var date2 = new Date(list.addtime);
        var time = date1.getTime();
        var howMinute =  Math.floor((time - list.addtime)/1000) + "秒前";
        var viewTime = howMinute;
        // console.log("d1d2d2d1");
        // console.log(date1);
        // console.log(date1.getDate());
        // console.log(date2.getDate());
        //设置显示格式
        if (date1.getMonth() - date2.getMonth() > 0){
            viewTime = date1.getMonth() - date2.getMonth() + "月前";
        } else if (date1.getDate() - date2.getDate() > 7){
            viewTime = parseInt((date1.getDate() - date2.getDate())/7) + "周前";
        } else if (date1.getDate() - date2.getDate() > 0) {
            viewTime = date1.getDate() - date2.getDate() + "天前";
        }else if (date1.getHours() - date2.getHours() > 0){
            viewTime = date1.getHours() - date2.getHours() + "小时前";
        }else if (date1.getMinutes() - date2.getMinutes() > 0){
            viewTime = date1.getMinutes() - date2.getMinutes() + "分钟前";
        }
        var js = "<div class=\"jsBox\">\n" +
            "    <div class=\"grade\"></div>\n" +
            "    <div class=\"time\">"+viewTime+"</div>\n" +
            "    <div class=\"intro\">\n" +
            "        <div class=\"content\">\n" +
            "            <a target=\"_blank\" class=\"livesb\" href=\"/views/front/flashDetail.html?id="+ list.fid +"\">"+list.title+"</a>\n" +
            "            <a target=\"_blank\" class=\"livesbx\" href=\"/views/front/flashDetail.html?id="+ list.fid +"\">"+list.content+"</a><br/>\n" +
            "        </div>\n" +
            "<div class=\"font\">\n" +
            "            <ul class=\"fontUl\">\n" +
            "                <li>" +
            "                   <img class=\"flash-look\" src=\"../../public/images/浏览.png\" alt=\"\">"+
                                "阅读量"+list.readnumber+
                            "</li>\n" +
            "                <li onclick=\"imageShare("+list.fid+")\">" +
            "                   <img  class=\"flash-look\" src=\"../../public/images/分享.png\" alt=\"\">"+
            "                   分享"+
                            "</li>\n" +
            "            </ul>\n" +
            "        </div>"+
            "    </div>\n" +
            "</div>";
        $(".load").before(js);
    }

    //隐藏文章
    function hiddenArticles() {
        var contentBoxArr = document.getElementsByClassName("contentBox");
        pageStart = 0;//重置分页数
        for (var i = 0; i < contentBoxArr.length; i++) {
            contentBoxArr[i].style.display = "none";//隐藏原有新闻
        }
    }
    //隐藏快讯
    function hiddenJs() {
        var jsBoxs = document.getElementsByClassName("jsBox");
        pageStart = 0;
        // console.log(jsBoxs)
        if (jsBoxs === "" || jsBoxs == null || jsBoxs.length === 0) {
            return;
        }
        for (var i = 0; i < jsBoxs.length; i++) {
            jsBoxs[i].style.display = "none";
        }
    }

    // //检查手机号码
    // function testphone(num) {
    //
    //     // console.log("绑定成功")
    //     if(num.length == 11 && (checkPhone(num) == 1)) {
    //         return true;
    //     } else {
    //         return false;
    //     }
    //     // else if ($("#phone").val().length > 11){
    //     //     layer.msg("请输入正确的手机号码!");
    //     // }
    // }
    //
    // function checkPhone(num) {
    //     var filter = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
    //     if (filter.test(num)) {
    //         return 1;
    //     }
    // }
    // function ellipsis(str, length) {
    //     // console.log(element);
    //     // var str = element.innerText;
    //     // console.log(str.length);
    //     if (str.length > length) {
    //         str = str.substring(0, length) + "...";
    //     }
    //     return str;
    // }


})(window);
