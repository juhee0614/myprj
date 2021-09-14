/**
 * 
 */
 'use strict'
  		
  		const $writeBtn = document.getElementById('writeBtn');
  		const $listBtn = document.getElementById('listBtn');
  		
  		//답글
  		$writeBtn.addEventListener("click",e=>{
			const bnum = e.target.dataset.bnum;
			//console.log( writeForm.bcontent.value); 
			const content =writeForm.bcontent.value;
			if(content.trim().length == 0){
				alert('내용을 입력해주세요');
				return;
			}
			//submit버튼 누른것과 동일
			writeForm.submit();
});
  		
  		
  		//목록
  		$listBtn.addEventListener('click',e=>{
			const cate = e.target.dataset.cate;
  			location.href =`/bbs/list?cate=${cate}`;
  		});
