(function (_window) {
    var navigatepageNums = document.querySelectorAll(".navigatepageNums")[0];
    var pageNum = getQueryString("pageNum") == null?1:getQueryString("pageNum");
    var allPageSpan = document.getElementById("allPageSpan");
    var loadState = getQueryString("loadState") == null?9: getQueryString("loadState");
    var preLi = document.getElementsByClassName("preLi")[0];
    var nextA = document.getElementsByClassName("nextA")[0];
    var jumpPageButton = document.getElementById('jumpPageButton');
    var jumpPageA = document.getElementById('jumpPageA');
    // var currentStaate = 9;
    var pageBox = document.getElementById("pageBox");
    var number = 1;//文章顺序
    var pageStart = 0;//分页页码
    var showAllButton = document.getElementById("showAll");
    var showWaitAuditButton = document.getElementById("showWaitAudit");
    var releaseButton = document.getElementById("release");
    var notPassButton = document.getElementById("notPass");
    var noArticle = document.getElementsByClassName('user_article_table_no')[0];
    var navigatepageNums = document.getElementsByClassName("navigatepageNums")[0];

    var articles = requestPageInfo(pageNum,loadState);
    if (articles.list.length > 0){
        noArticle.style.display = 'none';
        navigatepageNums.style.display = "inline-block";
        for (var i = 0; i < articles.list.length; i++){
            console.log(articles.list[i]);
            addArticles(articles.list[i]);
        }
    }
    else{
        noArticle.style.display = 'block';
    }

    //请求文章
    function requestMyArticle(state) {
        console.log(state);
        var articles;
        var URL = "";
        //获取全部文章
        if (state == 9) {
            URL = "/article/myAllArticle?pageStart=";
        }else if (state == 2) {
            URL = "/article/myArticleChecking?pageStart=";
        }else if (state == 1) {
            URL = "/article/myArticleRelease?pageStart=";
        }else if (state == 3){
            URL = "/article/myArticleNotPass?pageStart=";
        }
        $.ajax({
            url: URL + pageStart,
            async:false,
            dataType:"json",
            type:"get",
            success:function (data) {
                articles = data;
                console.log(articles);
                pageStart++;
            },
            error:function (e) {
                console.log(e);
            }
        });
        return articles;
    }
    // 增加文章列表
    // function addArticle(article) {
    //     console.log(article);
    //     var state;
    //
    //     if (article.state == 2){
    //         state = "等待审核";
    //         // stateSpan.style.color = "#9B9B9B";
    //     } else if (article.state == 3){
    //         state = "未通过";
    //         // stateSpan.style.color = "#DA361A";
    //     } else if (article.state == 1){
    //         state = "已通过";
    //         // stateSpan.style.color = "#ff9d11";
    //     }else if (article.state == 0)
    //     {
    //         state = "已下架";
    //         // stateSpan.style.color = "#DA361A";
    //     }else {
    //         // stateSpan.innerText = "未知状态";
    //         // stateSpan.style.color = "#DA361A";
    //     }
    //     var d = new Date(article.addtime);
    //     var addtime = d.getFullYear()+"-"+d.getMonth()+"-"+d.getDate();
    //
    //     var Article = "<div class=\"contentBox\">\n" +
    //         "\n" +
    //         "                    <span class=\"artical-pic\">\n" +
    //         "                        <img class=\"newsPic\" src=\""+ article.coverimg+"\">\n" +
    //         "                    </span>\n" +
    //         "\n" +
    //         "                    <span class=\"contentBrief\">\n" +
    //         "                            <a herf=\"/\" target=\"_self\">\n" +
    //         "                                <h2>"+ article.title +"</h2>\n" +
    //         "                                <span class=\"checkBox\">"+ state +"</span>\n" +
    //         "                            </a>\n" +
    //         "                            <p class=\"contentBox-p\">"+ article.title+"</p>\n" +
    //         "                            <div class=\"smallSpan\">\n" +
    //         "\n" +
    //         "                                <span class=\"contentUserSpan\">\n" +
    //         "                                    <p>"+ article.author+"</p>\n" +
    //         "                                    <span></span>\n" +
    //         "                                    <span>"+ addtime +"</span>\n" +
    //         "                                </span>\n" +
    //         "\n" +
    //         "                                <span class=\"lookSpan\">\n" +
    //         "                                    <img src=\"../../public/images/浏览.png\" alt=\"\">\n" +
    //         "                                    <span>"+ article.readnumber +"</span>\n" +
    //         "                                </span>\n" +
    //         "                            </div>\n" +
    //         "\n" +
    //         "                        </span>\n" +
    //         "                </div>"
    //
    //     $(".navigatepageNums").before(Article);
    // }

    //隐藏文章
    function hidden(){
        var articleList = document.getElementsByClassName("contentBox");
        for (var i = 0; i < articleList.length; i++){
            articleList[i].style.display = "none";
        }
    }

    //展示所有
    showAllButton.onclick = function (ev) {
        selectByState(0,9);
        changeLiColor(this);
    };
    //展示审核中
    showWaitAuditButton.onclick = function (ev) {
        selectByState(0,2);
        changeLiColor(this);
    };
    //展示已通过
    releaseButton.onclick = function (ev) {
        selectByState(0,1);
        changeLiColor(this);
    };
    //展示未通过
    notPassButton.onclick = function (ev) {
        selectByState(0,3);
        changeLiColor(this);
    };
    //筛选方法
    function selectByState(pageNum,state) {
        hidden();
        hidenPageNum();
        preLi.style.display = "inline-block";
        nextA.style.display = "inline-block";
        loadState = state;
        var articles = requestPageInfo(pageNum,state);
        if (articles.list.length > 0){
            navigatepageNums.style.display="block";
            noArticle.style.display = 'none';
            addLi(articles);
            for (var i = 0; i < articles.list.length; i++){
                addArticles(articles.list[i]);
            }
        } else {
            noArticle.style.display = 'block';
            navigatepageNums.style.display="none";
        }
        // if(state == 9){
        //     if (articles.list.length > 0){
        //         noArticle.style.display = 'none';
        //         for (var i = 0; i < articles.list.length; i++){
        //             console.log(articles.list[i]);
        //             addArticles(articles.list[i]);
        //         }
        //     }
        //     else{
        //         noArticle.style.display = 'block';
        //     }
        //
        // }
        // else {
        //
        //     if(articles.length > 0){
        //
        //         for (var i = 0; i < articles.length; i++){
        //             addArticles(articles[i]);
        //         }
        //     }
        //     else{
        //         noArticle.style.display = 'block';
        //     }
        //
        // }
    }

    //改变被选择框的样式
    function changeLiColor(_this){
        var lis = document.querySelectorAll(".user_article_menu li");
        for (var i = 0; i < lis.length; i++){
            lis[i].style.color = "#949494";
            lis[i].style.borderBottom = '0';
        }
        _this.style.color = "#1992EF";
        _this.style.borderBottom = "#1992EF solid 2px";
    }


    //分页
    var pageInfo = requestPageInfo(pageNum,loadState);//每次获取的分页信息
    var lis = document.querySelectorAll(".user_article_menu li");
    for (var i = 0; i < lis.length; i++){
        lis[i].style.color = "#949494";
        lis[i].style.borderBottom = '0';
    }
    if (loadState == 9){
        lis[0].style.color = "#1992EF";
        lis[0].style.borderBottom = "#1992EF solid 2px";
    }else if (loadState == 1){
        lis[2].style.color = "#1992EF";
        lis[2].style.borderBottom = "#1992EF solid 2px";
    }else if (loadState == 2){
        lis[1].style.color = "#1992EF";
        lis[1].style.borderBottom = "#1992EF solid 2px";
    }else if (loadState == 3){
        lis[3].style.color = "#1992EF";
        lis[3].style.borderBottom = "#1992EF solid 2px";
    }

    // var pageUl = document.querySelectorAll(".navigatepageNums ul")[0];
    addLi(pageInfo);
    function addLi(pageInfo) {
        console.log("addLi");
        console.log(loadState);
        var pageUl = document.createElement("ul");
        allPageSpan.innerText = "共"+pageInfo.pages+"页,到第";
        for (var i = 0; i < pageInfo.navigatepageNums.length; i++){
            var li = document.createElement("li");
            var a = document.createElement("a");
            li.setAttribute("class","pageNum");
            a.setAttribute("href","?pageNum="+pageInfo.navigatepageNums[i]+"&loadState="+loadState);
            a.innerText = pageInfo.navigatepageNums[i];
            if (pageInfo.navigatepageNums[i] == pageInfo.pageNum) {
                li.setAttribute("class","active");
            }
            li.onclick = function (ev) {
                change();
            };
            li.appendChild(a);
            pageUl.appendChild(li);
        }
        navigatepageNums.insertBefore(pageUl,nextA);
        if(!pageInfo.isFirstPage){
            var pre = preLi.getElementsByTagName("a")[0];
            pre.setAttribute("href","?pageNum="+(pageInfo.pageNum - 1)+"&loadState="+loadState);
        }else {
            preLi.style.display = "none";
        }
        if(!pageInfo.isLastPage){
            var next = nextA.getElementsByTagName("a")[0];
            next.setAttribute("href","?pageNum="+(pageInfo.pageNum + 1)+"&loadState="+loadState);
        }else {
            nextA.style.display = "none";
        }
    }




    function requestPageInfo(pageNum,state) {
        var pageInfo;
        if (state == 9) {
            URL = "/article/myAllArticle";
        }else if (state == 2) {
            URL = "/article/myArticleChecking";
        }else if (state == 1) {
            URL = "/article/myArticleRelease";
        }else if (state == 3){
            URL = "/article/myArticleNotPass";
        }
        $.ajax({
            url:URL+"?pageNum="+pageNum+"&pageSize=10",
            type:"get",
            dataType:"json",
            async:false,
            success:function (data) {
                console.log(data);
                pageInfo = data;
            }
        });
        return pageInfo;
    }
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        console.log(r);
        if (r != null)
            return unescape(r[2]);
        return null;
    }
    function change(_this){
        var li = document.querySelectorAll(".pageNum");
        for (var i = 0; i < li.length; i++) {

        }
    }

    function addArticles(e) {
        // console.log(e);
        var allNews = document.getElementById("allNews");
        var loadBox = document.getElementsByClassName("loadBox")[0];
        var contentBox = document.createElement('div');
        if (e.state == 2){
            var stateImg = document.createElement("img");
            contentBox.appendChild(stateImg);
            stateImg.setAttribute("class","stateImg");
            stateImg.setAttribute("src","../../public/imgs/dsh.png")
        }else if (e.state == 3){
            var stateImg = document.createElement("img");
            contentBox.appendChild(stateImg);
            stateImg.setAttribute("class","stateImg");
            stateImg.setAttribute("src","../../public/imgs/wtg.png")
        }
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
        if (document.body.clientWidth > 550){
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
        a.setAttribute("target","_top");
        a.setAttribute("href","/checkArticle?id="+e.aid);
        //标题
        h2Title.innerHTML = e.title;
        //内容简要
        contentP.innerText = e.brief.substring(0,50);

        //用户名字
        // var user = findUserByAId(e.uid);
        // var user = findUserByAId(e.uid);
        if (document.body.clientWidth > 550) {
            console.log(e.author);
            // if (false){
            //     p.innerText = e.author.substring(0,3) + "***" + e.substring(8,11);
            // } else {
                p.innerText = e.author;
            // }
        }else {
            // if (false){
            //     p.innerText = e.nickname.substring(0,3) + "***" + e.nickname.substring(8,11);
            // } else {
            //     console.log(e.author);
            //     p.innerText = e.author;
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
        $(".navigatepageNums")[0].before(contentBox);
    }

    function hidenPageNum() {
        var navigatepageNumsUl = document.querySelectorAll(".navigatepageNums ul");
        for (var i = 0; i < navigatepageNumsUl.length; i++){
            navigatepageNumsUl[i].style.display = "none";
        }
    }

    jumpPageButton.onclick = function (ev) {
        var jumpPageNum = document.getElementById('jumpPageInput').value;
        if(jumpPageNum == "" || jumpPageNum == null){
            layer.msg("您还没有输入数值");
            return;
        }
        if (jumpPageNum > pageInfo.pages) {
            layer.msg("您输入的值过大");
        }else {
            jumpPageA.setAttribute("href","?pageNum="+jumpPageNum+"&loadState="+loadState);
            jumpPageA.click();
        }
    }
})(window);