function noticeModifyClick(){
	
}

function noticeRemoveClick(){
	$('button.notice_remove').click(function(){
		 let ntcIdx = $(this).attr('id');
		console.log(ntcIdxValue);
		let ajaxUrl = "./ntcremove";
        $.ajax({
            url: ajaxUrl,
			method:'get',
			data: {ntcIdx:ntcIdxValue},
            success:function(responseObj){
                if(responseObj.status == 0){//삭제실패
                    alert(responseObj.msg);
                }else{
					alert(responseObj.msg);
				}
            }
        });
        return false;
	});
}