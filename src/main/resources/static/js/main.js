(function($) { 
  "use strict";

  /***********
   All swiper slider JS
   ************/

   /*hero slider one*/
   var hero_slider__one = new Swiper('.hero_slider__one', {
    slidesPerView: 1,
    speed: 1000,
    spaceBetween: 30,
    loop: true,
    roundLengths: true,
    centeredSlides : false,
    autoplay: {
      delay: 3000
    },
    pagination: {
      el: ".hero_pagination__one",
      clickable: true
    },
    breakpoints: {
      480:{
        slidesPerView: 1
      },
      575:{
        slidesPerView: 2,
      },
      992:{    
        slidesPerView: 3,
        centeredSlides : true,
      }
    }
  });


 
  var hero_slider__two = new Swiper('.hero_slider__two', {
    spaceBetween: 10,
    centeredSlides : true,
    slidesPerView: 1,
    speed: 1500,
    effect: 'fade',
    fadeEffect: {
      crossFade: true
    },
    autoplay: {
      delay: 4000
    },
    loop: true,
    loopedSlides: 4
  });

  var hero_thumbs__two = new Swiper('.hero_thumbs__two', {
    direction: 'horizontal',
    spaceBetween: 0,
    centeredSlides: false,
    slidesPerView: 1,
    touchRatio: 0.2,
    slideToClickedSlide: true,
    loop: true,
    loopedSlides: 3,
    breakpoints: {
      480:{
        slidesPerView: 1
      },
      575:{
        slidesPerView: 2,
      },
      992:{    
        slidesPerView: 5,
        direction: 'vertical',
        centeredSlides: true
      }
    }
  });
  hero_slider__two.controller.control = hero_thumbs__two;
  hero_thumbs__two.controller.control = hero_slider__two;


  /*live auction slider*/
  var live_auction_slider__one = new Swiper('.live_auction_slider__one', {
    slidesPerView: 1,
    speed: 1000,
    spaceBetween: 30,
    loop: true,
    roundLengths: true,
    centeredSlides : false,
    autoplay: {
      delay: 3500
    },
    navigation: {
      nextEl: '.live_auction__next',
      prevEl: '.live_auction__prev',
    },
    breakpoints: {
      480:{
        slidesPerView: 1
      },
      575:{
        slidesPerView: 2,
      },
      992:{    
        slidesPerView: 4
      }
    }
  });


  /*collection slider one*/
   var collection_slider__one = new Swiper('.collection_slider__one', {
    slidesPerView: 1,
    speed: 1500,
    spaceBetween: 30,
    loop: true,
    roundLengths: true,
    centeredSlides : false,
    autoplay: {
      delay: 3500
    },
    navigation: {
      nextEl: '.collection__next',
      prevEl: '.collection__prev',
    },
    breakpoints: {
      480:{
        slidesPerView: 1
      },
      575:{
        slidesPerView: 2,
      },
      992:{    
        slidesPerView: 3
      }
    }
  });

  /*collection slider two*/
   var collection_slider__two = new Swiper('.collection_slider__two', {
    slidesPerView: 1,
    speed: 1500,
    spaceBetween: 30,
    loop: true,
    roundLengths: true,
    centeredSlides : false,
    autoplay: {
      delay: 3500
    },
    navigation: {
      nextEl: '.collection__three_next',
      prevEl: '.collection__three_prev',
    },
    breakpoints: {
      480:{
        slidesPerView: 1
      },
      575:{
        slidesPerView: 2,
      },
      992:{    
        slidesPerView: 4
      }
    }
  });


 /*sellers slider one*/
   var serllers_slider__one = new Swiper('.serllers_slider__one', {
    slidesPerView: 2,
    speed: 1200,
    spaceBetween: 30,
    loop: true,
    roundLengths: true,
    centeredSlides : false,
    autoplay: {
      delay: 3000
    },
    navigation: {
      nextEl: '.seller__next',
      prevEl: '.seller__prev',
    },
    breakpoints: {
      575:{
        slidesPerView: 3,
      },
      768:{
        slidesPerView: 4,
      },
      992:{    
        slidesPerView: 5
      },
      1200:{    
        slidesPerView: 6
      },
    }
  });


 /*artist slider one*/
   var artist_slider__one = new Swiper('.artist_slider__one', {
    slidesPerView: 2,
    speed: 1200,
    spaceBetween: 30,
    loop: true,
    roundLengths: true,
    centeredSlides : false,
    autoplay: {
      delay: 3000
    },
    navigation: {
      nextEl: '.artist__next',
      prevEl: '.artist__prev',
    },
    breakpoints: {
      575:{
        slidesPerView: 3,
      },
      768:{
        slidesPerView: 4,
      },
      992:{    
        slidesPerView: 5
      },
      1200:{    
        slidesPerView: 6
      },
    }
  });

   /***********
   mobile menu  js
   ************/
   $('.hamburger').on('click',function (event) {
    $(this).toggleClass('h-active');
    $('.main-nav').toggleClass('slidenav');
  });

  $('.header-home .main-nav ul li  a').on('click',function (event) {
    $('.hamburger').removeClass('h-active');
    $('.main-nav').removeClass('slidenav');
  });

  $(".main-nav .fl").on('click', function (event) {
    var $fl = $(this);
    $(this).parent().siblings().find('.sub-menu').slideUp();
    $(this).parent().siblings().find('.fl').addClass('flaticon-plus').text("+");
    if ($fl.hasClass('flaticon-plus')) {
      $fl.removeClass('flaticon-plus').addClass('flaticon-minus').text("-");
    } else {
      $fl.removeClass('flaticon-minus').addClass('flaticon-plus').text("+");
    }
    $fl.next(".sub-menu").slideToggle();
  });


    /***********
   Sticky Navbar js
   ************/
  $(window).scroll(function () {
    var scroll = $(window).scrollTop();
    if (scroll >= 20) {
      $(".header-area").addClass("sticky");
    } else {
      $(".header-area").removeClass("sticky");
    }
  });

   /***********
   Preloder js
   ************/
   jQuery(window).on('load',function(){
    $(".preloader").delay(1600).fadeOut("slow");
  });

  /***********
   Wow js Initialization
   ************/
  function wowAnimation(){
    new WOW({
      offset:100,
      animateClass:"animated",
      mobile:true,
    }).init();
  }

  jQuery(window).on( 'load', function(){
    wowAnimation();
  });


  /*nice select init*/
  $(document).ready(function() {
    $('.select__nice').niceSelect();
  });



  /*dark mood custom JS*/ 
  var toggleSlider = document.getElementById('slider');

  toggleSlider && toggleSlider.addEventListener('change', e => {
  if(e.target.checked === true) {
    if(document.body.classList.contains('theme-dark-active')){
      document.body.classList.add('theme-light-active')
      document.body.classList.remove('theme-dark-active')
      localStorage.setItem('activeTheme', 'theme-light-active');
    }
    else if(document.body.classList.contains('theme-light-active')){
      document.body.classList.add('theme-dark-active')
      document.body.classList.remove('theme-light-active')
      localStorage.setItem('activeTheme', 'theme-dark-active');
    }
  }



  if(e.target.checked === false){
    if(document.body.classList.contains('theme-dark-active')){
      document.body.classList.add('theme-light-active')
      document.body.classList.remove('theme-dark-active')
      localStorage.setItem('activeTheme', 'theme-light-active');
    }
    else if(document.body.classList.contains('theme-light-active')){
      document.body.classList.add('theme-dark-active')
      document.body.classList.remove('theme-light-active')
      localStorage.setItem('activeTheme', 'theme-dark-active');
    }
  }
});

var activeTheme=localStorage.getItem('activeTheme');
if(activeTheme=='theme-light-active'){
  document.body.classList.add('theme-light-active');
  document.body.classList.remove('theme-dark-active');
  $( "input.check-status" ).prop( "checked", false );

}else{
  document.body.classList.add('theme-dark-active');
  document.body.classList.remove('theme-light-active');
  $( "input.check-status" ).prop( "checked", true );
}


/*custom dropdown*/
$(".has__dropdown").click(function(e){
  $(this).next().toggleClass("dropdown-active");
   e.stopPropagation();
});

$(document).click(function(){
  $(".has__dropdown").next().removeClass("dropdown-active");
});
$(".custom__dropdown").click(function(e){
  e.stopPropagation();
});


/*Mobil searchbar custom JS*/
const searchOpen = document.getElementById('navSearch');
const closeSearch = document.getElementById('closeSearch');
searchOpen && searchOpen.addEventListener('click', ()=>{
  document.getElementById('mobilSearch').classList.add('active-search')
});
closeSearch && closeSearch.addEventListener('click', ()=>{
  if(document.getElementById('mobilSearch').classList.contains('active-search')){
    document.getElementById('mobilSearch').classList.remove('active-search')
  }
});

 window.onclick = function(event){

    if(event.target == document.getElementById('mobilSearch')){
      document.getElementById('mobilSearch').classList.remove('active-search')
    }
     
 }

/*fancybox JS*/
$('[data-fancybox]').fancybox({
  youtube : {
    controls : 0,
    showinfo : 0
  },
  vimeo : {
    color : 'f00'
  }
});

}(jQuery));