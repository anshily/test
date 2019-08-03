
var oneLiArr = document.getElementsByClassName("oneLi");
var setLi = document.querySelectorAll(".setLi a");
if (document.body.clientWidth > 650){
    var isWind = true;
    $(".windA").click(function () {
        $(".user-set ul").slideToggle();
        if (isWind) {
            isWind = false;
            $(".unwindImg").css("transform","rotate(90deg)");
        }else {
            isWind = true;
            $(".unwindImg").css("transform","rotate(0deg)");
        }
    });

    for (var i = 0; i < setLi.length; i++){
        setLi[i].onclick = function (e) {
            for (var j = 0; j < setLi.length; j++){
                setLi[j].style.color = "#5D5F62";
                setLi[j].style.fontSize = "16px";
            }
            this.style.color = "black";
            this.style.fontSize = "17px";
        }
    }

    for (var i = 0; i < oneLiArr.length; i++){
        oneLiArr[i].onclick = function (e) {
            for (var j = 0; j < oneLiArr.length; j++){
                oneLiArr[j].style.border = "none";
                oneLiArr[j].style.backgroundColor = "white";
            }
            this.style.backgroundColor = "#F2F4F7";
            this.style.borderLeft = "solid 4px #1992EF";
        }
    }

    oneLiArr[0].click();
}else {
    $(".wheel-button").wheelmenu({
        trigger: "click",
        animation: "fly",
        animationSpeed: "fast",
        angle: "NW",
    });

    for (var i = 0; i < oneLiArr.length; i++){
        oneLiArr[i].onclick = function (e) {
            for (var j = 0; j < oneLiArr.length; j++){
                // oneLiArr[j].style.border = "none";
                oneLiArr[j].style.backgroundColor = "white";
            }
            this.style.backgroundColor = "#F2F4F7";
            // this.style.borderLeft = "solid 4px #1992EF";
        }
    }
    var setMenu = document.getElementById("setMenu");
    var menuAs = document.querySelectorAll(".item a");
    for (var i = 0; i < menuAs.length; i++){
        menuAs[i].onclick = function (e) {
            setMenu.click();
        }
    }
}




