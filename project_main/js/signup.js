

function signup_js(){
    let $signupFormObj = $('header>session>div.login_item>ul.login>li.log_li>a#signup');
    $signupFormObj.click(function () {
        let url = './signup.html';
        let target = 'signup';
        let features = 'width=300px, height=300px';
        windowId= window.open(url, target, features);  
        
    });

}

