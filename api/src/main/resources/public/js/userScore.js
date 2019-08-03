$(function () {
    var jumpPageButton = document.getElementById('jumpPageButton');
    var jumpPageA = document.getElementById('jumpPageA');
    var allPageSpan = document.getElementById("allPageSpan");
    // $.ajax({
    //
    //     url: "/credits/list",
    //     data: {
    //         pageNum: 1,
    //         size: 3
    //     },
    //     success: function (res) {
    //         if (res.code === 0) {
    //
    //             var mesList = res.data.list;
    //             console.log(res.data);
    //
    //         }
    //     }
    //
    // });

    //分页
    var pageNum = getQueryString("pageNum") == null?1:getQueryString("pageNum");
    var pageInfo = requestPageInfo(pageNum);//每次获取的分页信息
    var preLi = document.getElementsByClassName("preLi")[0];
    var nextA = document.getElementsByClassName("nextA")[0];
    var pageUl = document.querySelectorAll(".navigatepageNums ul")[0];
    var navigatepageNums = document.getElementsByClassName("navigatepageNums")[0];
    if (pageInfo.list.length > 0){
        navigatepageNums.style.display = "inline-block";
    }
    addLi(pageInfo);
    addList(pageInfo.list);
    if(!pageInfo.isFirstPage){
        var pre = preLi.getElementsByTagName("a")[0];
        pre.setAttribute("href","?pageNum="+(pageInfo.pageNum - 1));
    }else {
        preLi.style.display = "none";
    }
    if(!pageInfo.isLastPage){
        var next = nextA.getElementsByTagName("a")[0];
        next.setAttribute("href","?pageNum="+(pageInfo.pageNum + 1));
    }else {
        nextA.style.display = "none";
    }
    function addLi(pageInfo) {
        for (var i = 0; i < pageInfo.navigatepageNums.length; i++){
            var li = document.createElement("li");
            var a = document.createElement("a");
            allPageSpan.innerText = "共"+pageInfo.pages+"页,到第";
            li.setAttribute("class","pageNum");
            a.setAttribute("href","?pageNum="+pageInfo.navigatepageNums[i]);
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
    }
    function requestPageInfo(pageNum) {
        var pageInfo;
        $.ajax({
            url:"/credits/pcList?pageNum="+pageNum+"&pageSize=10",
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

    function addList(mesList) {
        var score;
        console.log("mmmmmmm");
        console.log(mesItem);
        for (var i = 0; i < mesList.length; i++) {
            var message = mesList[i];
            var date = new Date(message.add_time);
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var hours = date.getHours();
            var minutes = date.getMinutes();
            if(month >= 1 && month <= 9){
                month = "0" + month;
            }
            if(day >= 1 && day <= 9){
                day = "0" + day;
            }
            if(hours >= 1 && hours <= 9){
                hours = "0" + hours;
            }
            if(minutes >= 0 && minutes <= 9){
                minutes = "0" + minutes;
            }
            var viewTime = year +"-"+ month +"-"+ day +" "+ hours +":"+ minutes;
            if(message.score  < 0){
                score = message.score;
            }else{
                score = "+"+message.score;
            }
            if(i%2 == 0){
                var mesItem = "<li class='lishow'><span>" + message.info + "</span><span>" +score +"</span><span>" + viewTime + "</span></li>";
            }
            else{
                var mesItem = "<li class='li'><span>" + message.info + "</span><span>" +score +"</span><span>" + viewTime + "</span></li>";
            }
            $("#messageList").append(mesItem);
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
            jumpPageA.setAttribute("href","?pageNum="+jumpPageNum);
            jumpPageA.click();
        }
    }
});