
// MAIN화면에서 회원가입을 누르면 회원가입으로 가는 것입니다. 
function signup_js(){
    let $signupFormObj = $('header>session>div.login_item>ul.login>li.log_li>a#signup');
    $signupFormObj.click(function () {
        let url = './signup.html';
        let target = 'signup';
        let features = 'width=300px, height=300px';
        windowId= window.open(url, target, features);  
        
    });

}

// 로그인 버튼 클릭시 로그인 이벤트 적용 
function login_js(){
    let $loginFormObj = $('header>session>div.login_item>ul.login>li.log_li>a#login');
    $loginFormObj.click(function () {
        let url = './login.html';
        let target = 'login';
        let features = 'width=300px, height=300px';
        windowId= window.open(url, target, features);  
        
    });

}


// 위의 메뉴 클릭했을 때 페이지 이동 
// let $menuOne = $('div.login>form');
// $menuOne.click(function(){
//     let ajaxUrl = $(this).attr('action');
//     let ajaxMethod = $(this).attr('method');
//     let idValue = $(this).find('input[name=id]').val();
//     let pwdValue = $(this).find('input[name=pwd]').val();
//     $.ajax({
//         url: ajaxUrl,
//         method: ajaxMethod,
//         data: {id:idValue, pwd:pwdValue},//"id="+idValue+"&pwd="+pwdValue;
//         success: function(responseObj){
          
            
//             if(responseObj.status == 0){//로그인 실패
//                 alert(responseObj.msg);
//             }else{//로그인 성공
//                 location.href="./"; //현재사용중인 web context path 
//             }
//         },
//         error: function(xhr){
//             alert("응답실패 status:" + xhr.status);
//         } 
//     });
//     return false; //기본이벤트핸들러 금지 + 이벤트전파 중지
// });
function menuClick(){
    let $menuObj = $('div.wrap>ul.menu>li.menu_item>a');
    $menuObj.click(function(){
        let menuHref = $(this).attr('id');//href속성값얻기
        


        // let prodNo = $(this).attr('id');
        // let ajaxUrl = './productdetail';

        let ajaxUrl = ""; //요청URL
        let ajaxMethod = "post"; //요청방식
        switch(menuHref){
            case 'menu1':

                ajaxUrl="./canlendar.html"
                $.ajax({
                    url: ajaxUrl,
                    method: ajaxMethod,
                // success:function
                // $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                //     if(jqXHR.status != 200){
                //         alert('응답실패:' + jqXHR.status);
                //     }
                // });
                success: function(responseObj){
                    location.href="./canlendar.html";
                },
                error: function(xhr){
                    alert("응답실패 status:" + xhr.status);
                } 
            });
            return false; //기본이벤트핸들러 금지 + 이벤트전파 중지
        
                
                
                //ajaxMethod = "GET";
                

                // return false; 
               
            case 'menu2':
                ajaxUrl="./canlendar.html"
                
                // $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                //     if(jqXHR.status != 200){
                //         alert('응답실패:' + jqXHR.status);
                //     }
                // });
                // return false;
                
            case 'menu3':
                ajaxUrl="./canlendar.html"
        //         ajaxUrl = menuHref;
        //         $('section>div.articles').load(ajaxUrl,function(responseText,textStatus, jqXHR ){
        //             if(jqXHR.status != 200){
        //                 alert('응답실패:'+jqXHR.status);
                        
        //             }
        // });
        return false;
           
            case 'menu4t':
                ajaxUrl="./canlendar.html"
                // alert("장바구니메뉴클릭됨");
                // ajaxUrl = menuHref;
                // $('section>div.articles').load(ajaxUrl,function(responseText,textStatus, jqXHR ){
                //     if(jqXHR.status != 200){
                //         alert('응답실패:'+jqXHR.status);
                        
                //     }
                // });
                return false;
            case "menu5":
                ajaxUrl="./canlendar.html"
                // alert("장바구니메뉴클릭됨");
                // ajaxUrl = menuHref;
                // $('section>div.articles').load(ajaxUrl,function(responseText,textStatus, jqXHR ){
                //     if(jqXHR.status != 200){
                //         alert('응답실패:'+jqXHR.status);
                        
                //     }
                // });
                return false;




            // case 'logout':
            //     ajaxUrl = menuHref; 
            //     $.ajax({
            //         url: ajaxUrl,
            //         success:function(){//logoutServlet이 아무 문제 없었다. 
            //             location.href="./"
            //         },
            //         error:function(xhr){
            //             alert('응답실패:' + xhr.status);
            //         }
            //     })
            //     return false;



            // case 'orderlist':
            //     ajaxUrl = menuHref;
            //     $('section>div.articles').load(ajaxUrl,function(responseText,textStatus, jqXHR ){
            //         if(jqXHR.status != 200){
            //             alert('응답실패:'+jqXHR.status);
                        
            //         }
            //     });
            //     return false;
                
        }
    });
    
}
