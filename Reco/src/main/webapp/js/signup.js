// signup.html에서 실행되는 js파일입니다. 
function login_js(){
    let $signupFormObj = $('p>a');
    $signupFormObj.click(function () {
        let url = './login.html';
        let target = 'login';
        let features = 'width=300px, height=300px';
        windowId= window.open(url, target, features);  
        
    });

}
