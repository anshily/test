var mySwiper = new Swiper ('.swiper-container', {
    // direction: 'vertical',
    loop: true,
    autoplay: {
        delay: 3000,
        stopOnLastSlide: false,
        disableOnInteraction: true,
    },


    // 如果需要分页器
    pagination: {
        el: '.swiper-pagination',
        type:'custom',
        clickable :true,
        renderCustom: function (swiper, current, total) {
            // current.setAttribute("class","swiper-li");
            var customPaginationHtml = "";
            for(var i = 0; i < total; i++) {
                //判断哪个分页器此刻应该被激活
                if(i == (current - 1)) {
                    customPaginationHtml += '<span class="swiper-pagination-customs swiper-pagination-customs-active"></span>';
                } else {
                    customPaginationHtml += '<span class="swiper-pagination-customs"></span>';
                }
            }
            return customPaginationHtml;
        }
    },

    // 如果需要前进后退按钮
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
    },
    // 如果需要滚动条
    scrollbar: {
        el: '.swiper-scrollbar',
    },
})
$(".swiper-pagination").on("click","span",function(){
    var index = $(this).index();
    mySwiper.slideTo(index+1);
});