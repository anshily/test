// var phone =document.getElementById("phone");
// var pass = document.getElementById("pass");
// var login_button = document.getElementById("login_button");
var afterurl = window.location;
var remember_password = false;
$(function () {
    afterurl = $("#url").html();
});

//检查手机号
function testphone() {

    // console.log("绑定成功")
    if ($("#phone").val().length === 11 && (checkPhone() != 1)) {
        layer.msg("请输入正确的手机号码!");
    }
    else if ($("#phone").val().length < 11){
        layer.msg("请输入正确的手机号码!");
    }
}
//检查手机号的正则表达式
function checkPhone() {
    var filter = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
    if (filter.test($("#phone").val())) {
        return 1;
    }
}
//
// function changeStyle() {
//
//     var flag = $("#remember")[0].className;
//     if(flag == "img_unselect"){
//         console.log(2)
//         $("#remember").removeClass('img_unselect').addClass('img_selected');
//     }else{
//         $("#remember").removeClass('img_selected').addClass('img_unselect');
//     }
// }
// 记住密码的操作
function rememberPassword() {
    var flag = $("#remember")[0].className;
    if(flag == "img_unselect"){
        $("#remember").removeClass('img_unselect').addClass('img_selected');
    }else{
        $("#remember").removeClass('img_selected').addClass('img_unselect');
    }
    // 记住密码
    // var flag = $('.check').hasClass('img_unselect');
    // if(flag) {
    //     $('.check').addClass('img_selected').removeClass('img_unselect');
    //
    // }else {
    //     $('.check').removeClass('img_selected').addClass('img_unselect');
    // }
    // remember_password = !remember_password;
    // console.log(remember_password);
}
function findLocalPassword () {
    var infoArr = JSON.parse(localStorage.userInfo);
    var tel = $('#phone').val();
    for(var i = 0; i < infoArr.length; i++){
        if(infoArr[i].phone == tel){
            $('#remember').removeClass('img_unselect').addClass('img_selected');
            $('#password').val(infoArr[i].password);
        }
        else{}
    }


}
function login() {
    var username = $("#phone").val();

    var password = $("#password").val();


    console.log(username,password);
    if (checkPhone() != 1) {
        layer.msg("请输入正确的手机号码!");
    } else {

        var user = {
            phone: username,
            password: password
        };
        $.ajax({
            contentType:"application/json",
            async:false,
            data:JSON.stringify(user),
            type: "POST",
            url: "/user/ajaxLogin",
            success: function (result) {
                var infoArr = [];
                var flag = true;
                if (result.code != 0) {
                    layer.msg(result.message)
                } else {
                    localStorage['jwt'] = JSON.stringify(res.data);
                    layer.msg("登录成功...");
                    // 判断有没有点击记住密码
                    if($("#remember")[0].className == "img_selected"){
                        // 判断 本地有没有存储账号密码
                        if(localStorage['userInfo']){
                            infoArr = JSON.parse(localStorage['userInfo']);
                            for(var i = 0; i<infoArr.length;i++){
                                // 判断本地哟没有存在 账号
                                if(infoArr[i].phone == user.phone){
                                    //存在 修改
                                    infoArr[i].password = user.password;
                                }else{
                                    infoArr.push(user);
                                }
                            }
                            console.log(infoArr);
                            localStorage['userInfo'] = JSON.stringify(infoArr);
                        }else {
                            localStorage['userInfo'] = JSON.stringify([user]);
                        }
                    }else{
                        // 判断 本地有没有存储账号密码
                        if(localStorage['userInfo']){
                            infoArr = JSON.parse(localStorage['userInfo']);
                            for(var j = 0; j<infoArr.length;j++){
                                // 判断本地哟没有存在 账号
                                if(infoArr[j].phone == user.phone){
                                    //存在 删除
                                    infoArr.splice(j,1);
                                }else{}
                            }

                            localStorage['userInfo'] = JSON.stringify(infoArr);
                        }else {}
                    }
                    // location.href = afterurl;
                }
            }
        });


    }


}

