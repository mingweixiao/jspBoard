<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>디테일</title>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<style>

</style>
</head>
<body>
	<div id="cmtModContainer">
		<div id="cmtModWin">
			<form id="cmtFrm" action="/boardCmt" method="post">
				<input type="hidden" name="i_board" value="${data.i_board }">
				<input type="hidden" name="i_cmt">				
				<div><textarea name="cmt"></textarea></div>
				<div><input type="submit" value="수정"></div>
			</form>
			<button onclick="closeCmtModWin()">취소</button>
		</div>
	</div>
	<div>
	<div id="cmtModContainer">
	<div>
		<a href="/boardList"><button>리스트로 돌아가기</button></a>
		<c:if test="${loginUser.i_user == data.i_user }">
			<a href="/boardDetail?i_board=${data.i_board}&typ=mod"><button>수정</button></a>
			<a href="/boardDel?i_board=${data.i_board}"><button>삭제</button></a>
		</c:if>
	</div>
	<div>
		<button onclick="doLike(${data.i_board})">
			<span id="markLike"> 
				<c:if test="${data.likeUser > 0 }">♥</c:if>
				<c:if test="${data.likeUser == 0 }">♡</c:if>
			</span>
		</button>
	</div>
	<div>${msg}</div>
	<div>조회수: ${data.cnt }</div>
	<div>${data.title }, ${data.ctnt }, ${data.r_dt }, ${data.userNm }
	</div>

	<div>
		<form action="/boardCmt" method="post">
			<input type="hidden" name="i_board" value="${data.i_board}">
			<div>
				<textarea name="cmt"></textarea>
			</div>
			<div>
				<input type="submit" value="댓글달기">
			</div>
		</form>
	</div>
	<div>
	<h1>댓글</h1>
		<table>
			<tr>
				<th>no</th>
				<th>내용</th>
				<th>등록일시</th>
				<th>작성자</th>
				<th>비고</th>
			</tr>
			<c:forEach items="${data2}" var="item2">
				<tr class="boardCmt">
					<td>${item2.i_cmt}</td>
					<td>${item2.cmt}</td>
					<td>${item2.r_dt}</td>
					<td>${item2.writerNm}</td>
					<td>
						<c:if test="${item2.i_user == loginUser.i_user}">
							<a href = "/boardCmt?i_cmt=${item2.i_cmt}&i_board=${data.i_board}">삭제</a>
							<a href = "#" onclick="showCmtModWin(${item.i_cmt}, '${item.cmt}')">수정</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
	
	<script>
		function showCmtModWin(i_cmt,cmt){
			cmtModContainer.style.visibility = 'hidden'
		}
		function showCmtModWin(i_cmt, cmt) {			
			cmtModContainer.style.visibility = 'visible'			
			cmtFrm.i_cmt.value = i_cmt
			cmtFrm.cmt.value = cmt;
		}
		function doLike(i_board) {
			
			var isLike = (markLike.innerHTML.trim() == '♥') ?  1 : 0
			
			axios.get('/boardLike', {
				params: {
					'i_board': i_board,
					'isLike': isLike
				}
			}).then(function(res) {
				if(res.data.result == 1) { //잘 처리 됨!!!
					/*
					if(markLike.innerHTML == '♥') {
						markLike.innerHTML = '♡' 
					} else {
						markLike.innerHTML = '♥'
					}
					*/
					markLike.innerHTML = (markLike.innerHTML.trim() == '♥') ?  '♡' : '♥'
				}
				
				/*
				if(res.data.result == 1) {
					markLike.innerHTML = '♥'
				} else {
					markLike.innerHTML = '♡'
				}
				*/
				//console.log(JSON.stringify(res))
			})
		}
	</script>
</body>
</html>