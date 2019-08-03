$(function () {
    var jumpPageButton = document.getElementById('jumpPageButton');
    var jumpPageA = document.getElementById('jumpPageA');
    var allPageSpan = document.getElementById("allPageSpan");
    var pageNum = getQueryString("pageNum") == null?1:getQueryString("pageNum");

    var pageInfo = requestPageInfo(pageNum);//每次获取的分页信息
    var preLi = document.getElementsByClassName("preLi")[0];
    var nextA = document.getElementsByClassName("nextA")[0];
    var pageUl = document.querySelectorAll(".navigatepageNums ul")[0];
    var navigatepageNums = document.getElementsByClassName("navigatepageNums")[0];
    navigatepageNums.style.display = "none";
    // $.ajax({
    //
    //     url: "/credits/list",
    //     data: {
    //         pageNum: 1,
    //         size: 3
    //     },
    //     success: function (res) {
    //         if (res.code === 0){
    //
    //             var mesList = res.data.list;
    //             console.log(res.data);
    //
    //         }
    //     }
    //
    // });
    if (pageInfo.list.length <= 0 || pageInfo.list == null){


    }else {
        navigatepageNums.style.display = "inline-block";
        addMessag(pageInfo.list);
        addLi(pageInfo);
    }
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
            li.setAttribute("class","pageNum");
            a.setAttribute("href","?pageNum="+pageInfo.navigatepageNums[i]);
            allPageSpan.innerText = "共"+pageInfo.pages+"页,到第";
            a.innerText = pageInfo.navigatepageNums[i];
            if (pageInfo.navigatepageNums[i] == pageInfo.pageNum) {
                li.classList.add("active");
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
            url:"/message/pcList?pageNum="+pageNum+"&pageSize=10",
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
    };

    function addMessag(mesList) {
        for (var i = 0; i < mesList.length; i++) {

            var message = mesList[i];

            var date1 = new Date();
            var date2 = new Date(message.add_time);
            var time = date1.getTime();
            var howMinute =  Math.floor((time - message.add_time)/1000) + "秒前";
            var viewTime = howMinute;
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

            var mesItem = "<li><span>"+message.message_info+"</span><span>"+viewTime+"</span></li>"

            $("#messageList").append(mesItem);

        }
    }
});
// var date1 = new Date();
// var date2 = new Date(list.add_time);
// var time = date1.getTime();
// var howMinute = Math.floor(((time - list.addtime)/1000)/60) + "分钟前";
// var viewTime = howMinute;
// //设置显示格式
// if (date1.getMonth() - date2.getMonth() > 0){
//     viewTime = date1.getMonth() - date2.getMonth() + "月前";
// } else if (date1.getDate() - date2.getDate() > 7){
//     viewTime = parseInt((date1.getDate() - date2.getDate())/7) + "周前";
// } else if (date1.getDate() - date2.getDate() > 0) {
//     viewTime = date1.getDate() - date2.getDate() + "天前";
// }else if (date1.getHours() - date2.getHours() > 0){
//     viewTime = date1.getHours() - date2.getHours() + "小时前";
// }


