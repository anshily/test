
//检查手机号输入是否正确
function testphone(){

    // console.log("绑定成功")
    if ($("#phone").val().length === 11 && (checkPhone() != 1)){
        // layer.msg("请输入正确的手机号码!");
        // $("#phone").after("<span class=\"checkAlert\">请输入正确的手机号</span>");
        $("#inputBoxCheckAlert").show();
        $("#phone").focus();
    }else if ($("#phone").val().length < 11){
        // layer.msg("请输入正确的手机号码!");
        // $("#phone").after("<span class=\"checkAlert\">请输入正确的手机号</span>");
        $("#inputBoxCheckAlert").show();

    }else {
        $("#inputBoxCheckAlert").hide();
    }
}
//检查手机号的正则表达式
function checkPhone(){
    var filter = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
    if (filter.test($("#phone").val())) {
        return 1;
    }
    else{}
}
//检查密码的长度
function testPass() {
    if($("#pass").val().length < 6){
        layer.msg("密码位数不足，请输入6位以上的密码");
    }else {}
}
//检查两次密码是否输入一致
function check() {
    // $("#button").css("backgroundColor","#FF9933");

    if( $("#repass").val() !== $("#pass").val()) {
        layer.msg("两次输入密码不一致!");
        return 1; // 两次密码不一致
    }else if ( $("#inVerify").val() === null) {
        layer.msg("验证码不能为空!")
        return 2; // 验证码不能为空
    }else if ($("#repass").val() === null || $("#pass").val() === null){
        return 3;
    }
}
//发送验证码
function sendcode() {

    if (checkPhone() === 1) {

        console.log($("#phone").val());

        $.ajax({
            type: "POST",
            data: {
                "phone": $("#phone").val()
            },
            dataType: "json",
            url: "/sys/sms/sendSMS",
            success: function (result) {
                if (result.code === 0) {
                    layer.msg("验证码发送成功！注意查收！");
                    settime($("#gettime").get(0));
                }
            }
        });
        // return;
    }else {

        layer.msg("请输入正确的手机号码!")
    }
}
var countDown = 60;
function settime(time){

    if(countDown  == 0){
        // time.removeAttribute("disabled");
        time.innerText = "获取验证码";
        countDown = 60;
        return false;
    }else{
        // time.setAttribute("disabled",true);
        time.innerText = "已发送"+ countDown +"s";
        countDown--;
    }
    setTimeout(function () {
        settime(time);
    },1000);
}

//触发注册按钮
function register() {
    //1 两次输入密码不一致 2 验证码不能为空
    if(check() === 1){
        layer.msg("两次输入密码不一致!");
    }else if (check() === 2) {
        layer.msg("验证码不能为空!")
    }else if (check() === 3) {
        layer.msg("密码不能为空!")

    }else{


        var username = $("#phone").val();
        console.log(username);
        var password = $("#pass").val();
        var code = $("#inVerify").val();
        console.log(password);

        var user = {
            phone: username,
            password: password
        }

        $.ajax({
            type : "POST",
            data : {
                "phone" : username,
                "code": code
            },
            dataType : "json",
            url : "/sys/sms/checkSMS",
            success : function(result){
                if (result.code === 0){
                    // layer.msg("验证码发送成功！注意查收！")

                    $.ajax({

                        contentType:"application/json",
                        async:false,
                        data:JSON.stringify(user),
                        type: "POST",
                        // url: "user/ajaxLogin",
                        // type : "POST",
                        // data : {
                        //     "phone" : username,
                        //     "password" : password
                        //     // "vcode" : code
                        // },
                        // dataType : "json",
                        url : "/user/ajaxRegister",
                        success : function(result) {
                            console.log(result);
                            if (result.code != 0) {

                                layer.msg(result.message)

                            } else {
                                // var token = sessionStorage.getItem("token");
                                // if (token !== "") {
                                //     $.ajax({
                                //         url: "",
                                //         data: {
                                //             token: token,
                                //             phone: username
                                //         },
                                //         success: function () {
                                //
                                //         }
                                //     })
                                // }
                                layer.msg("注册成功正在跳转登陆页...");
                                setTimeout(function(){
                                    location.href = "/login";
                                }, 1000);
                            }
                        }
                    });
                } else if(result.code === 152){
                    layer.msg("验证码不正确");
                }
            }
        })

    }

}




