
//이메일 중복확인
function emailDupchk($emailObj, $submitBtObj){
	$emailObj.focusout(function(){
		if($emailObj.val().trim() == ''){
            alert('이메일를 입력하세요');
            $emailObj.focus();
            return false;
        }

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
                }else {
                    $submitBtObj.css('visibility','visible');
                }
			},
		});
	});
}

//닉네임 중복확인
function nickDupchk($nicknameObj, $submitBtObj){
	$nicknameObj.focusout(function(){
		if($nicknameObj.val().trim() == ''){
            alert('이메일를 입력하세요');
            $nicknameObj.focus();
            return false;
        }
		
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
                }else {
                    $submitBtObj.css('visibility','visible');
                }
			},
		});
	});
}
//가입버튼 클릭되엇을때
function signupClick($formObj){
	
}

//회원가입의 로그인 버튼되었을때
function beforeLoginClick(){
	$('button.beforeLoginClick').click(function(){
		$('header>nav>ul>li>a[href=login.html]').trigger('click');
	});
	
		
}
