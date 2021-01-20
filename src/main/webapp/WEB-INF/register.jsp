<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Register</title>
	</head>
	
	<body>
		<h1>Register</h1>
		<hr>
		<form action="register" method="POST">
			<input type="text" name="nickname" id="nickname" placeholder="Nickname" required><br>
			<input type="text" name="nome" id="nome" placeholder="Nome" required><br>
			<input type="text" name="cognome" id="cognome" placeholder="Cognome" required><br>
			<input type="text" name="mail" id="mail" placeholder="Email" required><br>
			<input type="password" name="password" id="password" placeholder="Password" required><br>
			<button type="submit">Registrati</button>
		</form>
		<h3>${nonRegistato}</h3>
	</body>
</html>