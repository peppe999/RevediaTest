<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
	</head>

	<body>
		<h1>Login</h1>
		<hr>
		<form action="loginUser" method="POST">
			<input type="text" name="nickname" id="nickname" placeholder="Nickname/mail" required><br>
			<input type="password" name="password" id="password" placeholder="Password" required><br>
			<button type="submit">Login</button>
		</form>
	</body>

</html>