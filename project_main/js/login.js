
  function login_js(){
	    let $loginFormObj = $('header>session>div.login_item>ul.login>li.log_li>a#login');
	    $signupFormObj.click(function () {
	        let url = './login.html';
	        let target = 'login';
	        let features = 'width=300px, height=300px';
	        windowId= window.open(url, target, features);  
	        
	    });

	}
