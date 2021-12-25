//이메일 닉넴 중복 확인후 가입 버튼이 나와야하지만 안나옴


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
                }else{
					$submitBtObj.css('visibility','visible');
				}
			},
		});
	});
}

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
				}else{
					$submitBtObj.css('visibility','visible');
				}
			}
		});
	});
}


//가입버튼 클릭되엇을때
function signupSubmit($formObj){
	
	$formObj.submit(function(){
		//비밀번호값 유효성검사
        let $passwordObjArr = $('div.signup>form>input[type=password]');
        let $pwd = $($passwordObjArr[0]);
        let $pwd1 = $($passwordObjArr[1]);
        console.log($pwd.val());
        console.log($pwd1.val());

        if($pwd.val() != $pwd1.val()){
            alert('비밀번호가 일치하지 않습니다');
            $pwd1.focus();
            return false;
		}

		let ajaxUrl = $(this).attr('action');
        let ajaxMethod = $(this).attr('method'); 
		let sendData = $(this).serialize();
		
		$.ajax({
            url:ajaxUrl,
            method:ajaxMethod,
            data:sendData,//{id:idValue, pwd:pwdValue, name:nameValue},
            success:function(responseObj){
                alert(responseObj.msg);
                if(responseObj.status == 1){ //가입성공                
                    location.href='./index.jsp';
                }
            },error:function(xhr){
                alert("응답실패:" + xhr.status);
            }           
        });	
		return false;
	});		
}



//회원가입의 로그인 버튼 클릭되었을때
function beforeLoginClick(){
	$('button.beforeLogin').click(function(){
		ajaxUrl = 'login.html';
            ajaxMethod = "GET";
            $('section>div.articles').empty();
            $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                  if(jqXHR.status != 200){
                      alert('응답실패:' + jqXHR.status);
                  }
            });
	});
	
		
}
