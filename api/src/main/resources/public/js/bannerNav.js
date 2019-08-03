$(function () {

    bannerHtml =
        // "<div class='topNavigation-bg'></div>" +
        "<div class='banner-out'>" +
        "<div class=\"topNavigation\">\n" +
        "    <div class='logo-box'><a href=\"/\"><img class=\"logo\" src=\"../../public/images/ttqlogo2x.png\" alt=\"\"></a></div>\n" +
        "    <div class=\"author\" id=\"authorBox\" style=\"display: none\">\n" +
        "        <div class=\"writeArticle\"><span>发布内容</span></div>\n" +
        "        <div class=\"authorHeader\"><a href=\"/userCenter\"><img id=\"authorImg\" src=\"../../public/images/defuser.png\" onerror=\"this.src='../../public/images/defuser.png'\" alt=\"\"></a></div>\n" +
        "    </div>\n" +
        "    <div class=\"login-register\" id=\"authorLR\">\n" +
        "        <span id='login'>登录</span> |\n" +
        "        <span id='register'>注册</span>\n" +
        "    </div>\n" +
        "</div>" +
        "<div class='topNavigation-bg'></div>" +
        "</div>";
    $("body").prepend(bannerHtml);
    $("head").prepend("<script src=\"../../public/js/layer.js\"></script>");

    $("#login").click(function () {
        location.href = "/login?url="+window.location;
    })

    $("#register").click(function (){
        location.href = "/regis";
    })

    $(".writeArticle").click(function () {

        $.ajax({
            url: "/check/editor",
            type: "get",
            success: function (res) {
                if (res.code === 0){
                    location.href = "/editor";
                } else {
                    layer.alert(res.message);

                }
            }
        })
    })

    // $("img").error(function(){
    //     //当图片加载失败时，你要进行的操作
    //     $(this).attr('src','../../public/images/defuser.png');
    // });

    $.ajax({
        type: "get",
        url: "/user/isLogin",
        success: function(result) {
            if (result.code === 0){
                // console.log(result);
                if (result.data === "unLogin") {
                    window.shareUser = "unLogin";

                    console.log(window.shareUser)

                    $("#authorBox").hide();
                    $("#authorLR").show();
                }else {
                    $("#authorBox").show();
                    window.shareUser = result.data;
                    // localStorage.setItem("uid",result.data.id);

                    if (result.data.header != null){
                        // console.log(result.data.header);
                        $("#authorImg").attr("src","/"+result.data.header)
                    }
                    // $("#authorImg").src = result.data.header;
                    $("#authorLR").hide();
                }
            }
        }
    })
})