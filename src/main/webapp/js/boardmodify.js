function modifyBoardSubmit(){
	
}

function modifyCancelBtClick(){
	let $modifyCancelBt = $('fieldset>form>button.modifycancel');
	let $brdIdx = $('fieldset>form>input[id=brdIdx]').val().trim();
	console.log($brdIdx);	
	$modifyCancelBt.click(function(){
		$.ajax({
			url: './brddetail',
			method:'get',
			data:{brdIdx: $brdIdx},
			success:function(responseData){
				let $articlesObj = $('section>div.articles');
               	 $articlesObj.empty();
                 $articlesObj.html(responseData);
			}
		});
		return false;
	});
}