
function loadAfter(){
	let loginFont = document.getElementById("login");
	console.log(loginFont);
	if( loginFont != null){
		
		ajaxUrl = 'before.html';
	    ajaxMethod = "GET";
	    $('section>div.articles').empty();
	    $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
	 		if(jqXHR.status != 200){
				alert('응답실패:' + jqXHR.status);
	        }
		});	
		
	}else{
		ajaxUrl = 'after.jsp';
	    ajaxMethod = "GET";
	    $('section>div.articles').empty();
	    $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
	 		if(jqXHR.status != 200){
				alert('응답실패:' + jqXHR.status);
	        }
	    });
	}
}