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
					if(responseObj.status == 0){
						alert(responseObj.msg);
					}else{
						location.href="./index.jsp";	
					}
				},
				error: function(xhr){
					alert("응답실패 status:" + xhr.status);
				}
			});
			return false;
		});
}


function beforeSingupClick(){
	$('button.beforeSingupClick').click(function(){
		let $signupObj = $('header>nav>ul>li>a[href=signup.html]');
		$signupObj.trigger('click');
		return false;
	});
}