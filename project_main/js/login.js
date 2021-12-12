<script>window.addEventListener('load', function(){
            let windowId; 
            let btObjArr= document.querySelectorAll('button');
            btObjArr[0].addEventListener('click',function(){
                let url = 'popup.html';
                let target = 'firstw';
                //그 창을 또 띄우지 않기 위해서 이름을 준다. 
                //기존창의 내용이 변경된다. 
                let features = 'width=300px, height=300px';
                windowId= window.open(url, target, features);  
                //지금 열려진 창을 닫아야해서 창의 변수명이 필요하다. 
                btObjArr[0].style.display = 'none';
                btObjArr[1].style.display = 'inline-block';
            });    
            btObjArr[1].addEventListener('click',function(){
                windowId.close(); //새창이 닫힌다 
                btObjArr[1].style.display = 'none';
                btObjArr[0].style.display = 'inline-block';
            });
                
            
            

        });
        </script>