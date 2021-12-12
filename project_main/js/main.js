
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

