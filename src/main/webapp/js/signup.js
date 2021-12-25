
//이메일 중복확인
function emailDupchk($emailObj, $submitBtObj){
	$emailObj.focusout(function(){
		

		let ajaxUrl = "./emaildupchk";
		let ajaxMethod = 'get'; 
		let emailValue =  $emailObj.val().trim();
		
		$.ajax({
			url: ajaxUrl,
			method: ajaxMethod,
			data: {email:emailValue},
			success:function(responseObj){
				if(responseObj.status == 0){
                    alert('이미 사용중인 이메일입니다');
					$emailObj.focus();
				}
			}
		});
	});
}

//닉네임 중복확인
function nickDupchk($nicknameObj, $submitBtObj){
	$nicknameObj.focusout(function(){
		
		let ajaxUrl = "./nickdupchk";
		let ajaxMethod = 'get'; 
		let nicknameValue =  $nicknameObj.val().trim();
		
		$.ajax({
			url: ajaxUrl,
			method: ajaxMethod,
			data: {nickname:nicknameValue},
			success:function(responseObj){
				if(responseObj.status == 0){
                    alert('이미 사용중인 닉네임입니다');
					$nicknameObj.focus();
                }else {                   
                }
			},
		});
	});
}
//가입버튼 클릭되엇을때
function signupSubmit($formObj){
	
	$formObj.submit(function(){
		//비밀번호값 유효성검사
        let $passwordObjArr = $('div.signup>form>:password');
        let $pwd = $($passwordObjArr[0]);
        let $pwd1 = $($passwordObjArr[1]);
        console.log($pwd.val());
        console.log($pw1.val());

        if($pwd1.val() != $pwd2.val()){
            alert('비밀번호가 일치하지 않습니다');
            $pwd1.focus();
            return false;
        }

		return false;
	});
			
}



//회원가입의 로그인 버튼되었을때
function beforeLoginClick(){
	$('button.beforeLoginClick').click(function(){
		let $loginObj = ('header>nav>ul>li>a[href=login.html]')
		$loginObj.trigger('click');
		return false;
	});
	
		
}
