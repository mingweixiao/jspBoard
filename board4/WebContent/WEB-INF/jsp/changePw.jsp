<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내정보</title>
</head>
<body>
	<div>
	<h2>비밀번호 변경</h2>
		<form action="/myPage?typ=${param.typ}" method="POST">
			기존 비밀번호: 
			<input type="password" placeholder="기존 비밀번호"  name="currentPassword" required><br>
			변경할 비밀번호: 
			<input type="password" placeholder="변경할 비밀번호"  name="newPassword" required><br>
			비밀빈호 확인: 
			<input type="password" placeholder="비밀번호 확인" name="newPasswordCheck" required><br>
			<input type="submit" value="submit">
		</form>
	</div>
</body>
</html>