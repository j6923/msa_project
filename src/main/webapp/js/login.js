/** 로그인 버튼 클릭되었을 떄*/
function loginClick(){
		let $loginFormObj = $(fieldset>form>div.login>button);
		
		$loginFormObj.submit(function(){
			let ajaxUrl = $(this).attr('action');
       		let ajaxMethod = $(this).attr('method');
        	let emailValue = $(this).find('input[name=email]').val();
        	let pwdValue = $(this).find('input[name=pwd]').val();



			$.ajax({
				url: ajaxUrl,
				method: ajaxMethod,
				data:{eMail:emailValue, pwd:pwdValue},
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
