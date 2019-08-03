var cssStr = "<link rel=\"stylesheet\" href=\"../../public/css/top.css\">";
var domStr =
    "<a href=\"#\">\n" +
    "<div id=\"toTopBox\">\n" +
    "    <div class=\"innerBox\">\n" +
    "            <img class=\"xsImg\" src=\"../../public/imgs/xsb.png\">\n" +
    "            <p class=\"top\">TOP</p>\n" +
    "    </div>\n" +
    "</div>\n"+
    "</a>";

if (document.body.clientWidth > 700){
    $('body').append(domStr);
    $('head').append(cssStr);
    $("#toTopBox").hide();
    window.onscroll = function (ev){
        if (window.scrollY > 300){
            $("#toTopBox").show();
        }else {
            $("#toTopBox").hide();
        }
    };
}
