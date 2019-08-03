
var articleType;//定义文章类型
var eid = $("#movEid").html();
var cImg;

//限制标题字数
function limitNumber() {
    if($("#articleTitle").val().length > 14){
        layer.msg("文章标题最多输入15个字");
    }
    else{}
}
//限制来源的字数
function limitsource() {
    if ($("#articleSource").val().length > 9) {
        layer.msg("来源最多输入10个字");
    }
    else{}
}
//限制作者的字数
function limitauthor() {
    if ($("#articleAuthor").val().length > 9) {
        layer.msg("作者最多输入10个字");
    }
    else{}
}
//让新闻默认成为被选中的状态
$(function () {
    $(".keywordInput")[0].click();
});
//改变当前选框的颜色，并获取其中的值
$(".keywordInput").click(function (e) {
    changeallcolor();
    $(this).css("color","#1992EF");
    $(this).css("borderColor","#1992EF")
    articleType = $(this)[0].value;
    console.log(articleType);
});
//改变所有选框的颜色
function changeallcolor() {
    var Arr = document.getElementsByClassName('keywordInput');
    for (var i = 0;i < Arr.length;i++){
        Arr[i].style.color = '#333333';
        Arr[i].style.borderColor = '#E9E9E9';
    }
}

//绑定file的事件到触发按钮上
$(function () {
    $("#upfile").click(function () {
        $("#file").click();
    })
});
//显示图片
function updateImg(e) {

    //获取文件
    var file = $("#file")[0].files[0];
    var size = file.size;
    // console.log(size);
    if (size > 3 * 1024 *1024) {
        layer.confirm("图片大小不能超过3M");
    } else {
        if (file) {
            console.log("hhh");
            //创建读取文件的对象
            var reader = new FileReader();
            reader.readAsDataURL(file);
            //创建文件读取相关的变量
            var imgFile;

            //为文件读取成功设置事件
            reader.onload = function (e) {
                // alert('文件读取完成');
                imgFile = e.target.result; //获取当前文件的内容
                // console.log(imgFile);
                $(".imgView").attr('src', imgFile);
                $(".imgView").css("display", "inline-block");
                $(".buttonBox").css("background-color", "#1992EF");
                $(".buttonBox").text("重新上传");
                $(".tipBox").css("display", "none");
            };
        }
    }


}

// var rImg;
//触发保存按钮，保存发布的内容
$(".commit").click(function () {
    // console.log(1111111);
    var rImg;
    var articleTitle = $("#articleTitle").val();
    console.log(articleTitle);
    var articleAuthor = $("#articleAuthor").val();
    var articleSource = $("#articleSource").val();
    var articleBrief = $("#articleBrief").val();
    var ccc = $(".imgView")[0].src;
    // console.log(ccc);
    var coverImg = $("#file")[0].files[0];
    var readerImg = new FileReader();
    var articleContent = $("#editor").html();
    if (articleTitle == null || articleTitle == "") {
        layer.msg("请填写内容名称");
        return;
    }
    if (articleType == null || articleType == "") {
        layer.msg("请选择类型");
        return;
    }
    if (articleAuthor == null || articleAuthor == "") {
        layer.msg("请填写作者");
        return;
    }
    if (articleSource == null || articleSource == "") {
        layer.msg("请填写来源");
        return;
    }
    if (articleBrief == null || articleBrief == "") {
        layer.msg("请填写摘要");
        return;
    }
    if (articleContent == null || articleContent == "") {
        layer.msg("请填写文本内容");
        return;
    }
    //更换封面
    if (coverImg != null) {
        var size = coverImg.size;
        // console.log(size);
        if (size > 3 * 1024 * 1024) {
            layer.confirm("图片大小不能超过3M");
        } else {
            readerImg.readAsDataURL(coverImg);
            readerImg.onload = function (e) {
                var article = {
                    title: articleTitle,
                    cid: articleType,
                    author: articleAuthor,
                    source: articleSource,
                    brief: articleBrief,
                    coverimg: readerImg.result,
                    content: articleContent,
                    aid: eid
                };
                console.log(article);
                $.ajax({
                    url: "power/article/reuploadArticle",
                    data: JSON.stringify(article),
                    contentType: "application/json",
                    type: "post",
                    async: false,
                    success: function (is) {
                        console.log(is);
                        if (is.code === 0) {
                            layer.msg("保存成功");
                            location.href = "/editorSuccess";
                        } else if (is.code === 2504) {
                            layer.msg(is.message);
                        }
                        else {
                            layer.msg("服务器错误，保存失败");
                        }
                    }
                });
            }
        }
    }
    //未更换封面
    if (coverImg == null || coverImg == "") {
        rImg = cImg;
        var article = {
            title: articleTitle,
            cid: articleType,
            author: articleAuthor,
            source: articleSource,
            brief: articleBrief,
            coverimg: rImg,
            content: articleContent,
            aid:eid
        };
        console.log(article);
        $.ajax({
            url: "power/article/reuploadArticle",
            data: JSON.stringify(article),
            contentType: "application/json",
            type: "post",
            async: false,
            success: function (is) {
                console.log(is);
                if (is.code === 0) {
                    layer.msg("保存成功");
                    location.href = "/editorSuccess";
                } else if (is.code === 2504) {
                    layer.msg(is.message);
                }
                else {
                    layer.msg("服务器错误，保存失败");
                }
            }
        });
    }
});

(function () {
    $.ajax({
        url:"/article/readArticle?id="+eid,
        dataType:"json",
        type:"get",
        async: false,
        success:function (data) {
            console.log("dddddd");
            console.log(data);
            putArticle(data);
        },
        error:function (e) {
            console.log(e);
        }
    });

    function putArticle(map) {
        var i = map.article.type;
        cImg = map.article.coverimg;
        $(".keywordInput")[i].click();
        $("#articleTitle").val(map.article.title);
        $("#articleAuthor").val(map.article.author);
        $("#articleSource").val(map.article.source);
        $("#articleBrief").val(map.article.brief);
        $(".imgView").attr('src', cImg);
        $("#editor").html(map.article.content);
    }

})();

