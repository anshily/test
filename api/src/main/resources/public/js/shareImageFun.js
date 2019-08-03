function imageShare(flashId) {
    //如果需要图片分享请在这写逻辑
    var shareElement = document.getElementById('shareElement');
    shareElement.style.display = 'block';
    console.log(flashId);
    var shareImage = document.getElementById("shareImage");
    shareImage.onclick = function (e) {
        e.stopPropagation();
        return false;
    };
    shareElement.onclick = function (ev) {
        this.style.display = "none";
        shareImage.style.display = "none";
    };
    var lodingBox = document.getElementById("lodingBox");
    lodingBox.style.display = "inline-block";
    var shareErCode = new QRCode(document.getElementById("shareErCode"), {
        text: window.location,
        width: 160,
        height: 160,
        colorDark: "#2C323E",
        colorLight: "#FFF",
        correctLevel: QRCode.CorrectLevel.H
    });
    //验证登录如果没有登录就去登录
    $.ajax({
        type: "get",
        url: "/user/isLogin",
        success: function (result) {
            if (result.code == 0) {
                if (result.data == "unLogin") {
                    location.href = "/login?url=" + window.location;
                } else {
                    //如果登录就生成二维码
                    var fo = {fid: flashId, uid: result.data.id,type:2}
                    $.ajax({
                        url: "/share/genShareToken",
                        data: fo,
                        type: 'get',
                        success: function (res) {
                            if (res.code === 0) {
                                token = res.data.share_token;
                                shareErCode.clear();
                                shareErCode.makeCode("http://www.51cnews.com/shareFlashDetail?token=" + token);


                                var img = document.querySelectorAll("#shareErCode canvas")[0];
                                var testImg = document.getElementById("testImg");
                                var base64 = img.toDataURL();
                                var uploadObject = {base64:base64,rootUrl:""};
                                //上传生成的二维码并生成图片
                                $.ajax({
                                    url:"/uploadImage",
                                    data:uploadObject,
                                    type:"post",
                                    dataType:"json",
                                    success:function (e) {
                                        shareImage.setAttribute("src","/setImage?id="+flashId+"&erCode="+e);
                                        setTimeout(function (e) {
                                            lodingBox.style.display = "none";
                                            shareImage.style.display = "inline-block";
                                        },2000)
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }
    });
}