function isMobileTablet() {
	// 화면이 1180px 이하인 경우 모바일/태블릿으로 판단
	return window.innerWidth <= 1180;
}


$(function(){

    $("input[id^='date']").each(function(){
		var _this = this.id;
		$('#' + _this).datepicker({
			dateFormat: "yy-mm-dd",
			changeMonth: true,
			changeYear: true,
			showMonthAfterYear: true,
			dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
			dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], 
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
		});
	});

	$("header>.wrap>div>ul>li>a").on("mouseenter", function (e) {
		e.preventDefault();
		if (!isMobileTablet()) {
			$("header").addClass("active");	
			$("header .wrap>div>ul>li").removeClass("active");
			$(this).parent().addClass("active"); 
			// if ($(this).parent().hasClass("menu-item-has-children")) {
			// 	$(".headerArea").addClass("active");
			// 	$(".headerArea").removeClass("black");
			// 	$(".headerArea .navbar > li.menu-item-has-children").find(".sub-menu").hide();
			// 	$(this).parent().find(".sub-menu").show();  
			// } else {
			// 	$(".headerArea").removeClass("active");
			// 	$(".headerArea").addClass("black");
			// 	$(".headerArea .navbar > li.menu-item-has-children").find(".sub-menu").hide();
			// }
			// if($(".headerArea").hasClass('white')) {
			//     $(".headerArea").removeClass("white");
			// }
		}
		
	});

	$('header').on("mouseleave", function (e) {
		if (!isMobileTablet()) {
			$("header").removeClass("active");
			$("header .wrap>div>ul>li").removeClass("active");
		}
		e.preventDefault();
	});

	$('.sub__aside>ul>li>a').on("click", function (e) {
	    if (!$(this).hasClass('inactive')) { // inactive 클래스가 없을 경우만 동작하도록 조건 추가
	        e.preventDefault();
	        if($(this).parent().hasClass('active')) {
	            $(this).siblings('ul').slideUp(300);
	            $(this).parent().removeClass('active');
	        } else {
	            $(this).siblings('ul').slideDown(300);
	            $(this).parent().addClass('active');
	        }
	    }
	});


	$('.__info-btn').on("click", function (e) {
		e.preventDefault();
		$('.user__info').toggleClass('active');
	});

	// ----- POPUPOPEN
	$('[data-popup-open]').on('click', function(e)  {
        e.preventDefault();
        var targeted_popup_class = jQuery(this).attr('data-popup-open');
		if($(this).hasClass('active')){
			$('[data-popup="' + targeted_popup_class + '"]').fadeOut(350);
		}else if($(this).parent('li').hasClass('active')){
			$('[data-popup="' + targeted_popup_class + '"]').fadeOut(350);
		}
		else{
			$('[data-popup="' + targeted_popup_class + '"]').fadeIn(350);
		}
    });

    //----- POPUPCLOSE
    $('[data-popup-close]').on('click', function(e)  {
        var targeted_popup_class = jQuery(this).attr('data-popup-close');
        $('[data-popup="' + targeted_popup_class + '"]').fadeOut(350);
		$('[data-popup-open=' + targeted_popup_class + ']').removeClass('active').parent('li').removeClass('active');
        e.preventDefault();
    });

	// mainpage .my__quick__menu
	$('.my__quick__menu .sub-menu>li>div>a').on("click", function (e) {
		e.preventDefault();
		$(this).toggleClass('active');
	});

	$('.btn__type__expand').on("click", function (e) {
		e.preventDefault();
		$(this).parent().toggleClass('open');
		if($(this).parent().hasClass('open')){
			$(this).text('접기');
		}else{
			$(this).text('더보기');
		}
	});



// gis
	$('.aside>ul>li>a').on("click", function (e) {
		e.preventDefault();
		$('.aside>ul>li').removeClass('active');
		$(this).parent().toggleClass('active');
	});
    $(".scroll-list").mCustomScrollbar({
        scrollInertia: 300,
		theme:"dark"
    });
	$('.address-search-box>li>a').on('click',function(e){
        e.preventDefault();
        $(this).siblings('ul').toggleClass('on').parent('li').siblings().children('ul').removeClass('on');
    });

	$('[class*="widget__"]>li>a').on('click',function(e){
		e.preventDefault();
		if($(this).parents().hasClass('widget__right')) {
			if($(this).parent('li').hasClass('hasdepth')){
				$(this).siblings('ul').slideToggle(300);
				$(this).parent().toggleClass('active');
			}
		}else if($(this).parents().hasClass('widget__top')){
			$(this).parent().toggleClass('active');
		}
	});
	$('a[class*="widget__"]').on('click',function(e){
		e.preventDefault();
		$(this).toggleClass('active');
	});

	$(".layer__control .btn__toggle").on("click", function(e){
		e.preventDefault();
		$(this).parents('.layer__control').toggleClass('active');
	
	});
	$(".layer__control input[type='color']").change(function(){
		var colorCode = $(this).val(); // 선택된 색상의 16진수 코드값을 가져옴
		$(this).parent().siblings('div').children('input[type="range"]').css('background', 'linear-gradient(90deg, #fff, ' + colorCode + ')');	  
	});

	$('.left-moving-btn>button').on('click',function(e){
		e.preventDefault();
		$('.aside__depth').toggleClass('on');
	});
	$('.show__hide__box>button').on('click',function(e){
		e.preventDefault();
		$(this).parent().toggleClass('active');
		$(this).parents('.popup__window.panel').toggleClass('hide');
	});

	

	$('.popup__window .popup__header').on('mousedown',function(e){
		// $(this).parent('.popup__window').draggable("disable");
		$(this).parent('.popup__window').draggable({ 
			disabled: false,
			containment: "body>.wrap", 
			scroll: false,
			able: true 
		});
	});
	$('.popup__window .popup__header').on('mouseup',function(e){
		$(this).parent('.popup__window').draggable( {
			disabled: true
		} );
	});

	// 탭버튼 addClass(활설화)
	$('.tab__list>ul>li>a').on('click',function(e){
		var snthsAnlsYn = $(this).hasClass('snths-anls');
		var lyrInfoYn = $(this).hasClass('lyrInfo');
		if(!snthsAnlsYn && !lyrInfoYn) {
			e.preventDefault();
			var idx = $(this).parent().index();
			$(this).parent().addClass('active').siblings().removeClass('active');
			$(this).parents('.tab__list').siblings('.tab__body','.tab__body__depth').children('ul').children('li').eq(idx).addClass('active').siblings().removeClass('active');
		}
		if (lyrInfoYn) {
			e.preventDefault();
			let grp = $(this).closest('li').data('grp');
			$(this).parent().addClass('active').siblings().removeClass('active');
			$(this).parents('.tab__list').siblings('.tab__body','.tab__body__depth').children('ul').children(`li[data-grp="${grp}"]`).addClass('active').siblings().removeClass('active');
		}
	});


});


