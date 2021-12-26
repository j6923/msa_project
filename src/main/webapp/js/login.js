/** 로그인 버튼 클릭되었을 떄*/
function loginClick(){
		let $loginFormObj = $('div.login>form');
		
		$loginFormObj.submit(function(){
			let ajaxUrl = $(this).attr('action');
       		let ajaxMethod = $(this).attr('method');
        	let emailValue = $(this).find('input[name=email]').val();
        	let pwdValue = $(this).find('input[name=pwd]').val();



			$.ajax({
				url: ajaxUrl,
				method: ajaxMethod,
				data:{email:emailValue, pwd:pwdValue},
				success: function(responseObj){
					if(responseObj.status == 0){//로그인실패
						alert(responseObj.msg);
					}else{
						opener.parent.location.reload();
						window.self.close();	
					}
				},
				error: function(xhr){
					alert("응답실패 status:" + xhr.status);
				}
			});
			return false;
		});
}

//로그인화면에서 회원가입 버튼 클릭할때
function beforeSignupClick(){
	$('button.beforeSignup').click(function(){
		
			ajaxUrl = 'signup.html';
            ajaxMethod = "GET";
            $('section>div.articles').empty();
            $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                  if(jqXHR.status != 200){
                      alert('응답실패:' + jqXHR.status);
                  }
            });
	
	});
}